package org.jmolecules.example.axonframework.infrastructure.adapter.out.readmodel.bankaccount

import org.jmolecules.event.annotation.DomainEventHandler
import org.jmolecules.example.axonframework.core.model.bankaccount.event.BankAccountCreatedEvent
import org.jmolecules.example.axonframework.core.model.bankaccount.event.MoneyDepositedEvent
import org.jmolecules.example.axonframework.core.model.bankaccount.event.MoneyWithdrawnEvent
import org.jmolecules.example.axonframework.core.model.bankaccount.read.BankAccountCurrentBalance
import org.jmolecules.example.axonframework.core.model.moneytransfer.event.MoneyTransferredEvent
import org.jmolecules.example.axonframework.core.port.out.repository.BankAccountCurrentBalanceRepository
import org.springframework.stereotype.Component

@Component
class BankAccountCurrentBalanceProjector(
  private val repository: BankAccountCurrentBalanceRepository
) {

  @DomainEventHandler(namespace = "axon.bank", name = "BankAccountCreatedEvent")
  fun on(evt: BankAccountCreatedEvent) {
    repository.save(BankAccountCurrentBalance(accountId = evt.accountId, currentBalance = evt.initialBalance))
  }

  @DomainEventHandler(namespace = "axon.bank", name = "MoneyWithdrawnEvent")
  fun on(evt: MoneyWithdrawnEvent) {
    repository.findById(evt.accountId).ifPresent {
      repository.save(it.copy(currentBalance = it.currentBalance - evt.amount))
    }
  }

  @DomainEventHandler(namespace = "axon.bank", name = "MoneyDepositedEvent")
  fun on(evt: MoneyDepositedEvent) {
    repository.findById(evt.accountId).ifPresent {
      repository.save(it.copy(currentBalance = it.currentBalance + evt.amount))
    }
  }

  @DomainEventHandler(namespace = "axon.bank", name = "MoneyTransferredEvent")
  fun on(evt: MoneyTransferredEvent) {
    repository.findById(evt.sourceAccountId).ifPresent {
      repository.save(it.copy(currentBalance = it.currentBalance - evt.amount))
    }
    repository.findById(evt.targetAccountId).ifPresent {
      repository.save(it.copy(currentBalance = it.currentBalance + evt.amount))
    }
  }
}
