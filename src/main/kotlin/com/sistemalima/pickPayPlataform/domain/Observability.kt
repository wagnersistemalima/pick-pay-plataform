package com.sistemalima.pickPayPlataform.domain

import java.time.LocalDateTime
import java.util.UUID

data class Observability(
    val correlationId: String = UUID.randomUUID().toString(),
    val dateTime: String = LocalDateTime.now().toString(),
    val email: String? = null,
    val userId: Long? = null,
    val senderId: Long?,
    val receveirId: Long?
)
