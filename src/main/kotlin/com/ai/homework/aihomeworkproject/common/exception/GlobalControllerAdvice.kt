
package com.ai.homework.aihomeworkproject.common.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus

@ControllerAdvice
class GlobalControllerAdvice {

//    @ExceptionHandler(Exception::class)
//    fun handleAllExceptions(e: Exception): ResponseEntity<ExceptionResponse> {
//        val exceptionName = e::class.simpleName ?: "UnknownException"
//        val message = e.message ?: "No message"
//
//        return ResponseEntity
//            .status(HttpStatus.INTERNAL_SERVER_ERROR)
//            .body(
//                ExceptionResponse(
//                    code = exceptionName,
//                    message = message
//                )
//            )
//    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleValidationException(ex: MethodArgumentNotValidException): ResponseEntity<Map<String, String>> {
        val errors = ex.bindingResult.fieldErrors.associate {
            it.field to (it.defaultMessage ?: "유효하지 않은 입력입니다.")
        }
        return ResponseEntity.badRequest().body(errors)
    }

}
