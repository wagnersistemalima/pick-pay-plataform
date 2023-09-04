package com.sistemalima.pickPayPlataform.adapters.clients.http.service.notification

import com.sistemalima.pickPayPlataform.adapters.clients.http.service.notification.entity.NotificationRequest
import com.sistemalima.pickPayPlataform.adapters.clients.http.service.notification.entity.NotificationResponse
import com.sistemalima.pickPayPlataform.application.ports.outputs.NotificationService
import com.sistemalima.pickPayPlataform.domain.Observability
import feign.FeignException
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class NotificationServiceImpl(
    private val notificationClient: NotificationClient
): NotificationService {

    private val logger = LoggerFactory.getLogger(NotificationServiceImpl::class.java)

    override fun execute(notificationRequest: NotificationRequest, observability: Observability): NotificationResponse {

        return try {

            logger.info(javaClass.simpleName, "notification service transaction: $observability")

            notificationClient.send(notificationRequest).body!!

        }catch (exception: FeignException) {
            // serviço de terceiro estar indisponível/instável. mock para simular o envio

            logger.warn("${javaClass.simpleName}, email notification service is unavailable, $observability")

            NotificationResponse(
                email = notificationRequest.email,
                message = "Unavailable notification service time out"
            )
        }
    }
}