package org.jmolecules.example.axonframework.bank.adapter.out.dispatch

import org.axonframework.queryhandling.QueryGateway
import org.jmolecules.architecture.hexagonal.SecondaryAdapter
import org.jmolecules.example.axonframework.bank.domain.bankaccount.type.CurrentBalance
import org.jmolecules.example.axonframework.bank.domain.bankaccount.type.AccountId
import org.jmolecules.example.axonframework.bank.domain.moneytransfer.type.MoneyTransferSummary
import org.jmolecules.example.axonframework.bank.domain.moneytransfer.type.MoneyTransferId
import org.jmolecules.example.axonframework.bank.application.port.out.query.BankAccountQueryPort
import org.jmolecules.example.axonframework.bank.application.port.out.query.MoneyTransferQueryPort
import org.jmolecules.example.axonframework.bank.adapter.out.querymodel.bankaccount.BankAccountCurrentBalanceQuery
import org.jmolecules.example.axonframework.bank.adapter.out.querymodel.moneytransfer.MoneyTransferSummariesForBankAccountQuery
import org.jmolecules.example.axonframework.bank.adapter.out.querymodel.moneytransfer.MoneyTransferSummaryByIdQuery
import org.jmolecules.example.axonframework.bank.domain.moneytransfer.type.MoneyTransferSummaries
import org.jmolecules.example.axonframework.infrastructure.architecture.QueryDispatcher
import org.springframework.stereotype.Component
import java.util.Optional
import java.util.concurrent.CompletableFuture

/**
 * Implementation of the bank account query dispatch.
 */
@Component
@SecondaryAdapter
class BankAccountQueryDispatchAdapter(
  val queryGateway: QueryGateway
) : BankAccountQueryPort, MoneyTransferQueryPort {

  @QueryDispatcher(namespace = "axon.bank", name = "BankAccountCurrentBalanceQuery")
  override fun findCurrentBalance(accountId: AccountId): CompletableFuture<Optional<CurrentBalance>> {
    return queryGateway.query(
      BankAccountCurrentBalanceQuery(accountId), BankAccountCurrentBalanceQuery.RESPONSE_TYPE
    )
  }

  @QueryDispatcher(namespace = "axon.bank", name = "MoneyTransferSummariesForBankAccountQuery")
  override fun findMoneyTransfers(accountId: AccountId): CompletableFuture<MoneyTransferSummaries> {
    return queryGateway.query(
      MoneyTransferSummariesForBankAccountQuery(accountId), MoneyTransferSummariesForBankAccountQuery.RESPONSE_TYPE
    )
  }

  @QueryDispatcher(namespace = "axon.bank", name = "MoneyTransferSummaryByIdQuery")
  override fun findMoneyTransfer(moneyTransferId: MoneyTransferId): CompletableFuture<Optional<MoneyTransferSummary>> {
    return queryGateway.query(
      MoneyTransferSummaryByIdQuery(moneyTransferId), MoneyTransferSummaryByIdQuery.RESPONSE_TYPE
    )
  }
}
