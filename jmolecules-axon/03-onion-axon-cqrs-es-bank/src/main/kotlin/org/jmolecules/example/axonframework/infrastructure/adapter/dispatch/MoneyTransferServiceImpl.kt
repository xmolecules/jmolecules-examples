package org.jmolecules.example.axonframework.infrastructure.adapter.dispatch

import org.axonframework.commandhandling.gateway.CommandGateway
import org.axonframework.queryhandling.QueryGateway
import org.jmolecules.example.axonframework.core.model.bankaccount.AccountId
import org.jmolecules.example.axonframework.core.model.bankaccount.Amount
import org.jmolecules.example.axonframework.core.model.moneytransfer.MoneyTransferId
import org.jmolecules.example.axonframework.core.model.moneytransfer.command.RequestMoneyTransferCommand
import org.jmolecules.example.axonframework.core.model.moneytransfer.query.MoneyTransferSummariesForBankAccountQuery
import org.jmolecules.example.axonframework.core.model.moneytransfer.query.MoneyTransferSummary
import org.jmolecules.example.axonframework.core.model.moneytransfer.query.MoneyTransferSummaryByIdQuery
import org.jmolecules.example.axonframework.core.usecase.moneytransfer.MoneyTransferIdGenerator
import org.jmolecules.example.axonframework.core.usecase.moneytransfer.MoneyTransferService
import org.springframework.stereotype.Component
import java.util.*
import java.util.concurrent.CompletableFuture

/**
 * Implementation of the transfer service using Axon Buses for Command and Query dispatch.
 */
@Component
class MoneyTransferServiceImpl(
  private val commandGateway: CommandGateway,
  private val queryGateway: QueryGateway,
  private val moneyTransferIdGenerator: MoneyTransferIdGenerator
) : MoneyTransferService {

  override fun transferMoney(
    sourceAccountId: AccountId,
    targetAccountId: AccountId,
    amount: Amount
  ): MoneyTransferId {
    val moneyTransferId = moneyTransferIdGenerator.get()
    commandGateway.sendAndWait<Void>(
      RequestMoneyTransferCommand(
        moneyTransferId = moneyTransferId,
        sourceAccountId = sourceAccountId,
        targetAccountId = targetAccountId,
        amount = amount
      )
    )
    return moneyTransferId
  }

  override fun getMoneyTransfers(accountId: AccountId): CompletableFuture<List<MoneyTransferSummary>> {
    return queryGateway.query(MoneyTransferSummariesForBankAccountQuery(accountId), MoneyTransferSummariesForBankAccountQuery.RESPONSE_TYPE)
  }

  override fun getMoneyTransfer(moneyTransferId: MoneyTransferId): CompletableFuture<Optional<MoneyTransferSummary>> {
    return queryGateway.query(MoneyTransferSummaryByIdQuery(moneyTransferId), MoneyTransferSummaryByIdQuery.RESPONSE_TYPE)
  }
}
