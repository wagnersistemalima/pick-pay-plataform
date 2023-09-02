package com.sistemalima.pickPayPlataform.adapters.repository.mapper

import com.sistemalima.pickPayPlataform.adapters.repository.entities.UserEntity
import com.sistemalima.pickPayPlataform.domain.User

object UserEntityMapper {

    fun UserEntity.toModel(): User {
        return User(
            id = this.id,
            name = this.name,
            document = this.document,
            email = this.email,
            password = this.password,
            userType = this.userType.toModel(),
            accounting = this.accounting.toModel()
        )
    }

    private fun UserEntity.UserEntityTypeEnum.toModel(): User.UserTypeEnum {
        return when(this) {
            UserEntity.UserEntityTypeEnum.COMMON_PERSON -> User.UserTypeEnum.COMMON_PERSON
            UserEntity.UserEntityTypeEnum.MERCHANT_PERSON -> User.UserTypeEnum.MERCHANT_PERSON
        }
    }

    private fun UserEntity.AccountingEntity.toModel(): User.Accounting {
        return User.Accounting(
            balance = this.balance
        )
    }
}