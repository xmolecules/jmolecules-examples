package org.jmolecules.example.axonframework.bank.adapter.out.commandmodel

import org.axonframework.modelling.command.AggregateLifecycle
import org.jmolecules.architecture.cqrs.CommandHandler
import org.jmolecules.ddd.annotation.AggregateRoot
import org.jmolecules.ddd.annotation.Identity
import org.jmolecules.event.annotation.DomainEventHandler
import org.jmolecules.event.annotation.DomainEventPublisher
import org.jmolecules.example.axonframework.bank.adapter.out.commandmodel.atm.DepositMoneyCommand
import org.jmolecules.example.axonframework.bank.adapter.out.commandmodel.atm.WithdrawMoneyCommand
import org.jmolecules.example.axonframework.bank.adapter.out.commandmodel.bankaccount.CreateBankAccountCommand
import org.jmolecules.example.axonframework.bank.adapter.out.commandmodel.moneytransfer.CancelMoneyTransferCommand
import org.jmolecules.example.axonframework.bank.adapter.out.commandmodel.moneytransfer.CompleteMoneyTransferCommand
import org.jmolecules.example.axonframework.bank.adapter.out.commandmodel.moneytransfer.ReceiveMoneyTransferCommand
import org.jmolecules.example.axonframework.bank.adapter.out.commandmodel.moneytransfer.RequestMoneyTransferCommand
import org.jmolecules.example.axonframework.bank.adapter.out.querymodel.bankaccount.BankAccountCurrentBalanceQuery
import org.jmolecules.example.axonframework.bank.domain.bankaccount.command.BankAccount
import org.jmolecules.example.axonframework.bank.domain.bankaccount.event.BankAccountCreatedEvent
import org.jmolecules.example.axonframework.bank.domain.bankaccount.event.MoneyDepositedEvent
import org.jmolecules.example.axonframework.bank.domain.bankaccount.event.MoneyWithdrawnEvent
import org.jmolecules.example.axonframework.bank.domain.moneytransfer.event.MoneyTransferCancelledEvent
import org.jmolecules.example.axonframework.bank.domain.moneytransfer.event.MoneyTransferCompletedEvent
import org.jmolecules.example.axonframework.bank.domain.moneytransfer.event.MoneyTransferReceivedEvent
import org.jmolecules.example.axonframework.bank.domain.moneytransfer.event.MoneyTransferRequestedEvent

@AggregateRoot
class BankAccountAggregate {

  companion object {
    @DomainEventPublisher(
      publishes = "axon.bank.BankAccountCreatedEvent", type = DomainEventPublisher.PublisherType.INTERNAL
    )
    @CommandHandler(namespace = "axon.bank", name = "CreateBankAccountCommand")
    @JvmStatic
    fun handle(cmd: CreateBankAccountCommand): BankAccountAggregate {
      AggregateLifecycle.apply(
        BankAccount.createAccount(accountId = cmd.accountId, initialBalance = cmd.initialBalance)
      )
      return BankAccountAggregate()
    }
  }

  @Identity
  var accountId: String? = null
    private set

  // domain model
  private lateinit var bankAccount: BankAccount

  @DomainEventPublisher(
    publishes = "axon.bank.MoneyDepositedEvent", type = DomainEventPublisher.PublisherType.INTERNAL
  )
  @CommandHandler(namespace = "axon.bank", name = "DepositMoneyCommand")
  fun handle(cmd: DepositMoneyCommand) {
    AggregateLifecycle.apply(
      bankAccount.depositMoney(cmd.amount)
    )
  }

  @DomainEventPublisher(
    publishes = "axon.bank.MoneyWithdrawnEvent", type = DomainEventPublisher.PublisherType.INTERNAL
  )
  @CommandHandler(namespace = "axon.bank", name = "WithdrawMoneyCommand")
  fun handle(cmd: WithdrawMoneyCommand) {
    AggregateLifecycle.apply(
      bankAccount.withdrawMoney(cmd.amount)
    )
  }

  @DomainEventPublisher(
    publishes = "axon.bank.MoneyTransferRequestedEvent", type = DomainEventPublisher.PublisherType.INTERNAL
  )
  @CommandHandler(namespace = "axon.bank", name = "RequestMoneyTransferCommand")
  fun handle(cmd: RequestMoneyTransferCommand) {
    AggregateLifecycle.apply(
      bankAccount.requestMoneyTransfer(
        moneyTransferId = cmd.moneyTransferId,
        sourceAccountId = cmd.sourceAccountId,
        targetAccountId = cmd.targetAccountId,
        amount = cmd.amount
      )
    )
  }

  @DomainEventPublisher(
    publishes = "axon.bank.MoneyTransferReceivedEvent", type = DomainEventPublisher.PublisherType.INTERNAL
  )
  @CommandHandler(namespace = "axon.bank", name = "ReceiveMoneyTransferCommand")
  fun handle(cmd: ReceiveMoneyTransferCommand) {
    AggregateLifecycle.apply(
      bankAccount.receiveMoneyTransfer(
        moneyTransferId = cmd.moneyTransferId,
        targetAccountId = cmd.targetAccountId,
        amount = cmd.amount
      )
    )
  }

  @DomainEventPublisher(
    publishes = "axon.bank.MoneyTransferCompletedEvent", type = DomainEventPublisher.PublisherType.INTERNAL
  )
  @CommandHandler(namespace = "axon.bank", name = "CompleteMoneyTransferCommand")
  fun handle(cmd: CompleteMoneyTransferCommand) {
    AggregateLifecycle.apply(
      bankAccount.completeMoneyTransfer(moneyTransferId = cmd.moneyTransferId, sourceAccountId = cmd.sourceAccountId)
    )
  }

  @DomainEventPublisher(
    publishes = "axon.bank.MoneyTransferCancelledEvent", type = DomainEventPublisher.PublisherType.INTERNAL
  )
  @CommandHandler(namespace = "axon.bank", name = "ReceiveMoneyTransferCommand")
  fun handle(cmd: CancelMoneyTransferCommand) {
    AggregateLifecycle.apply(
      bankAccount.cancelMoneyTransfer(
        moneyTransferId = cmd.moneyTransferId,
        sourceAccountId = cmd.sourceAccountId,
        rejectionReason = cmd.rejectionReason
      )
    )
  }

  // event sourcing handler to change the state of the commandmodel domain

  @DomainEventHandler(namespace = "axon.bank", name = "BankAccountCreatedEvent")
  fun on(evt: BankAccountCreatedEvent) {
    accountId = evt.accountId.value
    bankAccount = BankAccount.initializeAccount(accountId = evt.accountId, initialBalance = evt.initialBalance)
  }

  @DomainEventHandler(namespace = "axon.bank", name = "MoneyDepositedEvent")
  fun on(evt: MoneyDepositedEvent) {
    bankAccount.increaseBalance(amount = evt.amount)
  }

  @DomainEventHandler(namespace = "axon.bank", name = "MoneyWithdrawnEvent")
  fun on(evt: MoneyWithdrawnEvent) {
    bankAccount.decreaseBalance(amount = evt.amount)
  }

  @DomainEventHandler(namespace = "axon.bank", name = "MoneyTransferRequestedEvent")
  fun on(evt: MoneyTransferRequestedEvent) {
    bankAccount.initializeMoneyTransfer(moneyTransferId = evt.moneyTransferId, amount = evt.amount)
  }

  @DomainEventHandler(namespace = "axon.bank", name = "MoneyTransferCompletedEvent")
  fun on(evt: MoneyTransferCompletedEvent) {
    bankAccount.acknowledgeMoneyTransferCompletion(moneyTransferId = evt.moneyTransferId)
  }

  @DomainEventHandler(namespace = "axon.bank", name = "MoneyTransferReceivedEvent")
  fun on(evt: MoneyTransferReceivedEvent) {
    bankAccount.increaseBalance(amount = evt.amount)
  }

  @DomainEventHandler(namespace = "axon.bank", name = "MoneyTransferCancelledEvent")
  fun on(evt: MoneyTransferCancelledEvent) {
    bankAccount.acknowledgeMoneyTransferCancellation(moneyTransferId = evt.moneyTransferId)
  }
}
