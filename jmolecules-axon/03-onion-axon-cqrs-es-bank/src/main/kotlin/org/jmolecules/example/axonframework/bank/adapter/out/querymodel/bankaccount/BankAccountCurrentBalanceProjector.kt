package org.jmolecules.example.axonframework.bank.adapter.out.querymodel.bankaccount

import org.jmolecules.architecture.cqrs.QueryModel
import org.jmolecules.event.annotation.DomainEventHandler
import org.jmolecules.example.axonframework.bank.application.port.out.repository.BankAccountCurrentBalanceRepository
import org.jmolecules.example.axonframework.bank.domain.bankaccount.event.BankAccountCreatedEvent
import org.jmolecules.example.axonframework.bank.domain.bankaccount.event.MoneyDepositedEvent
import org.jmolecules.example.axonframework.bank.domain.bankaccount.event.MoneyWithdrawnEvent
import org.jmolecules.example.axonframework.bank.domain.bankaccount.query.BankAccountCurrentBalance
import org.jmolecules.example.axonframework.bank.domain.moneytransfer.event.MoneyTransferredEvent
import org.springframework.stereotype.Component

@Component
@QueryModel // FIXME: currently needed to map handler!
class BankAccountCurrentBalanceProjector(
  private val repository: BankAccountCurrentBalanceRepository
) {

  @DomainEventHandler(namespace = "axon.bank", name = "BankAccountCreatedEvent")
  fun on(evt: BankAccountCreatedEvent) {
    repository.save(BankAccountCurrentBalance(accountId = evt.accountId, balance = evt.initialBalance))
  }

  @DomainEventHandler(namespace = "axon.bank", name = "MoneyWithdrawnEvent")
  fun on(evt: MoneyWithdrawnEvent) {
    repository.findById(evt.accountId).ifPresent { found ->
      repository.save(found.decreaseBalance(evt.amount))
    }
  }

  @DomainEventHandler(namespace = "axon.bank", name = "MoneyDepositedEvent")
  fun on(evt: MoneyDepositedEvent) {
    repository.findById(evt.accountId).ifPresent { found ->
      repository.save(found.increaseBalance(evt.amount))
    }
  }

  @DomainEventHandler(namespace = "axon.bank", name = "MoneyTransferredEvent")
  fun on(evt: MoneyTransferredEvent) {
    repository.findById(evt.sourceAccountId).ifPresent { found ->
      repository.save(found.decreaseBalance(evt.amount))
    }
    repository.findById(evt.targetAccountId).ifPresent { found ->
      repository.save(found.increaseBalance(evt.amount))
    }
  }
}
