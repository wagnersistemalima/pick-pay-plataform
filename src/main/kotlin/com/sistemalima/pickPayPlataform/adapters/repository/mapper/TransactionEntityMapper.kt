package com.sistemalima.pickPayPlataform.adapters.repository.mapper

import com.sistemalima.pickPayPlataform.adapters.repository.entities.TransactionEntity
import com.sistemalima.pickPayPlataform.domain.Transaction

object TransactionEntityMapper {

    fun TransactionEntity.toDomain(): Transaction {
        return Transaction(
            id = this.id,
            senderId = this.senderId,
            receiverId = this.receiverId,
            value = this.value
        )
    }
}