package com.sistemalima.pickPayPlataform.application.usercase

import com.sistemalima.pickPayPlataform.adapters.repository.mapper.UserEntityMapper.toModel
import com.sistemalima.pickPayPlataform.application.ports.inputs.UserService
import com.sistemalima.pickPayPlataform.application.ports.outputs.UserRepository
import com.sistemalima.pickPayPlataform.domain.User
import com.sistemalima.pickPayPlataform.domain.mapper.UserMapper.toEntity
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(
    private val userRepository: UserRepository
): UserService {

    override fun execute(user: User): User {

        val userEntity = userRepository.save(user.toEntity())
        return userEntity.toModel()
    }
}