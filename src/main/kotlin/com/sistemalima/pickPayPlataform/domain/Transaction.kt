package com.sistemalima.pickPayPlataform.domain

import java.math.BigDecimal

data class Transaction(
    val id: Long? = null,
    val senderId: Long,
    val receiverId: Long,
    val value: BigDecimal
)
