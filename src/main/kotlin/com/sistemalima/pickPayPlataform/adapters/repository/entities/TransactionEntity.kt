package com.sistemalima.pickPayPlataform.adapters.repository.entities

import jakarta.persistence.Entity
import jakarta.persistence.Table
import jakarta.persistence.Column
import jakarta.persistence.Id
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import java.math.BigDecimal

@Entity
@Table(name = "tb_transaction")
data class TransactionEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "cod_sender_tran")
    val senderId: Long,

    @Column(name = "cod_receiver_tran")
    val receiverId: Long,

    @Column(name = "val_tran")
    val value: BigDecimal
)
