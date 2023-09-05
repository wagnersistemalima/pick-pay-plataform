package com.sistemalima.pickPayPlataform.adapters.clients.http.service.authorization

import com.sistemalima.pickPayPlataform.adapters.clients.http.service.authorization.entity.AuthorizationResponse
import com.sistemalima.pickPayPlataform.application.ports.outputs.AuthorizationService
import com.sistemalima.pickPayPlataform.domain.Observability
import feign.FeignException.FeignClientException
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.io.IOException
import java.math.BigDecimal

@Service(value = "authorizationApiConnector")
@CircuitBreaker(name = "authorizationApi")
class AuthorizationServiceImpl(
    private val authorizationClient: AuthorizationClient
): AuthorizationService {

    private val logger = LoggerFactory.getLogger(AuthorizationServiceImpl::class.java)

    @CircuitBreaker(name = "authorizationApi", fallbackMethod = "")
    override fun execute(userSenderId: Long, value: BigDecimal, observability: Observability): AuthorizationResponse {

        logger.info("class: ${javaClass.simpleName}, Get moviment request authorization, $observability")

        return try {
            authorizationClient.authorization().body!!

        }catch (exception: FeignClientException) {
            logger.error("Error: class: ${javaClass.simpleName}," +
                    "message: Unavailable authorization service, time out server, $observability")

            throw IOException("Unavailable authorization service, time out server")
        }
    }
}