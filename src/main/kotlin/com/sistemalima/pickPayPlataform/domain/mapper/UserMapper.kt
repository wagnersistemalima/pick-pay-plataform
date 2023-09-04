package com.sistemalima.pickPayPlataform.domain.mapper

import com.sistemalima.pickPayPlataform.adapters.controller.entity.UserResponse
import com.sistemalima.pickPayPlataform.adapters.repository.entities.UserEntity
import com.sistemalima.pickPayPlataform.domain.User

object UserMapper {

    fun User.toEntity(): UserEntity {

        return UserEntity(
            name = this.name,
            document = this.document,
            email = this.email,
            password = this.password,
            userType = this.userType.toEntity(),
            accounting = this.accounting.toAccountingEntity()
        )
    }

    private fun User.UserTypeEnum.toEntity(): UserEntity.UserEntityTypeEnum {
        return when(this) {
            User.UserTypeEnum.COMMON_PERSON -> UserEntity.UserEntityTypeEnum.COMMON_PERSON
            User.UserTypeEnum.MERCHANT_PERSON -> UserEntity.UserEntityTypeEnum.MERCHANT_PERSON
        }
    }

    private fun User.Accounting.toAccountingEntity(): UserEntity.AccountingEntity {
        return UserEntity.AccountingEntity(
            balance = this.balance
        )
    }

    fun User.toResponse(): UserResponse {
        return UserResponse(
            id = checkNotNull(this.id),
            name = this.name,
            userType = this.userType.toResponse(),
            accounting = this.accounting.toResponse()
        )
    }

    private fun User.UserTypeEnum.toResponse(): UserResponse.UserResponseTypeEnum {
        return when(this) {
            User.UserTypeEnum.COMMON_PERSON -> UserResponse.UserResponseTypeEnum.COMMON_PERSON
            User.UserTypeEnum.MERCHANT_PERSON -> UserResponse.UserResponseTypeEnum.MERCHANT_PERSON
        }
    }

    private fun User.Accounting.toResponse(): UserResponse.AccountingResponse {
        return UserResponse.AccountingResponse(
            balance = this.balance
        )
    }
}