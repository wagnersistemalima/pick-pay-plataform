package com.sistemalima.pickPayPlataform.adapters.clients.http.service.authorization

import com.sistemalima.pickPayPlataform.adapters.clients.http.service.authorization.entity.AuthorizationResponse
import com.sistemalima.pickPayPlataform.application.ports.outputs.AuthorizationService
import feign.FeignException.FeignClientException
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
import org.springframework.stereotype.Service
import java.io.IOException
import java.math.BigDecimal

@Service(value = "backendAConnector")
@CircuitBreaker(name = "backendA")
class AuthorizationServiceImpl(
    private val authorizationClient: AuthorizationClient
): AuthorizationService {

    @CircuitBreaker(name = "backendA", fallbackMethod = "")
    override fun execute(userSenderId: Long, value: BigDecimal): AuthorizationResponse {

        return try {
            authorizationClient.authorization().body!!

        }catch (exception: FeignClientException) {
            throw IOException("Unavailable authorization service, time out server")
        }
    }
}