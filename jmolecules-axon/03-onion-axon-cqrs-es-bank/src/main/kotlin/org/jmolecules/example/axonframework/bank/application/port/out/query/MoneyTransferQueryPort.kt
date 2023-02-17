package org.jmolecules.example.axonframework.bank.application.port.out.query

import org.jmolecules.architecture.hexagonal.SecondaryPort
import org.jmolecules.example.axonframework.bank.domain.bankaccount.type.AccountId
import org.jmolecules.example.axonframework.bank.domain.moneytransfer.type.MoneyTransferSummaries
import org.jmolecules.example.axonframework.bank.domain.moneytransfer.type.MoneyTransferSummary
import org.jmolecules.example.axonframework.bank.domain.moneytransfer.type.MoneyTransferId
import java.util.*
import java.util.concurrent.CompletableFuture

/**
 * Query for money transfers.
 */
@SecondaryPort
interface MoneyTransferQueryPort {
  /**
   * Retrieves a list of money transfers the account was involved in.
   */
  fun getMoneyTransfers(accountId: AccountId): CompletableFuture<MoneyTransferSummaries>

  /**
   * Retrieves a money transfer by id.
   */
  fun getMoneyTransfer(moneyTransferId: MoneyTransferId): CompletableFuture<Optional<MoneyTransferSummary>>
}
