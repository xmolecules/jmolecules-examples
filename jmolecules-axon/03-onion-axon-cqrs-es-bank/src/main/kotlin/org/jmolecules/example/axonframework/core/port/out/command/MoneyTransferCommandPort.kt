package org.jmolecules.example.axonframework.core.port.out.command

import org.jmolecules.example.axonframework.core.model.bankaccount.type.AccountId
import org.jmolecules.example.axonframework.core.model.bankaccount.type.Amount
import org.jmolecules.example.axonframework.core.model.moneytransfer.state.MoneyTransfer
import org.jmolecules.example.axonframework.core.model.moneytransfer.type.MoneyTransferId
import org.jmolecules.example.axonframework.core.model.moneytransfer.type.Reason

/**
 * Command port for money transfers.
 */
interface MoneyTransferCommandPort {
  /**
   * Initializes the money transfer.
   * @param sourceAccountId source account to transfer money from.
   * @param targetAccountId target account to transfer money to.
   * @param amount amount of money to transfer.
   */
  fun transferMoney(sourceAccountId: AccountId, targetAccountId: AccountId, amount: Amount): MoneyTransferId

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
  fun cancelMoneyTransfer(moneyTransfer: MoneyTransfer, reason: Reason)
}
