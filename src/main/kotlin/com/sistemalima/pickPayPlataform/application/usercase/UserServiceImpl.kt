package com.sistemalima.pickPayPlataform.application.usercase

import com.sistemalima.pickPayPlataform.adapters.repository.mapper.UserEntityMapper.toDomain
import com.sistemalima.pickPayPlataform.application.ports.inputs.UserService
import com.sistemalima.pickPayPlataform.application.ports.outputs.UserRepository
import com.sistemalima.pickPayPlataform.domain.User
import com.sistemalima.pickPayPlataform.domain.exceptions.BusinessException
import com.sistemalima.pickPayPlataform.domain.exceptions.ResourceEntityNotFoundException
import com.sistemalima.pickPayPlataform.domain.mapper.UserMapper.toEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserServiceImpl(
    private val userRepository: UserRepository
): UserService {

    @Transactional
    override fun create(user: User): User {
        validatedUserType(user)
        validationRegisterUnique(user)
        val userEntity = userRepository.save(user.toEntity())
        return userEntity.toDomain()
    }

    @Transactional(readOnly = true)
    override fun findAll(): List<User> {
        val listUserEntity = userRepository.findAll()
        return listUserEntity.map { it.toDomain() }
    }

    @Transactional(readOnly = true)
    override fun findById(id: Long): User {
        val userEntity = userRepository.findById(id).orElseThrow {
            throw ResourceEntityNotFoundException("user id not found")
        }
        return userEntity.toDomain()
    }

    @Transactional(readOnly = true)
    private fun validationRegisterUnique(user: User) {
        if (userRepository.existsByEmail(user.email)) {
            throw BusinessException("emails must be unique in the system")
        }

        if (userRepository.existsByDocument(user.document)) {
            throw BusinessException("documents must be unique in the system")
        }
    }

    private fun validatedUserType(user: User): Boolean {

        val cnpj = 14
        val cpf = 11
        return if (user.document.length == cnpj && user.userType == User.UserTypeEnum.MERCHANT_PERSON) {
            true
        } else if (user.document.length == cpf && user.userType == User.UserTypeEnum.COMMON_PERSON) {
            true
        } else {
            throw BusinessException("common person or merchant type validation error")
        }
    }
}