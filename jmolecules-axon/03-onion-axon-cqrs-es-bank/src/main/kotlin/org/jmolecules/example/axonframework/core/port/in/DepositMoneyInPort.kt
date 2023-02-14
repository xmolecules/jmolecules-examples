package org.jmolecules.example.axonframework.core.port.`in`

import org.jmolecules.example.axonframework.core.model.bankaccount.exception.MaximumBalanceExceededException
import org.jmolecules.example.axonframework.core.model.bankaccount.type.AccountId
import org.jmolecules.example.axonframework.core.model.bankaccount.type.Amount

/**
 * Port to address UC-002 Deposit Money (ATM).
 */
interface DepositMoneyInPort {
  /**
   * Deposits money on the given account.
   * @param accountId account id.
   * @param amount amount to deposit.
   */
  @Throws(MaximumBalanceExceededException::class)
  fun depositMoney(accountId: AccountId, amount: Amount)
}
