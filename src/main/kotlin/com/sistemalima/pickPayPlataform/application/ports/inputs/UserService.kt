package com.sistemalima.pickPayPlataform.application.ports.inputs

import com.sistemalima.pickPayPlataform.domain.User

interface UserService {

    fun execute(user: User): User
}