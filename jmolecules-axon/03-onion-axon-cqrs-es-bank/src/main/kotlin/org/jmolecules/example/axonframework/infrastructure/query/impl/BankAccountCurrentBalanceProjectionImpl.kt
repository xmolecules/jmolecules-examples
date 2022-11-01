package org.jmolecules.example.axonframework.infrastructure.query.impl

import org.axonframework.queryhandling.QueryHandler
import org.jmolecules.architecture.cqrs.annotation.QueryModel
import org.jmolecules.event.annotation.DomainEventHandler
import org.jmolecules.example.axonframework.domain.api.query.BankAccountCurrentBalanceQuery
import org.jmolecules.example.axonframework.domain.api.query.BankAccountCurrentBalance
import org.jmolecules.example.axonframework.domain.event.BankAccountCreatedEvent
import org.jmolecules.example.axonframework.domain.event.atm.MoneyDepositedEvent
import org.jmolecules.example.axonframework.domain.event.atm.MoneyWithdrawnEvent
import org.jmolecules.example.axonframework.domain.event.transfer.MoneyTransferredEvent
import org.jmolecules.example.axonframework.domain.model.bank.BankAccountCurrentBalanceProjection
import org.jmolecules.example.axonframework.domain.model.bank.BankAccountCurrentBalanceRepository
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
