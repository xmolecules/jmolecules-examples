package org.jmolecules.example.axonframework.bank.application.port.out.command

import org.jmolecules.architecture.hexagonal.SecondaryPort
import org.jmolecules.example.axonframework.bank.domain.bankaccount.type.AccountId
import org.jmolecules.example.axonframework.bank.domain.bankaccount.type.Amount
import org.jmolecules.example.axonframework.bank.domain.moneytransfer.command.MoneyTransfer
import org.jmolecules.example.axonframework.bank.domain.moneytransfer.type.MoneyTransferId
import org.jmolecules.example.axonframework.bank.domain.moneytransfer.type.RejectionReason
import java.util.concurrent.CompletableFuture

/**
 * Command port for money transfers.
 */
@SecondaryPort
interface MoneyTransferCommandPort {
  /**
   * Initializes the money transfer.
   * @param sourceAccountId source account to transfer money from.
   * @param targetAccountId target account to transfer money to.
   * @param amount amount of money to transfer.
   */
  fun transferMoney(sourceAccountId: AccountId, targetAccountId: AccountId, amount: Amount): CompletableFuture<MoneyTransferId>

  /**
   * Indicate receipt of the money transfer.
   * @param moneyTransfer transfer to receive.
   */
  fun receiveMoneyTransfer(moneyTransfer: MoneyTransfer)

  /**
   * Indicate completion of money transfer.
   */
  fun completeMoneyTransfer(moneyTransfer: MoneyTransfer)

  /**
   * Indicate cancellation of money transfer.
   */
  fun cancelMoneyTransfer(moneyTransfer: MoneyTransfer, rejectionReason: RejectionReason)
}
