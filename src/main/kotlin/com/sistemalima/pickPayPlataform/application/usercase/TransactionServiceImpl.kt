package com.sistemalima.pickPayPlataform.application.usercase

import com.sistemalima.pickPayPlataform.adapters.clients.http.service.authorization.entity.AuthorizationResponse
import com.sistemalima.pickPayPlataform.adapters.repository.entities.UserEntity
import com.sistemalima.pickPayPlataform.adapters.repository.mapper.TransactionEntityMapper.toDomain
import com.sistemalima.pickPayPlataform.adapters.repository.mapper.UserEntityMapper.toRequest
import com.sistemalima.pickPayPlataform.application.ports.inputs.TransactionService
import com.sistemalima.pickPayPlataform.application.ports.outputs.AuthorizationService
import com.sistemalima.pickPayPlataform.application.ports.outputs.NotificationService
import com.sistemalima.pickPayPlataform.application.ports.outputs.TransactionRepository
import com.sistemalima.pickPayPlataform.application.ports.outputs.UserRepository
import com.sistemalima.pickPayPlataform.domain.Transaction
import com.sistemalima.pickPayPlataform.domain.exceptions.BusinessException
import com.sistemalima.pickPayPlataform.domain.exceptions.ResourceEntityNotFoundException
import com.sistemalima.pickPayPlataform.domain.mapper.TransactionMapper.toEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TransactionServiceImpl(
    private val transactionRepository: TransactionRepository,
    private val userRepository: UserRepository,
    private val authorizationService: AuthorizationService,
    private val notificationService: NotificationService
) : TransactionService {
    override fun newTransaction(transaction: Transaction): Transaction {

        // logica para debitar do usuario que envia COMMON_PERSON
        val userEntitySender = userRepository.findById(transaction.senderId).orElseThrow {
            ResourceEntityNotFoundException("senderId not found")
        }

        val authorizationResponse = authorizationService.execute(checkNotNull(userEntitySender.id), userEntitySender.accounting.balance)

        validatedAuthorization(authorizationResponse)

        commonPersonTransfer(userEntitySender, transaction)

        // logica para creditar ao usuario que recebe
        val userEntityReceiver = userRepository.findById(transaction.receiverId).orElseThrow {
            ResourceEntityNotFoundException("receiverId not found")
        }

        accountCredit(userEntityReceiver, transaction)

        val transactionEntity = transactionRepository.save(transaction.toEntity())

        // enviar email com a mensagem para o recebedor da transação
        val notificationResponse = notificationService.execute(userEntityReceiver.toRequest(transaction.value))
        return transactionEntity.toDomain(notificationResponse)
    }

    @Transactional
    private fun commonPersonTransfer(userEntitySender: UserEntity, transaction: Transaction) {

        if (userEntitySender.userType == UserEntity.UserEntityTypeEnum.COMMON_PERSON) {
            userEntitySender.accounting.toWithdraw(transaction.value)
            userRepository.save(userEntitySender)
        } else {
            throw BusinessException(
                "senderId: ${transaction.senderId}," +
                        " merchante person only receives transfers, does not send money to anyone"
            )
        }
    }

    @Transactional
    private fun accountCredit(userEntityReceiver: UserEntity, transaction: Transaction) {
        userEntityReceiver.accounting.toDeposit(transaction.value)
        userRepository.save(userEntityReceiver)
    }

    private fun validatedAuthorization(authorization: AuthorizationResponse): Boolean {

        return when(authorization.message) {
            "Autorizado" -> true
            else -> {
                throw BusinessException("authorization denied")
            }
        }
    }
}