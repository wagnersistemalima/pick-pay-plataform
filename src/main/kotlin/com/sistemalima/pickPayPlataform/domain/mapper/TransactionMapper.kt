package com.sistemalima.pickPayPlataform.domain.mapper

import com.sistemalima.pickPayPlataform.adapters.controller.entity.TransactionResponse
import com.sistemalima.pickPayPlataform.adapters.repository.entities.TransactionEntity
import com.sistemalima.pickPayPlataform.domain.Transaction

object TransactionMapper {

    fun Transaction.toEntity(): TransactionEntity {
        return TransactionEntity(
            id = this.id,
            senderId = this.senderId,
            receiverId = this.receiverId,
            value = this.value
        )
    }

    fun Transaction.toResponse(): TransactionResponse {
        return TransactionResponse(
            id = checkNotNull(this.id),
            senderId = this.senderId,
            receiverId = this.receiverId,
            value = this.value,
            email = this.email,
            message = this.message
        )
    }
}