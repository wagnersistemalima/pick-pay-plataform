package com.sistemalima.pickPayPlataform.adapters.controller.mapper

import com.sistemalima.pickPayPlataform.adapters.controller.entity.UserRequest
import com.sistemalima.pickPayPlataform.adapters.controller.mapper.UserRequestMapper.toAccounting
import com.sistemalima.pickPayPlataform.adapters.controller.mapper.UserRequestMapper.toDomain
import com.sistemalima.pickPayPlataform.adapters.controller.mapper.UserRequestMapper.toModel
import com.sistemalima.pickPayPlataform.domain.User
import com.sistemalima.pickPayPlataform.domain.exceptions.BusinessException

object UserRequestMapper {

    private const val CNPJ = 14
    private const val CPF = 11

    fun UserRequest.toDomain(): User {

        validatedUserType()

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

    private fun UserRequest.validatedUserType(): Boolean {

        return if (this.document.length == CNPJ && this.userType == UserRequest.UserRequestTypeEnum.MERCHANT_PERSON) {
            true
        } else if (this.document.length == CPF && this.userType == UserRequest.UserRequestTypeEnum.COMMON_PERSON) {
            true
        } else {
            throw BusinessException("common person or merchant type validation error")
        }
    }
}