package org.jmolecules.example.axonframework.bank.application.usecase

import org.jmolecules.example.axonframework.bank.domain.bankaccount.type.InsufficientBalance
import org.jmolecules.example.axonframework.bank.domain.bankaccount.type.MaximumBalanceExceeded
import org.jmolecules.example.axonframework.bank.domain.bankaccount.type.AccountId
import org.jmolecules.example.axonframework.bank.domain.bankaccount.type.Amount
import org.jmolecules.example.axonframework.bank.domain.moneytransfer.type.MoneyTransferNotFound
import org.jmolecules.example.axonframework.bank.domain.moneytransfer.type.MoneyTransferSummary
import org.jmolecules.example.axonframework.bank.domain.moneytransfer.type.MoneyTransferId
import org.jmolecules.example.axonframework.bank.application.port.`in`.TransferMoneyInPort
import org.jmolecules.example.axonframework.bank.application.port.out.command.MoneyTransferCommandPort
import org.jmolecules.example.axonframework.bank.application.port.out.query.MoneyTransferQueryPort
import org.jmolecules.example.axonframework.bank.domain.moneytransfer.type.MoneyTransferSummaries
import java.util.*
import java.util.concurrent.CompletableFuture

/**
 * Operations related to money transfers.
 */
class TransferMoneyUseCase(
  private val moneyTransferCommandPort: MoneyTransferCommandPort,
  private val moneyTransferQueryPort: MoneyTransferQueryPort,
) : TransferMoneyInPort {

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
  override fun transferMoney(sourceAccountId: AccountId, targetAccountId: AccountId, amount: Amount): CompletableFuture<MoneyTransferId> {
    return moneyTransferCommandPort.transferMoney(
      sourceAccountId = sourceAccountId,
      targetAccountId = targetAccountId,
      amount = amount
    )
  }

  /**
   * Retrieves a list of money transfers for given account.
   * @param accountId account id.
   * @throws [MoneyTransferNotFound] if no money transfer can be found.
   * @return list of transfers, the account is part of.
   */
  @Throws(MoneyTransferNotFound::class)
  override fun getMoneyTransfers(accountId: AccountId): CompletableFuture<MoneyTransferSummaries> {
    return moneyTransferQueryPort.findMoneyTransfers(accountId = accountId)
  }

  /**
   * Finds a money transfer.
   * @param moneyTransferId id of the money transfer.
   * @throws [MoneyTransferNotFound] if no money transfer can be found.
   * @return money transfer.
   */
  @Throws(MoneyTransferNotFound::class)
  override fun getMoneyTransfer(moneyTransferId: MoneyTransferId): CompletableFuture<Optional<MoneyTransferSummary>> {
    return moneyTransferQueryPort.findMoneyTransfer(moneyTransferId = moneyTransferId)
  }
}
