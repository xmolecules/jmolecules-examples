package org.jmolecules.example.axonframework.bank.adapter.`in`.rest

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import org.axonframework.axonserver.connector.command.AxonServerRemoteCommandHandlingException
import org.jmolecules.ddd.annotation.ValueObject
import org.jmolecules.example.axonframework.bank.domain.bankaccount.type.InsufficientBalanceException
import org.jmolecules.example.axonframework.bank.domain.bankaccount.type.MaximumBalanceExceededException
import org.jmolecules.example.axonframework.bank.domain.moneytransfer.type.MoneyTransferNotFoundException
import org.springdoc.core.GroupedOpenApi
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@Configuration
@ControllerAdvice
@OpenAPIDefinition(
  info = Info(
    title = "CQRS/ES Axon Bank",
    description = "Example implementation of a simple bank application refining Clean Architecture, CQRS/ES and Axon Framework."
  )
)
class RestConfiguration {

  @ExceptionHandler(
    IllegalArgumentException::class,
    MaximumBalanceExceededException::class,
    InsufficientBalanceException::class,
    AxonServerRemoteCommandHandlingException::class
  )
  fun badRequest(exception: Exception): ResponseEntity<ErrorDto> {
    return when (exception) {
      is AxonServerRemoteCommandHandlingException -> ResponseEntity.badRequest().body(
        ErrorDto(exception.exceptionDescriptions.toString())
      )
      else -> ResponseEntity.badRequest().body(
        ErrorDto(exception.message ?: "")
      )
    }
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
      .packagesToScan(this::class.java.`package`.name)
      .build()

  @Bean
  fun moneyTransferApi(): GroupedOpenApi =
    GroupedOpenApi.builder()
      .group("Money Transfer")
      .pathsToMatch("/rest/money-transfer/**")
      .packagesToScan(this::class.java.`package`.name)
      .build()

  @Bean
  fun atmApi(): GroupedOpenApi =
    GroupedOpenApi.builder()
      .group("ATM")
      .pathsToMatch("/rest/atm/**")
      .packagesToScan(this::class.java.`package`.name)
      .build()


  /**
   * Error DTO to return message details to the client.
   */
  @ValueObject
  data class ErrorDto(
    val message: String
  )
}
