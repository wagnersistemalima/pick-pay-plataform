package com.sistemalima.pickPayPlataform.adapters.controller.mapper

import com.sistemalima.pickPayPlataform.adapters.controller.entity.UserRequest
import com.sistemalima.pickPayPlataform.domain.User

object UserRequestMapper {

    fun UserRequest.toDomain(): User {
        return User(
            name = this.name,
            document = this.document,
            email = this.email,
            password = this.password,
            userType = this.userType.toModel(),
            accounting = toAccounting()
        )
    }

    private fun UserRequest.UserRequestTypeEnum.toModel(): User.UserTypeEnum {
        return when(this) {
            UserRequest.UserRequestTypeEnum.COMMON_PERSON -> {User.UserTypeEnum.COMMON_PERSON}
            UserRequest.UserRequestTypeEnum.MERCHANT_PERSON -> {User.UserTypeEnum.MERCHANT_PERSON}
        }
    }

    private fun UserRequest.toAccounting(): User.Accounting {
        return User.Accounting(
            balance = this.accounting.balance
        )
    }
}