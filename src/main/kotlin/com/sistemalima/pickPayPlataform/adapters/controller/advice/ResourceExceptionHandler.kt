package com.sistemalima.pickPayPlataform.adapters.controller.advice

import com.sistemalima.pickPayPlataform.adapters.controller.advice.entity.ErrorView
import com.sistemalima.pickPayPlataform.domain.exceptions.BusinessException
import com.sistemalima.pickPayPlataform.domain.exceptions.ResourceEntityNotFoundException
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.io.IOException

@RestControllerAdvice
class ResourceExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handlerMethodArgumentNotValidException(exception: MethodArgumentNotValidException, request: HttpServletRequest): ErrorView {

        val errorMessage = HashMap<String, String?>()
        exception.bindingResult.fieldErrors.forEach { error -> errorMessage[error.field] = error.defaultMessage }

        return ErrorView(
            status = HttpStatus.BAD_REQUEST.value(),
            error = HttpStatus.BAD_REQUEST.name,
            message = errorMessage.toString(),
            path = request.servletPath
        )
    }

    @ExceptionHandler(BusinessException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handlerBusinessException(exception: BusinessException, request: HttpServletRequest): ErrorView {

        return ErrorView(
            status = HttpStatus.BAD_REQUEST.value(),
            error = HttpStatus.BAD_REQUEST.name,
            message = exception.message.toString(),
            path = request.servletPath
        )
    }

    @ExceptionHandler(ResourceEntityNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handlerResourceEntityNotFoundException(exception: ResourceEntityNotFoundException, request: HttpServletRequest): ErrorView {

        return ErrorView(
            status = HttpStatus.NOT_FOUND.value(),
            error = HttpStatus.NOT_FOUND.name,
            message = exception.message.toString(),
            path = request.servletPath
        )
    }

    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handlerException(exception: Exception, request: HttpServletRequest): ErrorView {

        return ErrorView(
            status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
            error = HttpStatus.INTERNAL_SERVER_ERROR.name,
            message = exception.message.toString(),
            path = request.servletPath
        )
    }

    @ExceptionHandler(IOException::class)
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    fun handlerIoException(exception: IOException, request: HttpServletRequest): ErrorView {

        return ErrorView(
            status = HttpStatus.BAD_GATEWAY.value(),
            error = HttpStatus.BAD_GATEWAY.name,
            message = exception.message.toString(),
            path = request.servletPath
        )
    }
}