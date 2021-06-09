package com.sesame.apicleanuper

import com.sesame.apicleanuper.newyorkhospital.CannotFindUrlException
import com.sesame.apicleanuper.newyorkhospital.NewYorkHospitalClientException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class CustomExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(value = [(NewYorkHospitalClientException::class), (CannotFindUrlException::class)])
    fun handleException(
        exception: NewYorkHospitalClientException,
        request: WebRequest
    ): ResponseEntity<String> {
        return ResponseEntity(
            exception.message,
            HttpStatus.SERVICE_UNAVAILABLE
        )
    }
}