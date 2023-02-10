package org.jmolecules.example.axonframework.infrastructure.adapter.out.dispatch

import org.axonframework.queryhandling.QueryGateway
import org.jmolecules.example.axonframework.core.model.bankaccount.read.BankAccountCurrentBalance
import org.jmolecules.example.axonframework.core.model.bankaccount.type.AccountId
import org.jmolecules.example.axonframework.core.model.moneytransfer.read.MoneyTransferSummary
import org.jmolecules.example.axonframework.core.model.moneytransfer.type.MoneyTransferId
import org.jmolecules.example.axonframework.core.port.out.query.BankAccountQueryPort
import org.jmolecules.example.axonframework.core.port.out.query.MoneyTransferQueryPort
import org.jmolecules.example.axonframework.infrastructure.adapter.out.readmodel.bankaccount.BankAccountCurrentBalanceQuery
import org.jmolecules.example.axonframework.infrastructure.adapter.out.readmodel.moneytransfer.MoneyTransferSummariesForBankAccountQuery
import org.jmolecules.example.axonframework.infrastructure.adapter.out.readmodel.moneytransfer.MoneyTransferSummaryByIdQuery
import org.springframework.stereotype.Component
import java.util.*
import java.util.concurrent.CompletableFuture

/**
 * Implementation of the bank account query dispatch.
 */
@Component
class BankAccountQueryDispatchAdapter(
  val queryGateway: QueryGateway
) : BankAccountQueryPort, MoneyTransferQueryPort {

  override fun getCurrentBalance(accountId: AccountId): CompletableFuture<Optional<BankAccountCurrentBalance>> {
    return queryGateway.query(
      BankAccountCurrentBalanceQuery(accountId), BankAccountCurrentBalanceQuery.RESPONSE_TYPE
    )
  }

  override fun getMoneyTransfers(accountId: AccountId): CompletableFuture<List<MoneyTransferSummary>> {
    return queryGateway.query(
      MoneyTransferSummariesForBankAccountQuery(accountId), MoneyTransferSummariesForBankAccountQuery.RESPONSE_TYPE
    )
  }

  override fun getMoneyTransfer(moneyTransferId: MoneyTransferId): CompletableFuture<Optional<MoneyTransferSummary>> {
    return queryGateway.query(
      MoneyTransferSummaryByIdQuery(moneyTransferId), MoneyTransferSummaryByIdQuery.RESPONSE_TYPE
    )
  }


}
