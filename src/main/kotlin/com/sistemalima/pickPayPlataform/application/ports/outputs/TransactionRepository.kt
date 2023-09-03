package com.sistemalima.pickPayPlataform.application.ports.outputs

import com.sistemalima.pickPayPlataform.adapters.repository.entities.TransactionEntity
import org.springframework.data.jpa.repository.JpaRepository

interface TransactionRepository: JpaRepository<TransactionEntity, Long> {
}