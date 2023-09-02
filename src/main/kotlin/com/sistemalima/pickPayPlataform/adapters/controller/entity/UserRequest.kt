package com.sistemalima.pickPayPlataform.adapters.controller.entity

import java.math.BigDecimal

data class UserRequest(
    val name: String,
    val document: String,
    val email: String,
    val password: String,
    val userType: UserRequestTypeEnum,
    val accounting: AccountingRequest
){
    data class AccountingRequest(
        val balance: BigDecimal
    )

    enum class UserRequestTypeEnum {
        COMMON_PERSON,
        MERCHANT_PERSON
    }
}
