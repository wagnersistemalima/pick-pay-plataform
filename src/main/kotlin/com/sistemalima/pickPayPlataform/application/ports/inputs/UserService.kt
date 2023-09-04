package com.sistemalima.pickPayPlataform.application.ports.inputs

import com.sistemalima.pickPayPlataform.domain.Observability
import com.sistemalima.pickPayPlataform.domain.User

interface UserService {

    fun create(user: User, observability: Observability): User

    fun findAll(observability: Observability): List<User>

    fun findById(id: Long, observability: Observability): User
}