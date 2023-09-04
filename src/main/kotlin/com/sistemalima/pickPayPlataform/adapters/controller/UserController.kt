package com.sistemalima.pickPayPlataform.adapters.controller

import com.sistemalima.pickPayPlataform.adapters.controller.entity.Request
import com.sistemalima.pickPayPlataform.adapters.controller.entity.Response
import com.sistemalima.pickPayPlataform.adapters.controller.entity.UserRequest
import com.sistemalima.pickPayPlataform.adapters.controller.entity.UserResponse
import com.sistemalima.pickPayPlataform.adapters.controller.mapper.UserRequestMapper.toDomain
import com.sistemalima.pickPayPlataform.application.ports.inputs.UserService
import com.sistemalima.pickPayPlataform.domain.Observability
import com.sistemalima.pickPayPlataform.domain.mapper.UserMapper.toResponse
import jakarta.validation.Valid
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.util.UriComponentsBuilder

@Validated
@RestController
@RequestMapping(path = ["/users"], produces = [MediaType.APPLICATION_JSON_VALUE])
class UserController(
    private val userService: UserService
) {

    private val logger = LoggerFactory.getLogger(UserController::class.java)

    @PostMapping
    fun create(
        @RequestBody @Valid request: Request<UserRequest>,
        uriBuilder: UriComponentsBuilder
    ): ResponseEntity<Response<UserResponse>> {
        val observability = Observability(email = request.data.email)
        logger.info("class: ${javaClass.simpleName}, Start process create user, $observability")

        val user = userService.create(request.data.toDomain(), observability)
        val uri = uriBuilder.path("/users/${user.id}").build().toUri()

        logger.info("class: ${javaClass.simpleName}, End process create user, $observability")

        return ResponseEntity.created(uri).body(Response(user.toResponse()))
    }

    @GetMapping
    fun findAll(): ResponseEntity<Response<List<UserResponse>>> {
        val observability = Observability()
        logger.info("class: ${javaClass.simpleName}, Start process list user, $observability")

        val listUser = userService.findAll(observability)

        logger.info("class: ${javaClass.simpleName}, End process list user, $observability")

        return ResponseEntity.ok(Response(listUser.map { it.toResponse() }))
    }

    @GetMapping(value = ["/{id}"])
    fun findByid(@PathVariable id: Long): ResponseEntity<Response<UserResponse>> {
        val observability = Observability(userId = id)

        logger.info("class: ${javaClass.simpleName}, Start process userId, $observability")

        val user = userService.findById(id, observability)

        logger.info("class: ${javaClass.simpleName}, End process userId, $observability")

        return ResponseEntity.ok(Response(user.toResponse()))
    }
}