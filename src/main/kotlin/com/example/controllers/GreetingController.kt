package com.example.controllers

import controllers.common.ApiError
import org.springframework.http.HttpStatus
import org.springframework.validation.BindException
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("greeting")
class GreetingController {

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  fun greeting(@Valid criteria: RequestCriteria?): Greeting {
    val hour: Int = criteria?.hour!!
    return if (hour < 5) {
      Greeting("good night")
    } else if (hour in 5..15) {
      Greeting("good morning")
    } else if (16 <= hour) {
      Greeting("good night")
    } else {
      throw IllegalArgumentException("bean validation doesn't work")
    }
  }

  @ExceptionHandler(BindException::class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  fun handleBindingException(e: BindException): ApiError {
    var apiErrorDetails: List<ApiError> = e.bindingResult.fieldErrors.map {
      ApiError(
        it.defaultMessage,
        it.field
      )
    }
    val apiError = ApiError("request is invalid", "", apiErrorDetails)
    return apiError
  }
}