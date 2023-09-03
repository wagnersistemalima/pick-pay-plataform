package com.sistemalima.pickPayPlataform.adapters.repository.entities

import com.sistemalima.pickPayPlataform.domain.exceptions.BusinessException
import java.math.BigDecimal
import jakarta.persistence.Entity
import jakarta.persistence.Table
import jakarta.persistence.Column
import jakarta.persistence.Id
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Enumerated
import jakarta.persistence.EnumType
import jakarta.persistence.Embedded
import jakarta.persistence.Embeddable

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
        var balance: BigDecimal
    ) {
        fun toWithdraw(value: BigDecimal) {
            if (value > BigDecimal.ZERO && this.balance > BigDecimal.ZERO && value <= this.balance) {
                this.balance -= value
            } else {
                throw BusinessException("Insufficient account balance for this transaction")
            }
        }

        fun toDeposit(value: BigDecimal) {
            if (value > BigDecimal.ZERO) {
                this.balance += value
            }
        }
    }

    enum class UserEntityTypeEnum {
        COMMON_PERSON,
        MERCHANT_PERSON
    }
}
