package com.sistemalima.pickPayPlataform.adapters.repository.mapper

import com.sistemalima.pickPayPlataform.adapters.clients.http.service.notification.entity.NotificationResponse
import com.sistemalima.pickPayPlataform.adapters.repository.entities.TransactionEntity
import com.sistemalima.pickPayPlataform.domain.Transaction

object TransactionEntityMapper {

    fun TransactionEntity.toDomain(notificationResponse: NotificationResponse): Transaction {
        return Transaction(
            id = this.id,
            senderId = this.senderId,
            receiverId = this.receiverId,
            value = this.value,
            email = notificationResponse.email,
            message = notificationResponse.message
        )
    }
}