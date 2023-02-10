package org.jmolecules.example.axonframework.infrastructure

import org.jmolecules.example.axonframework.core.application.ApplicationConfiguration
import org.jmolecules.example.axonframework.infrastructure.adapter.InfrastructureConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import

fun main(args: Array<String>) {
  runApplication<CleanAxonBankApplication>(*args).let { }
}

@SpringBootApplication
@Import(
  ApplicationConfiguration::class,
  InfrastructureConfiguration::class
)
class CleanAxonBankApplication
