package org.jmolecules.example.axonframework.bank.domain.moneytransfer.read

import org.jmolecules.ddd.annotation.Entity
import org.jmolecules.ddd.annotation.Identity
import org.jmolecules.example.axonframework.bank.domain.bankaccount.type.AccountId
import org.jmolecules.example.axonframework.bank.domain.bankaccount.type.Amount
import org.jmolecules.example.axonframework.bank.domain.moneytransfer.type.MoneyTransferId
import org.jmolecules.example.axonframework.bank.domain.moneytransfer.type.RejectionReason

@Entity
class BankAccountMoneyTransfer(
  @Identity
  val moneyTransferId: MoneyTransferId,
  val sourceAccountId: AccountId,
  val targetAccountId: AccountId,
  val amount: Amount,
  var success: Boolean? = null,
  var errorMessage: RejectionReason? = null
) {
  /**
   * Completes money transfer.
   * @return money transfer marked as completed.
   */
  fun complete(): BankAccountMoneyTransfer {
    return this.apply {
      success = true
    }
  }

  /**
   * Cancels money transfer.
   * @param rejectionReason reason for rejection.
   * @return money transfer marked as cancelled.
   */
  fun cancel(rejectionReason: RejectionReason): BankAccountMoneyTransfer {
    return this.apply {
      success = false
      errorMessage = rejectionReason
    }
  }
}
