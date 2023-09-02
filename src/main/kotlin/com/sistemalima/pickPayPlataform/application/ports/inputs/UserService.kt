package com.sistemalima.pickPayPlataform.application.ports.inputs

import com.sistemalima.pickPayPlataform.domain.User

interface UserService {

    fun create(user: User): User

    fun findAll(): List<User>
}