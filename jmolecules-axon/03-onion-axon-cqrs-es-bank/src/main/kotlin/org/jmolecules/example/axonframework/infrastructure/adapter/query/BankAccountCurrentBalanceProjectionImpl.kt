package org.jmolecules.example.axonframework.infrastructure.adapter.query

import org.axonframework.queryhandling.QueryHandler
import org.jmolecules.architecture.cqrs.annotation.QueryModel
import org.jmolecules.event.annotation.DomainEventHandler
import org.jmolecules.example.axonframework.core.model.bankaccount.event.BankAccountCreatedEvent
import org.jmolecules.example.axonframework.core.model.bankaccount.event.MoneyDepositedEvent
import org.jmolecules.example.axonframework.core.model.bankaccount.event.MoneyWithdrawnEvent
import org.jmolecules.example.axonframework.core.model.bankaccount.query.BankAccountCurrentBalance
import org.jmolecules.example.axonframework.core.model.bankaccount.query.BankAccountCurrentBalanceProjection
import org.jmolecules.example.axonframework.core.model.bankaccount.query.BankAccountCurrentBalanceQuery
import org.jmolecules.example.axonframework.core.model.moneytransfer.event.MoneyTransferredEvent
import org.jmolecules.example.axonframework.core.port.storage.BankAccountCurrentBalanceRepository
import java.util.*

@QueryModel
class BankAccountCurrentBalanceProjectionImpl(
  private val repository: BankAccountCurrentBalanceRepository
) : BankAccountCurrentBalanceProjection {

  @QueryHandler
  override fun query(query: BankAccountCurrentBalanceQuery): Optional<BankAccountCurrentBalance> {
    return repository.findById(query.accountId)
  }

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
