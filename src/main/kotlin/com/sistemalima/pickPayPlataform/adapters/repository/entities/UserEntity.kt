package com.sistemalima.pickPayPlataform.adapters.repository.entities

import jakarta.persistence.*
import java.math.BigDecimal

@Entity
@Table(name = "tb_user")
data class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "name_user")
    val name: String,

    @Column(name = "cod_ident_user")
    val document: String,

    @Column(name = "email_user")
    val email: String,

    @Column(name = "password_user")
    val password: String,

    @Column(name = "desc_type_user")
    @Enumerated(value = EnumType.STRING)
    val userType: UserEntityTypeEnum,

    @Embedded
    val accounting: AccountingEntity
){
    @Embeddable
    data class AccountingEntity(
        val balance: BigDecimal
    )

    enum class UserEntityTypeEnum {
        COMMON_PERSON,
        MERCHANT_PERSON
    }
}
