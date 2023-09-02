package com.sistemalima.pickPayPlataform.domain

import java.math.BigDecimal

data class User(
    val id: Long? = null,
    val name: String,
    val document: String,
    val email: String,
    val password: String,
    val userType: UserTypeEnum,
    val accounting: Accounting
){
    data class Accounting(
        val balance: BigDecimal
    )

    enum class UserTypeEnum {
        COMMON_PERSON,
        MERCHANT_PERSON
    }
}
