package com.sistemalima.pickPayPlataform.application.ports.outputs

import com.sistemalima.pickPayPlataform.adapters.clients.http.service.authorization.entity.AuthorizationResponse
import java.math.BigDecimal

interface AuthorizationService {

    fun execute(userSenderId: Long, value: BigDecimal): AuthorizationResponse
}