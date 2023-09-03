package com.sistemalima.pickPayPlataform.adapters.controller

import com.sistemalima.pickPayPlataform.adapters.controller.entity.Request
import com.sistemalima.pickPayPlataform.adapters.controller.entity.Response
import com.sistemalima.pickPayPlataform.adapters.controller.entity.TransactionRequest
import com.sistemalima.pickPayPlataform.adapters.controller.entity.TransactionResponse
import com.sistemalima.pickPayPlataform.adapters.controller.mapper.TransactionRequestMapper.toDomain
import com.sistemalima.pickPayPlataform.application.ports.inputs.TransactionService
import com.sistemalima.pickPayPlataform.domain.mapper.TransactionMapper.toResponse
import jakarta.validation.Valid
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.util.UriComponentsBuilder

@Validated
@RestController
@RequestMapping(path = ["/transactions"], produces = [MediaType.APPLICATION_JSON_VALUE])
class TransactionController(
    private val transactionService: TransactionService
) {

    @PostMapping
    fun newTransaction(
        @RequestBody @Valid request: Request<TransactionRequest>,
        uriBuilder: UriComponentsBuilder
    ): ResponseEntity<Response<TransactionResponse>> {
        val transaction = transactionService.newTransaction(request.data.toDomain())
        val uri = uriBuilder.path("/transactions/${transaction.id}").build().toUri()
        return ResponseEntity.created(uri).body(Response(transaction.toResponse()))
    }
}