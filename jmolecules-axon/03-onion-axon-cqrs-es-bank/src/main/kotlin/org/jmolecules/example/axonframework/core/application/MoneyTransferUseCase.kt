package org.jmolecules.example.axonframework.core.application

import org.jmolecules.example.axonframework.core.model.bankaccount.exception.InsufficientBalanceException
import org.jmolecules.example.axonframework.core.model.bankaccount.exception.MaximumBalanceExceededException
import org.jmolecules.example.axonframework.core.model.bankaccount.type.AccountId
import org.jmolecules.example.axonframework.core.model.bankaccount.type.Amount
import org.jmolecules.example.axonframework.core.model.moneytransfer.exception.MoneyTransferNotFoundException
import org.jmolecules.example.axonframework.core.model.moneytransfer.read.MoneyTransferSummary
import org.jmolecules.example.axonframework.core.model.moneytransfer.type.MoneyTransferId
import org.jmolecules.example.axonframework.core.port.out.command.MoneyTransferCommandPort
import org.jmolecules.example.axonframework.core.port.out.query.MoneyTransferQueryPort
import org.springframework.stereotype.Component
import java.util.*
import java.util.concurrent.CompletableFuture

/**
 * Operations related to money transfers.
 */
@Component
class MoneyTransferUseCase(
  private val moneyTransferCommandPort: MoneyTransferCommandPort,
  private val moneyTransferQueryPort: MoneyTransferQueryPort,
) {

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
  fun transferMoney(sourceAccountId: AccountId, targetAccountId: AccountId, amount: Amount): MoneyTransferId {
    return moneyTransferCommandPort.transferMoney(
      sourceAccountId = sourceAccountId,
      targetAccountId = targetAccountId,
      amount = amount
    )
  }

  /**
   * Retrieves a list of money transfers for given account.
   * @param accountId account id.
   * @throws [MoneyTransferNotFoundException] if no money transfer can be found.
   * @return list of transfers, the account is part of.
   */
  @Throws(MoneyTransferNotFoundException::class)
  fun getMoneyTransfers(accountId: AccountId): CompletableFuture<List<MoneyTransferSummary>> {
    return moneyTransferQueryPort.getMoneyTransfers(accountId = accountId)
  }

  /**
   * Finds a money transfer.
   * @param moneyTransferId id of the money transfer.
   * @throws [MoneyTransferNotFoundException] if no money transfer can be found.
   * @return money transfer.
   */
  @Throws(MoneyTransferNotFoundException::class)
  fun getMoneyTransfer(moneyTransferId: MoneyTransferId): CompletableFuture<Optional<MoneyTransferSummary>> {
    return moneyTransferQueryPort.getMoneyTransfer(moneyTransferId = moneyTransferId)
  }
}
