package org.jmolecules.example.axonframework.infrastructure

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

fun main(args: Array<String>) {
  runApplication<AxonBankApplication>(*args).let { }
}

@SpringBootApplication
class AxonBankApplication