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
import com.sistemalima.pickPayPlataform.domain.Observability
import com.sistemalima.pickPayPlataform.domain.Transaction
import com.sistemalima.pickPayPlataform.domain.exceptions.BusinessException
import com.sistemalima.pickPayPlataform.domain.exceptions.ResourceEntityNotFoundException
import com.sistemalima.pickPayPlataform.domain.mapper.TransactionMapper.toEntity
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TransactionServiceImpl(
    private val transactionRepository: TransactionRepository,
    private val userRepository: UserRepository,
    private val authorizationService: AuthorizationService,
    private val notificationService: NotificationService
) : TransactionService {

    private val logger = LoggerFactory.getLogger(TransactionServiceImpl::class.java)

    @Transactional
    override fun newTransaction(transaction: Transaction, observability: Observability): Transaction {

        logger.info("class: ${javaClass.simpleName}, Get moviment request new transaction, $observability")

        // logica para debitar do usuario que envia COMMON_PERSON
        val userEntitySender = userRepository.findById(transaction.senderId).orElseThrow {

            logger.error("Error: class: ${javaClass.simpleName}," +
                    " message: senderId ${transaction.senderId} not found, $observability")

            ResourceEntityNotFoundException("senderId not found")
        }

        val authorizationResponse = authorizationService
            .execute(checkNotNull(userEntitySender.id), userEntitySender.accounting.balance, observability)

        validatedAuthorization(authorizationResponse, observability)

        commonPersonTransfer(userEntitySender, transaction, observability)

        // logica para creditar ao usuario que recebe
        val userEntityReceiver = userRepository.findById(transaction.receiverId).orElseThrow {

            logger.error("Error: class: ${javaClass.simpleName}," +
                    " message: receiverId ${transaction.receiverId} not found, $observability")

            ResourceEntityNotFoundException("receiverId not found")
        }

        accountCredit(userEntityReceiver, transaction)

        val transactionEntity = transactionRepository.save(transaction.toEntity())

        // enviar email com a mensagem para o recebedor da transação
        val notificationResponse = notificationService.execute(userEntityReceiver.toRequest(transaction.value), observability)

        return transactionEntity.toDomain(notificationResponse)
    }

    @Transactional
    private fun commonPersonTransfer(
        userEntitySender: UserEntity,
        transaction: Transaction,
        observability: Observability
    ) {

        if (userEntitySender.userType == UserEntity.UserEntityTypeEnum.COMMON_PERSON) {
            userEntitySender.accounting.toWithdraw(transaction.value)
            userRepository.save(userEntitySender)
        } else {

            logger.error("Error: class: ${javaClass.simpleName}," +
                    " message: senderId: ${transaction.senderId}," +
                    " merchante person only receives transfers, does not send money to anyone, $observability")

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

    private fun validatedAuthorization(authorization: AuthorizationResponse, observability: Observability): Boolean {

        return when(authorization.message) {
            "Autorizado" -> true
            else -> {

                logger.error("Error: class: ${javaClass.simpleName}," +
                        " message: authorization denied, $observability")

                throw BusinessException("authorization denied")
            }
        }
    }
}