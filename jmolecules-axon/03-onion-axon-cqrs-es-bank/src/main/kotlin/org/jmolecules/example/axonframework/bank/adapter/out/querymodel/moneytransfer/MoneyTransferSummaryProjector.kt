package org.jmolecules.example.axonframework.bank.adapter.out.querymodel.moneytransfer

import org.jmolecules.architecture.cqrs.annotation.QueryModel
import org.jmolecules.event.annotation.DomainEventHandler
import org.jmolecules.example.axonframework.bank.domain.moneytransfer.event.MoneyTransferCancelledEvent
import org.jmolecules.example.axonframework.bank.domain.moneytransfer.event.MoneyTransferCompletedEvent
import org.jmolecules.example.axonframework.bank.domain.moneytransfer.event.MoneyTransferRequestedEvent
import org.jmolecules.example.axonframework.bank.application.port.out.repository.MoneyTransferSummaryRepository
import org.jmolecules.example.axonframework.bank.domain.moneytransfer.query.BankAccountMoneyTransfer
import org.springframework.stereotype.Component

@Component
@QueryModel // currently needed to map handler!
class MoneyTransferSummaryProjector(
  private val repository: MoneyTransferSummaryRepository
) {

  @DomainEventHandler(namespace = "axon.bank", name = "MoneyTransferRequestedEvent")
  fun on(evt: MoneyTransferRequestedEvent) {
    repository.save(
      BankAccountMoneyTransfer(
        moneyTransferId = evt.moneyTransferId,
        sourceAccountId = evt.sourceAccountId,
        targetAccountId = evt.targetAccountId,
        amount = evt.amount,
        success = false
      )
    )
  }

  @DomainEventHandler(namespace = "axon.bank", name = "MoneyTransferCompletedEvent")
  fun on(evt: MoneyTransferCompletedEvent) {
    repository.findById(evt.moneyTransferId).ifPresent { found ->
      repository.save(found.complete())
    }
  }

  @DomainEventHandler(namespace = "axon.bank", name = "MoneyTransferCancelledEvent")
  fun on(evt: MoneyTransferCancelledEvent) {
    repository.findById(evt.moneyTransferId).ifPresent { found ->
      repository.save(found.cancel(evt.reason))
    }
  }
}
