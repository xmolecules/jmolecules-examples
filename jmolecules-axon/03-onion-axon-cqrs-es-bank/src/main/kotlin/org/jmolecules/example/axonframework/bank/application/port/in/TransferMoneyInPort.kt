package org.jmolecules.example.axonframework.bank.application.port.`in`

import org.jmolecules.example.axonframework.bank.domain.bankaccount.type.InsufficientBalance
import org.jmolecules.example.axonframework.bank.domain.bankaccount.type.MaximumBalanceExceeded
import org.jmolecules.example.axonframework.bank.domain.bankaccount.type.AccountId
import org.jmolecules.example.axonframework.bank.domain.bankaccount.type.Amount
import org.jmolecules.example.axonframework.bank.domain.moneytransfer.type.MoneyTransferNotFound
import org.jmolecules.example.axonframework.bank.domain.moneytransfer.type.MoneyTransferSummaries
import org.jmolecules.example.axonframework.bank.domain.moneytransfer.type.MoneyTransferSummary
import org.jmolecules.example.axonframework.bank.domain.moneytransfer.type.MoneyTransferId
import java.util.*
import java.util.concurrent.CompletableFuture

/**
 * Port to address UC-004 Perform a money transfer.
 */
interface TransferMoneyInPort {
  /**
   * Initializes money transfers from source to target account.
   * @param sourceAccountId source account id.
   * @param targetAccountId target account id.
   * @param amount amount to transfer.
   * @return money transfer id.
   * @throws [InsufficientBalance] if the balance source account would be below the minimum.
   * @throws [MaximumBalanceExceeded] if the balance target account would be above the maximum.
   */
  @Throws(InsufficientBalance::class, MaximumBalanceExceeded::class)
  fun transferMoney(sourceAccountId: AccountId, targetAccountId: AccountId, amount: Amount): MoneyTransferId

  /**
   * Retrieves a list of money transfers for given account.
   * @param accountId account id.
   * @throws [MoneyTransferNotFound] if no money transfer can be found.
   * @return list of transfers, the account is part of.
   */
  @Throws(MoneyTransferNotFound::class)
  fun getMoneyTransfers(accountId: AccountId): CompletableFuture<MoneyTransferSummaries>

  /**
   * Finds a money transfer.
   * @param moneyTransferId id of the money transfer.
   * @throws [MoneyTransferNotFound] if no money transfer can be found.
   * @return money transfer.
   */
  @Throws(MoneyTransferNotFound::class)
  fun getMoneyTransfer(moneyTransferId: MoneyTransferId): CompletableFuture<Optional<MoneyTransferSummary>>
}
