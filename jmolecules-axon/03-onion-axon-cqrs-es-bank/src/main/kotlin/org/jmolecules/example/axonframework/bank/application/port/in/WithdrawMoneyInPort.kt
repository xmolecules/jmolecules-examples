package org.jmolecules.example.axonframework.bank.application.port.`in`

import org.jmolecules.example.axonframework.bank.domain.bankaccount.type.InsufficientBalance
import org.jmolecules.example.axonframework.bank.domain.bankaccount.type.AccountId
import org.jmolecules.example.axonframework.bank.domain.bankaccount.type.Amount

/**
 * Port to address UC-003 Withdraw Money (ATM).
 */
interface WithdrawMoneyInPort {
  /**
   * Withdraws money from given account.
   * @param accountId account id.
   * @param amount amount to withdraw.
   */
  @Throws(InsufficientBalance::class)
  fun withdrawMoney(accountId: AccountId, amount: Amount)
}
