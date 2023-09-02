package com.sistemalima.pickPayPlataform.adapters.controller.entity

import jakarta.validation.Valid

data class Request<T>(
    @field:Valid
    val data: T
)
