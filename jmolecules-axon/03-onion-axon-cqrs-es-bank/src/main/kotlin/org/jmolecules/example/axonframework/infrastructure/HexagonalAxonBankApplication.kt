package org.jmolecules.example.axonframework.infrastructure

import org.jmolecules.architecture.onion.classical.InfrastructureRing
import org.jmolecules.example.axonframework.bank.configuration.BankContextConfiguration
import org.jmolecules.example.axonframework.infrastructure.configuration.InfrastructureConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import

/**
 * Starts the application.
 */
fun main(args: Array<String>) {
  runApplication<HexagonalAxonBankApplication>(*args).let { }
}

@InfrastructureRing
@SpringBootApplication
@Import(
  BankContextConfiguration::class,
  InfrastructureConfiguration::class
)
class HexagonalAxonBankApplication
