package org.jmolecules.example.axonframework.infrastructure.adapter.query

import org.axonframework.queryhandling.QueryHandler
import org.jmolecules.architecture.cqrs.annotation.QueryModel
import org.jmolecules.event.annotation.DomainEventHandler
import org.jmolecules.example.axonframework.core.model.moneytransfer.event.MoneyTransferCancelledEvent
import org.jmolecules.example.axonframework.core.model.moneytransfer.event.MoneyTransferCompletedEvent
import org.jmolecules.example.axonframework.core.model.moneytransfer.event.MoneyTransferRequestedEvent
import org.jmolecules.example.axonframework.core.model.moneytransfer.query.MoneyTransferSummariesForBankAccountQuery
import org.jmolecules.example.axonframework.core.model.moneytransfer.query.MoneyTransferSummary
import org.jmolecules.example.axonframework.core.model.moneytransfer.query.MoneyTransferSummaryByIdQuery
import org.jmolecules.example.axonframework.core.model.moneytransfer.query.MoneyTransferSummaryProjection
import org.jmolecules.example.axonframework.core.port.storage.MoneyTransferSummaryRepository
import java.util.*

@QueryModel
class MoneyTransferSummaryProjectionImpl(
  private val repository: MoneyTransferSummaryRepository
) : MoneyTransferSummaryProjection {

  @QueryHandler
  override fun query(query: MoneyTransferSummaryByIdQuery): Optional<MoneyTransferSummary> {
    return repository.findById(query.moneyTransferId)
  }

  @QueryHandler
  override fun query(query: MoneyTransferSummariesForBankAccountQuery): List<MoneyTransferSummary> {
    return repository.findByAccountId(query.accountId)
  }

  @QueryHandler
  fun query(): List<MoneyTransferSummary> {
    return repository.findAll()
  }


  @DomainEventHandler(namespace = "axon.bank", name = "MoneyTransferRequestedEvent")
  fun on(evt: MoneyTransferRequestedEvent) {
    repository.save(
      MoneyTransferSummary(
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
    repository.findById(evt.moneyTransferId).ifPresent {
      repository.save(it.copy(success = true))
    }
  }

  @DomainEventHandler(namespace = "axon.bank", name = "MoneyTransferCancelledEvent")
  fun on(evt: MoneyTransferCancelledEvent) {
    repository.findById(evt.moneyTransferId).ifPresent {
      repository.save(it.copy(success = false, errorMessage = evt.reason))
    }
  }
}
