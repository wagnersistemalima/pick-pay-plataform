package com.sistemalima.pickPayPlataform.adapters.controller.entity

import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import java.math.BigDecimal

data class TransactionRequest(

    @field:NotNull
    @field:Positive
    val senderId: Long,

    @field:NotNull
    @field:Positive
    val receiverId: Long,

    @field:Positive
    @field:NotNull
    val value: BigDecimal
)
