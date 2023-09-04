package com.sistemalima.pickPayPlataform.adapters.controller.entity

import java.math.BigDecimal

data class UserResponse(
    val id: Long,
    val name: String,
    val userType: UserResponseTypeEnum,
    val accounting: AccountingResponse
){
    data class AccountingResponse(
        val balance: BigDecimal
    )

    enum class UserResponseTypeEnum {
        COMMON_PERSON,
        MERCHANT_PERSON
    }
}
