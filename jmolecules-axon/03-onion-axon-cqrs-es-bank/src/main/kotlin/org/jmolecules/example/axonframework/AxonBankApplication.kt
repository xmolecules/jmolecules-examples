package org.jmolecules.example.axonframework

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication


@SpringBootApplication
@OpenAPIDefinition
class AxonBankApplication

fun main(args: Array<String>) {
    runApplication<AxonBankApplication>(*args).let { Unit }
}
