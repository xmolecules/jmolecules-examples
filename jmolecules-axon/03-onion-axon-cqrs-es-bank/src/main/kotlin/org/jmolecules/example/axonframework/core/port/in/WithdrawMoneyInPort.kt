package org.jmolecules.example.axonframework.core.port.`in`

import org.jmolecules.example.axonframework.core.model.bankaccount.exception.InsufficientBalanceException
import org.jmolecules.example.axonframework.core.model.bankaccount.type.AccountId
import org.jmolecules.example.axonframework.core.model.bankaccount.type.Amount

/**
 * Port to address UC-003 Withdraw Money (ATM).
 */
interface WithdrawMoneyInPort {
  /**
   * Withdraws money from given account.
   * @param accountId account id.
   * @param amount amount to withdraw.
   */
  @Throws(InsufficientBalanceException::class)
  fun withdrawMoney(accountId: AccountId, amount: Amount)
}
