package com.sistemalima.pickPayPlataform.adapters.controller.mapper

import com.sistemalima.pickPayPlataform.adapters.controller.entity.TransactionRequest
import com.sistemalima.pickPayPlataform.domain.Transaction

object TransactionRequestMapper {

    fun TransactionRequest.toDomain(): Transaction {
        return Transaction(
            senderId = this.senderId,
            receiverId = this.receiverId,
            value = this.value
        )
    }
}