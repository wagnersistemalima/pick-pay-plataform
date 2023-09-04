package com.sistemalima.pickPayPlataform.adapters.controller.advice.entity

import java.time.LocalDateTime

data class ErrorView(
    val timestamp: LocalDateTime = LocalDateTime.now(),
    val status: Int,
    val error: String? = null,
    val message: String,
    val path: String
)
