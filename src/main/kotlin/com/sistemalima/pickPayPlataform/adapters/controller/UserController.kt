package com.sistemalima.pickPayPlataform.adapters.controller

import com.sistemalima.pickPayPlataform.adapters.controller.entity.Request
import com.sistemalima.pickPayPlataform.adapters.controller.entity.Response
import com.sistemalima.pickPayPlataform.adapters.controller.entity.UserRequest
import com.sistemalima.pickPayPlataform.adapters.controller.entity.UserResponse
import com.sistemalima.pickPayPlataform.adapters.controller.mapper.UserRequestMapper.toDomain
import com.sistemalima.pickPayPlataform.application.ports.inputs.UserService
import com.sistemalima.pickPayPlataform.domain.mapper.UserMapper.toResponse
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = ["/users"], produces = [MediaType.APPLICATION_JSON_VALUE])
class UserController(
    private val userService: UserService
) {

    @PostMapping
    fun create(@RequestBody request: Request<UserRequest>): Response<UserResponse> {
        val user = userService.execute(request.data.toDomain())
        return Response(user.toResponse())
    }
}