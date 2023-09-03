package com.sistemalima.pickPayPlataform.application.usercase

import com.sistemalima.pickPayPlataform.adapters.repository.mapper.UserEntityMapper.toModel
import com.sistemalima.pickPayPlataform.application.ports.inputs.UserService
import com.sistemalima.pickPayPlataform.application.ports.outputs.UserRepository
import com.sistemalima.pickPayPlataform.domain.User
import com.sistemalima.pickPayPlataform.domain.exceptions.BusinessException
import com.sistemalima.pickPayPlataform.domain.mapper.UserMapper.toEntity
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserServiceImpl(
    private val userRepository: UserRepository
): UserService {

    @Transactional
    override fun create(user: User): User {
        validationRegisterUnique(user)
        val userEntity = userRepository.save(user.toEntity())
        return userEntity.toModel()
    }

    @Transactional(readOnly = true)
    override fun findAll(): List<User> {
        val listUserEntity = userRepository.findAll()
        return listUserEntity.map { it.toModel() }
    }

    @Transactional(readOnly = true)
    override fun findById(id: Long): User {
        val userEntity = userRepository.findById(id).orElseThrow {
            throw EntityNotFoundException("user id not found")
        }
        return userEntity.toModel()
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
}