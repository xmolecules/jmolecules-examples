package org.jmolecules.example.axonframework.bank.application.usecase

import org.jmolecules.example.axonframework.bank.domain.bankaccount.exception.InsufficientBalanceException
import org.jmolecules.example.axonframework.bank.domain.bankaccount.exception.MaximumBalanceExceededException
import org.jmolecules.example.axonframework.bank.domain.bankaccount.type.AccountId
import org.jmolecules.example.axonframework.bank.domain.bankaccount.type.Amount
import org.jmolecules.example.axonframework.bank.domain.moneytransfer.exception.MoneyTransferNotFoundException
import org.jmolecules.example.axonframework.bank.domain.moneytransfer.read.MoneyTransferSummary
import org.jmolecules.example.axonframework.bank.domain.moneytransfer.type.MoneyTransferId
import org.jmolecules.example.axonframework.bank.application.port.`in`.TransferMoneyInPort
import org.jmolecules.example.axonframework.bank.application.port.out.command.MoneyTransferCommandPort
import org.jmolecules.example.axonframework.bank.application.port.out.query.MoneyTransferQueryPort
import org.jmolecules.example.axonframework.bank.domain.moneytransfer.read.MoneyTransferSummaries
import org.springframework.stereotype.Component
import java.util.*
import java.util.concurrent.CompletableFuture

/**
 * Operations related to money transfers.
 */
@Component
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
   * @throws [InsufficientBalanceException] if the balance source account would be below the minimum.
   * @throws [MaximumBalanceExceededException] if the balance target account would be above the maximum.
   */
  @Throws(InsufficientBalanceException::class, MaximumBalanceExceededException::class)
  override fun transferMoney(sourceAccountId: AccountId, targetAccountId: AccountId, amount: Amount): MoneyTransferId {
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
  override fun getMoneyTransfers(accountId: AccountId): CompletableFuture<MoneyTransferSummaries> {
    return moneyTransferQueryPort.getMoneyTransfers(accountId = accountId)
  }

  /**
   * Finds a money transfer.
   * @param moneyTransferId id of the money transfer.
   * @throws [MoneyTransferNotFoundException] if no money transfer can be found.
   * @return money transfer.
   */
  @Throws(MoneyTransferNotFoundException::class)
  override fun getMoneyTransfer(moneyTransferId: MoneyTransferId): CompletableFuture<Optional<MoneyTransferSummary>> {
    return moneyTransferQueryPort.getMoneyTransfer(moneyTransferId = moneyTransferId)
  }
}
