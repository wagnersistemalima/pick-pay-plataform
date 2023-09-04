package com.sistemalima.pickPayPlataform.application.usercase

import com.sistemalima.pickPayPlataform.adapters.repository.mapper.UserEntityMapper.toDomain
import com.sistemalima.pickPayPlataform.application.ports.inputs.UserService
import com.sistemalima.pickPayPlataform.application.ports.outputs.UserRepository
import com.sistemalima.pickPayPlataform.domain.Observability
import com.sistemalima.pickPayPlataform.domain.User
import com.sistemalima.pickPayPlataform.domain.exceptions.BusinessException
import com.sistemalima.pickPayPlataform.domain.exceptions.ResourceEntityNotFoundException
import com.sistemalima.pickPayPlataform.domain.mapper.UserMapper.toEntity
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserServiceImpl(
    private val userRepository: UserRepository
): UserService {

    private val logger = LoggerFactory.getLogger(UserServiceImpl::class.java)

    @Transactional
    override fun create(user: User, observability: Observability): User {

        logger.info("class: ${javaClass.simpleName}, Get moviment request create user, $observability")

        validatedUserType(user, observability)
        validationRegisterUnique(user, observability)
        val userEntity = userRepository.save(user.toEntity())
        return userEntity.toDomain()
    }

    @Transactional(readOnly = true)
    override fun findAll(observability: Observability): List<User> {

        logger.info("class: ${javaClass.simpleName}, Get moviment request findAll user, $observability")


        val listUserEntity = userRepository.findAll()
        return listUserEntity.map { it.toDomain() }
    }

    @Transactional(readOnly = true)
    override fun findById(id: Long, observability: Observability): User {

        logger.info("class: ${javaClass.simpleName}, Get moviment request findById user, $observability")

        val userEntity = userRepository.findById(id).orElseThrow {

            logger.error("Error: class: ${javaClass.simpleName}," +
                    " method: findById, message: id: $id not found, $observability")

            throw ResourceEntityNotFoundException("user id not found")
        }
        return userEntity.toDomain()
    }

    @Transactional(readOnly = true)
    private fun validationRegisterUnique(user: User, observability: Observability) {
        if (userRepository.existsByEmail(user.email)) {

            logger.error("Error: class: ${javaClass.simpleName}," +
                    " method: validationRegisterUnique, message: emails must be unique in the system, $observability")

            throw BusinessException("emails must be unique in the system")
        }

        if (userRepository.existsByDocument(user.document)) {

            logger.error("Error: class: ${javaClass.simpleName}," +
                    " method: validationRegisterUnique, message: documents must be unique in the system, $observability")

            throw BusinessException("documents must be unique in the system")
        }
    }

    private fun validatedUserType(user: User, observability: Observability): Boolean {

        val cnpj = 14
        val cpf = 11
        return if (user.document.length == cnpj && user.userType == User.UserTypeEnum.MERCHANT_PERSON) {
            true
        } else if (user.document.length == cpf && user.userType == User.UserTypeEnum.COMMON_PERSON) {
            true
        } else {

            logger.error("Error: class: ${javaClass.simpleName}," +
                    " method: validatedUserType, message: common person or merchant type validation error, $observability")

            throw BusinessException("common person or merchant type validation error")
        }
    }
}