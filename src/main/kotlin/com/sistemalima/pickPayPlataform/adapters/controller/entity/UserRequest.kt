package com.sistemalima.pickPayPlataform.adapters.controller.entity

import com.sistemalima.pickPayPlataform.adapters.controller.validated.CpfOrCnpj
import jakarta.validation.Valid
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import org.hibernate.validator.constraints.Length
import java.math.BigDecimal

data class UserRequest(
    @field:NotBlank
    val name: String,

    @field:CpfOrCnpj
    @field:NotBlank
    val document: String,

    @field:Email(message = "email tem que ser valido")
    @field:NotBlank
    val email: String,

    @field:NotBlank
    @field:Length(min = 4, max = 4, message = "a senha deve ter no máximo 4 caracteres e no mínimo 4 caracteres")
    val password: String,

    @field:NotNull
    val userType: UserRequestTypeEnum,

    @field:NotNull
    @field:Valid
    val accounting: AccountingRequest
){
    data class AccountingRequest(
        @field:Positive
        @field:NotNull
        val balance: BigDecimal
    )

    enum class UserRequestTypeEnum {
        COMMON_PERSON,
        MERCHANT_PERSON
    }
}
