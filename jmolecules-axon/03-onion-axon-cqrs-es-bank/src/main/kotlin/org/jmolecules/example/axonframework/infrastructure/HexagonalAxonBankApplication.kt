package org.jmolecules.example.axonframework.infrastructure

import org.jmolecules.example.axonframework.bank.configuration.BankContextConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import

fun main(args: Array<String>) {
  runApplication<HexagonalAxonBankApplication>(*args).let { }
}

@SpringBootApplication
@Import(
  BankContextConfiguration::class,
  InfrastructureConfiguration::class
)
class HexagonalAxonBankApplication
