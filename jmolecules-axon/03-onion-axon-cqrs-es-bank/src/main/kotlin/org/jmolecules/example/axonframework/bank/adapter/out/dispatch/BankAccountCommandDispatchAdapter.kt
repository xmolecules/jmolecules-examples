package org.jmolecules.example.axonframework.bank.adapter.out.dispatch

import org.axonframework.commandhandling.gateway.CommandGateway
import org.jmolecules.architecture.cqrs.CommandDispatcher
import org.jmolecules.architecture.hexagonal.SecondaryAdapter
import org.jmolecules.example.axonframework.bank.application.port.out.MoneyTransferIdGenerator
import org.jmolecules.example.axonframework.bank.domain.bankaccount.type.AccountId
import org.jmolecules.example.axonframework.bank.domain.bankaccount.type.Amount
import org.jmolecules.example.axonframework.bank.domain.bankaccount.type.Balance
import org.jmolecules.example.axonframework.bank.domain.moneytransfer.command.MoneyTransfer
import org.jmolecules.example.axonframework.bank.domain.moneytransfer.type.MoneyTransferId
import org.jmolecules.example.axonframework.bank.domain.moneytransfer.type.RejectionReason
import org.jmolecules.example.axonframework.bank.application.port.out.command.AtmCommandPort
import org.jmolecules.example.axonframework.bank.application.port.out.command.BankAccountCommandPort
import org.jmolecules.example.axonframework.bank.application.port.out.command.MoneyTransferCommandPort
import org.jmolecules.example.axonframework.bank.adapter.out.commandmodel.bankaccount.CreateBankAccountCommand
import org.jmolecules.example.axonframework.bank.adapter.out.commandmodel.atm.DepositMoneyCommand
import org.jmolecules.example.axonframework.bank.adapter.out.commandmodel.atm.WithdrawMoneyCommand
import org.jmolecules.example.axonframework.bank.adapter.out.commandmodel.moneytransfer.CancelMoneyTransferCommand
import org.jmolecules.example.axonframework.bank.adapter.out.commandmodel.moneytransfer.CompleteMoneyTransferCommand
import org.jmolecules.example.axonframework.bank.adapter.out.commandmodel.moneytransfer.ReceiveMoneyTransferCommand
import org.jmolecules.example.axonframework.bank.adapter.out.commandmodel.moneytransfer.RequestMoneyTransferCommand
import org.springframework.stereotype.Component
import java.util.concurrent.CompletableFuture

/**
 * Implementation of the bank account command model dispatch.
 */
@Component
@SecondaryAdapter
class BankAccountCommandDispatchAdapter(
  private val commandGateway: CommandGateway,
  private val moneyTransferIdGenerator: MoneyTransferIdGenerator
) : BankAccountCommandPort, AtmCommandPort, MoneyTransferCommandPort {

  @CommandDispatcher(dispatches = "axon.bank.CreateBankAccountCommand")
  override fun createBankAccount(accountId: AccountId, initialBalance: Balance): CompletableFuture<Unit> {
    return commandGateway.send(
      CreateBankAccountCommand(
        accountId = accountId,
        initialBalance = initialBalance
      )
    )
  }

  @CommandDispatcher(dispatches = "axon.bank.WithdrawMoneyCommand")
  override fun withdrawMoney(accountId: AccountId, amount: Amount): CompletableFuture<Unit> {
    return commandGateway.send(
      WithdrawMoneyCommand(
        accountId,
        amount
      )
    )
  }

  @CommandDispatcher(dispatches = "axon.bank.DepositMoneyCommand")
  override fun depositMoney(accountId: AccountId, amount: Amount): CompletableFuture<Unit> {
    return commandGateway.send(
      DepositMoneyCommand(
        accountId,
        amount
      )
    )
  }

  @CommandDispatcher(dispatches = "axon.bank.RequestMoneyTransferCommand")
  override fun transferMoney(
    sourceAccountId: AccountId,
    targetAccountId: AccountId,
    amount: Amount
  ): CompletableFuture<MoneyTransferId> {
    val moneyTransferId = moneyTransferIdGenerator.get()
    return commandGateway.send<Unit>(
      RequestMoneyTransferCommand(
        moneyTransferId = moneyTransferId,
        sourceAccountId = sourceAccountId,
        targetAccountId = targetAccountId,
        amount = amount
      )
    ).thenApply { moneyTransferId }
  }

  @CommandDispatcher(dispatches = "axon.bank.ReceiveMoneyTransferCommand")
  override fun receiveMoneyTransfer(moneyTransfer: MoneyTransfer) {
    commandGateway.sendAndWait<Void>(
      ReceiveMoneyTransferCommand(
        moneyTransfer.targetAccountId,
        moneyTransfer.moneyTransferId,
        moneyTransfer.amount
      )
    )
  }

  @CommandDispatcher(dispatches = "axon.bank.CompleteMoneyTransferCommand")
  override fun completeMoneyTransfer(moneyTransfer: MoneyTransfer) {
    commandGateway.send<Void>(
      CompleteMoneyTransferCommand(
        sourceAccountId = moneyTransfer.sourceAccountId,
        moneyTransferId = moneyTransfer.moneyTransferId
      )
    )
  }

  @CommandDispatcher(dispatches = "axon.bank.CancelMoneyTransferCommand")
  override fun cancelMoneyTransfer(moneyTransfer: MoneyTransfer, rejectionReason: RejectionReason) {
    commandGateway.sendAndWait<Void>(
      CancelMoneyTransferCommand(
        moneyTransferId = moneyTransfer.moneyTransferId,
        sourceAccountId = moneyTransfer.sourceAccountId,
        targetAccountId = moneyTransfer.targetAccountId,
        rejectionReason = rejectionReason
      )
    )
  }
}
