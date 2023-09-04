package com.sistemalima.pickPayPlataform.adapters.clients.http.service.notification

import com.sistemalima.pickPayPlataform.adapters.clients.http.service.notification.entity.NotificationRequest
import com.sistemalima.pickPayPlataform.adapters.clients.http.service.notification.entity.NotificationResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@FeignClient(
    name = "notificationClient",
    url = "http://o4d9z.mocklab.io"
)
interface NotificationClient {

    @PostMapping(
        value = ["/notify"],
        produces = [MediaType.APPLICATION_JSON_VALUE],
        consumes = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun send(@RequestBody notificationRequest: NotificationRequest): ResponseEntity<NotificationResponse>
}