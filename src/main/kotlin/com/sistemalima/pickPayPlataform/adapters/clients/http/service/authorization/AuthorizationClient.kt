package com.sistemalima.pickPayPlataform.adapters.clients.http.service.authorization

import com.sistemalima.pickPayPlataform.adapters.clients.http.service.authorization.entity.AuthorizationResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping

@FeignClient(
    name = "authorizationClient",
    url = "https://run.mocky.io"
)
interface AuthorizationClient {

    @GetMapping(
        value = ["/v3/8fafdd68-a090-496f-8c9a-3442cf30dae6"],
        produces = [MediaType.APPLICATION_JSON_VALUE],
        consumes = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun authorization(): ResponseEntity<AuthorizationResponse>
}