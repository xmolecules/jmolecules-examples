package org.jmolecules.example.axonframework.bank.configuration

import org.jmolecules.example.axonframework.bank.application.port.out.MoneyTransferIdGenerator
import org.jmolecules.example.axonframework.bank.application.port.out.command.AtmCommandPort
import org.jmolecules.example.axonframework.bank.application.port.out.command.BankAccountCommandPort
import org.jmolecules.example.axonframework.bank.application.port.out.command.MoneyTransferCommandPort
import org.jmolecules.example.axonframework.bank.application.port.out.query.BankAccountQueryPort
import org.jmolecules.example.axonframework.bank.application.port.out.query.MoneyTransferQueryPort
import org.jmolecules.example.axonframework.bank.application.usecase.*
import org.jmolecules.example.axonframework.bank.domain.moneytransfer.type.MoneyTransferId
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import java.util.*

@Configuration
@ComponentScan(basePackages = ["org.jmolecules.example.axonframework.bank"])
class BankContextConfiguration {

  @Bean
  fun randomGenerator()=MoneyTransferIdGenerator { MoneyTransferId.of(UUID.randomUUID().toString()) }

  @Bean
  fun createBankAccountUseCase(bankAccountPort: BankAccountCommandPort) =
    CreateBankAccountUseCase(bankAccountPort = bankAccountPort)

  @Bean
  fun depositMoneyUseCase(atmOutPort: AtmCommandPort) = DepositMoneyUseCase(atmOutPort = atmOutPort)

  @Bean
  fun retrieveAccountInformationUseCase(bankAccountQueryPort: BankAccountQueryPort) =
    RetrieveAccountInformationUseCase(bankAccountQueryPort = bankAccountQueryPort)

  @Bean
  fun transferMoneyUseCase(
    moneyTransferCommandPort: MoneyTransferCommandPort,
    moneyTransferQueryPort: MoneyTransferQueryPort,
  ) = TransferMoneyUseCase(
    moneyTransferCommandPort = moneyTransferCommandPort,
    moneyTransferQueryPort = moneyTransferQueryPort
  )

  @Bean
  fun withdrawMoneyUseCase(atmOutPort: AtmCommandPort) = WithdrawMoneyUseCase(atmOutPort = atmOutPort)

}
