package com.sistemalima.pickPayPlataform.adapters.repository.mapper

import com.sistemalima.pickPayPlataform.adapters.clients.http.service.notification.entity.NotificationRequest
import com.sistemalima.pickPayPlataform.adapters.repository.entities.UserEntity
import com.sistemalima.pickPayPlataform.domain.User
import java.math.BigDecimal
import java.time.LocalDateTime

object UserEntityMapper {

    fun UserEntity.toDomain(): User {
        return User(
            id = this.id,
            name = this.name,
            document = this.document,
            email = this.email,
            password = this.password,
            userType = this.userType.toDomain(),
            accounting = this.accounting.toDomain()
        )
    }

    private fun UserEntity.UserEntityTypeEnum.toDomain(): User.UserTypeEnum {
        return when(this) {
            UserEntity.UserEntityTypeEnum.COMMON_PERSON -> User.UserTypeEnum.COMMON_PERSON
            UserEntity.UserEntityTypeEnum.MERCHANT_PERSON -> User.UserTypeEnum.MERCHANT_PERSON
        }
    }

    private fun UserEntity.AccountingEntity.toDomain(): User.Accounting {
        return User.Accounting(
            balance = this.balance
        )
    }

    fun UserEntity.toRequest(value: BigDecimal): NotificationRequest {
        val dateTime = LocalDateTime.now().toString()
        return NotificationRequest(
            email = this.email,
            message = "transfer received in the amount of $value, date: $dateTime"
        )
    }
}