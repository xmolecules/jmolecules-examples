package org.jmolecules.example.axonframework.core.port.`in`

import org.jmolecules.example.axonframework.core.model.bankaccount.exception.InsufficientBalanceException
import org.jmolecules.example.axonframework.core.model.bankaccount.exception.MaximumBalanceExceededException
import org.jmolecules.example.axonframework.core.model.bankaccount.type.AccountId
import org.jmolecules.example.axonframework.core.model.bankaccount.type.Amount
import org.jmolecules.example.axonframework.core.model.moneytransfer.exception.MoneyTransferNotFoundException
import org.jmolecules.example.axonframework.core.model.moneytransfer.read.MoneyTransferSummary
import org.jmolecules.example.axonframework.core.model.moneytransfer.type.MoneyTransferId
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
   * @throws [InsufficientBalanceException] if the balance source account would be below the minimum.
   * @throws [MaximumBalanceExceededException] if the balance target account would be above the maximum.
   */
  @Throws(InsufficientBalanceException::class, MaximumBalanceExceededException::class)
  fun transferMoney(sourceAccountId: AccountId, targetAccountId: AccountId, amount: Amount): MoneyTransferId

  /**
   * Retrieves a list of money transfers for given account.
   * @param accountId account id.
   * @throws [MoneyTransferNotFoundException] if no money transfer can be found.
   * @return list of transfers, the account is part of.
   */
  @Throws(MoneyTransferNotFoundException::class)
  fun getMoneyTransfers(accountId: AccountId): CompletableFuture<List<MoneyTransferSummary>>

  /**
   * Finds a money transfer.
   * @param moneyTransferId id of the money transfer.
   * @throws [MoneyTransferNotFoundException] if no money transfer can be found.
   * @return money transfer.
   */
  @Throws(MoneyTransferNotFoundException::class)
  fun getMoneyTransfer(moneyTransferId: MoneyTransferId): CompletableFuture<Optional<MoneyTransferSummary>>
}
