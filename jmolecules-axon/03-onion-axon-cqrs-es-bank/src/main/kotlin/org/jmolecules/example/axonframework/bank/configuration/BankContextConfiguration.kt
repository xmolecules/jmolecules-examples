package org.jmolecules.example.axonframework.bank.configuration

import org.jmolecules.example.axonframework.bank.application.port.out.MoneyTransferIdGenerator
import org.jmolecules.example.axonframework.bank.domain.moneytransfer.type.MoneyTransferId
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import java.util.*

@Configuration
@ComponentScan(basePackages = ["org.jmolecules.example.axonframework.bank"])
class BankContextConfiguration {
  @Bean
  fun randomGenerator(): MoneyTransferIdGenerator {
    return object : MoneyTransferIdGenerator {
      override fun get(): MoneyTransferId = MoneyTransferId.of(UUID.randomUUID().toString())
    }
  }
}