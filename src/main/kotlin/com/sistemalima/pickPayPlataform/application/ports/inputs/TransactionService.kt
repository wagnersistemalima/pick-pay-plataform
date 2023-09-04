package com.sistemalima.pickPayPlataform.application.ports.inputs

import com.sistemalima.pickPayPlataform.domain.Observability
import com.sistemalima.pickPayPlataform.domain.Transaction

interface TransactionService {

    fun newTransaction(transaction: Transaction, observability: Observability): Transaction
}