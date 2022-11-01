package org.jmolecules.example.axonframework.infrastructure.rest

import org.jmolecules.example.axonframework.domain.api.exception.InsufficientBalanceException
import org.jmolecules.example.axonframework.domain.api.exception.MaximumBalanceExceededException
import org.jmolecules.example.axonframework.domain.api.exception.MoneyTransferNotFoundException
import org.springdoc.core.GroupedOpenApi
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@Configuration
@ControllerAdvice
class RestConfiguration {

    @ExceptionHandler(
        IllegalArgumentException::class,
        MaximumBalanceExceededException::class,
        InsufficientBalanceException::class,
    )
    fun badRequest(exception: Exception): ResponseEntity<ErrorDto> {
        return ResponseEntity.badRequest().body(
            ErrorDto(exception.message ?: "")
        )
    }

    @ExceptionHandler(
        MoneyTransferNotFoundException::class,
    )
    fun notFound(exception: Exception): ResponseEntity<ErrorDto> {
        return ResponseEntity.notFound().build()
    }

    @Bean
    fun bankApi(): GroupedOpenApi =
        GroupedOpenApi.builder()
            .group("Bank Account")
            .pathsToMatch("/rest/bank-account/**")
            .packagesToScan("org.jmolecules.example.axonframework.infrastructure.rest")
            .build()

    @Bean
    fun moneyTransferApi(): GroupedOpenApi =
        GroupedOpenApi.builder()
            .group("Money Transfer")
            .pathsToMatch("/rest/money-transfer/**")
            .packagesToScan("org.jmolecules.example.axonframework.infrastructure.rest")
            .build()

    @Bean
    fun atmApi(): GroupedOpenApi =
        GroupedOpenApi.builder()
            .group("ATM")
            .pathsToMatch("/rest/atm/**")
            .packagesToScan("org.jmolecules.example.axonframework.infrastructure.rest")
            .build()


    /**
     * Error DTO to return message details to the client.
     */
    data class ErrorDto(
        val message: String
    )
}
