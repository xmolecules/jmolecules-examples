package org.jmolecules.example.axonframework.infrastructure.adapter.aggregate

import org.axonframework.modelling.command.AggregateLifecycle
import org.jmolecules.architecture.cqrs.annotation.CommandHandler
import org.jmolecules.ddd.annotation.AggregateRoot
import org.jmolecules.ddd.annotation.Identity
import org.jmolecules.event.annotation.DomainEventHandler
import org.jmolecules.event.annotation.DomainEventPublisher
import org.jmolecules.example.axonframework.core.model.bankaccount.BankAccount
import org.jmolecules.example.axonframework.core.model.bankaccount.BankAccountCreationVerificationResult.*
import org.jmolecules.example.axonframework.core.model.bankaccount.InsufficientBalanceException
import org.jmolecules.example.axonframework.core.model.bankaccount.MaximumBalanceExceededException
import org.jmolecules.example.axonframework.core.model.bankaccount.command.CreateBankAccountCommand
import org.jmolecules.example.axonframework.core.model.bankaccount.command.DepositMoneyCommand
import org.jmolecules.example.axonframework.core.model.bankaccount.command.WithdrawMoneyCommand
import org.jmolecules.example.axonframework.core.model.bankaccount.event.BankAccountCreatedEvent
import org.jmolecules.example.axonframework.core.model.bankaccount.event.MoneyDepositedEvent
import org.jmolecules.example.axonframework.core.model.bankaccount.event.MoneyWithdrawnEvent
import org.jmolecules.example.axonframework.core.model.moneytransfer.command.*
import org.jmolecules.example.axonframework.core.model.moneytransfer.event.MoneyTransferCancelledEvent
import org.jmolecules.example.axonframework.core.model.moneytransfer.event.MoneyTransferCompletedEvent
import org.jmolecules.example.axonframework.core.model.moneytransfer.event.MoneyTransferReceivedEvent
import org.jmolecules.example.axonframework.core.model.moneytransfer.event.MoneyTransferRequestedEvent

@AggregateRoot
class BankAccountAggregate {

  companion object {

    @DomainEventPublisher(
      publishes = "axon.bank.BankAccountCreatedEvent", type = DomainEventPublisher.PublisherType.INTERNAL
    )
    @CommandHandler(namespace = "axon.bank", name = "CreateBankAccountCommand")
    @JvmStatic
    fun handle(cmd: CreateBankAccountCommand): BankAccountAggregate {
      return when (val result = BankAccount.canCreateBankAccount(cmd.initialBalance)) {
        is ValidBalanceAmountVerificationResult -> {
          AggregateLifecycle.apply(
            BankAccountCreatedEvent(
              accountId = cmd.accountId,
              initialBalance = cmd.initialBalance
            )
          )
          BankAccountAggregate()
        }

        is InsufficientBalanceAmountVerificationResult -> throw InsufficientBalanceException(
          accountId = cmd.accountId,
          withdrawAmount = null,
          currentBalance = cmd.initialBalance,
          minimumBalance = result.minimumBalance
        )

        is BalanceAmountExceededVerificationResult -> throw MaximumBalanceExceededException(
          accountId = cmd.accountId,
          depositAmount = null,
          currentBalance = cmd.initialBalance,
          maximumBalance = result.maximumBalance
        )
      }
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
    bankAccount.requireIncreaseAllowed(cmd.amount)
    AggregateLifecycle.apply(
      MoneyDepositedEvent(
        accountId = cmd.accountId,
        amount = cmd.amount
      )
    )
  }

  @DomainEventPublisher(
    publishes = "axon.bank.MoneyWithdrawnEvent", type = DomainEventPublisher.PublisherType.INTERNAL
  )
  @CommandHandler(namespace = "axon.bank", name = "WithdrawMoneyCommand")
  fun handle(cmd: WithdrawMoneyCommand) {
    bankAccount.requireDecreaseAllowed(cmd.amount)
    AggregateLifecycle.apply(
      MoneyWithdrawnEvent(
        accountId = cmd.accountId,
        amount = cmd.amount
      )
    )
  }

  @DomainEventPublisher(
    publishes = "axon.bank.MoneyTransferRequestedEvent", type = DomainEventPublisher.PublisherType.INTERNAL
  )
  @CommandHandler(namespace = "axon.bank", name = "RequestMoneyTransferCommand")
  fun handle(cmd: RequestMoneyTransferCommand) {
    AggregateLifecycle.apply(
      MoneyTransferRequestedEvent(
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
    bankAccount.requireIncreaseAllowed(cmd.amount)
    AggregateLifecycle.apply(
      MoneyTransferReceivedEvent(
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
    if (!bankAccount.checkMoneyTransfer(cmd.moneyTransferId)) {
      throw MoneyTransferNotFoundException(accountId = cmd.sourceAccountId, moneyTransferId = cmd.moneyTransferId)
    }
    val amount = bankAccount.getAmountForMoneyTransfer(cmd.moneyTransferId)!!
    AggregateLifecycle.apply(
      MoneyTransferCompletedEvent(
        moneyTransferId = cmd.moneyTransferId,
        sourceAccountId = cmd.sourceAccountId,
        amount = amount
      )
    )
  }

  @DomainEventPublisher(
    publishes = "axon.bank.MoneyTransferCancelledEvent", type = DomainEventPublisher.PublisherType.INTERNAL
  )
  @CommandHandler(namespace = "axon.bank", name = "ReceiveMoneyTransferCommand")
  fun handle(cmd: CancelMoneyTransferCommand) {
    if (!bankAccount.checkMoneyTransfer(cmd.moneyTransferId)) {
      throw MoneyTransferNotFoundException(
        accountId = cmd.sourceAccountId,
        moneyTransferId = cmd.moneyTransferId
      )
    }
    AggregateLifecycle.apply(
      MoneyTransferCancelledEvent(
        moneyTransferId = cmd.moneyTransferId,
        reason = cmd.reason
      )
    )
  }

  // ----------------------------------------------------------
  @DomainEventHandler(namespace = "axon.bank", name = "BankAccountCreatedEvent")
  fun on(evt: BankAccountCreatedEvent) {
    accountId = evt.accountId.value
    bankAccount = BankAccount.createBankAccount(accountId = evt.accountId, initialBalance = evt.initialBalance)
  }

  @DomainEventHandler(namespace = "axon.bank", name = "MoneyDepositedEvent")
  fun on(evt: MoneyDepositedEvent) {
    bankAccount.depositMoney(amount = evt.amount)
  }

  @DomainEventHandler(namespace = "axon.bank", name = "MoneyWithdrawnEvent")
  fun on(evt: MoneyWithdrawnEvent) {
    bankAccount.withdrawMoney(amount = evt.amount)
  }

  @DomainEventHandler(namespace = "axon.bank", name = "MoneyTransferRequestedEvent")
  fun on(evt: MoneyTransferRequestedEvent) {
    bankAccount.initMoneyTransfer(moneyTransferId = evt.moneyTransferId, amount = evt.amount)
  }

  @DomainEventHandler(namespace = "axon.bank", name = "MoneyTransferCompletedEvent")
  fun on(evt: MoneyTransferCompletedEvent) {
    bankAccount.completeMoneyTransfer(moneyTransferId = evt.moneyTransferId)
  }

  @DomainEventHandler(namespace = "axon.bank", name = "MoneyTransferReceivedEvent")
  fun on(evt: MoneyTransferReceivedEvent) {
    bankAccount.receiveMoneyTransfer(amount = evt.amount)
  }

  @DomainEventHandler(namespace = "axon.bank", name = "MoneyTransferCancelledEvent")
  fun on(evt: MoneyTransferCancelledEvent) {
    bankAccount.cancelMoneyTransfer(moneyTransferId = evt.moneyTransferId)
  }
}
