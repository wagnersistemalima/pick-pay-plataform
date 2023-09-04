package com.sistemalima.pickPayPlataform.application.ports.outputs

import com.sistemalima.pickPayPlataform.adapters.clients.http.service.notification.entity.NotificationRequest
import com.sistemalima.pickPayPlataform.adapters.clients.http.service.notification.entity.NotificationResponse
import com.sistemalima.pickPayPlataform.domain.Observability

interface NotificationService {

    fun execute(notificationRequest: NotificationRequest, observability: Observability): NotificationResponse
}