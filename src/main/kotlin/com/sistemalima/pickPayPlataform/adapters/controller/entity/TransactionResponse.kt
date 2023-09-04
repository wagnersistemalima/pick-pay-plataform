package com.sistemalima.pickPayPlataform.adapters.controller.entity

import java.math.BigDecimal

data class TransactionResponse(
    val id: Long,
    val senderId: Long,
    val receiverId: Long,
    val value: BigDecimal,
    val email: String?,
    val message: String?
)
