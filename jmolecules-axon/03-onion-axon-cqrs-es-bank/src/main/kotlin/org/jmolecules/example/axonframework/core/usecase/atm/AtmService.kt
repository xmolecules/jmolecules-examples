package org.jmolecules.example.axonframework.core.usecase.atm

import org.jmolecules.example.axonframework.core.model.bankaccount.AccountId
import org.jmolecules.example.axonframework.core.model.bankaccount.Amount
import org.jmolecules.example.axonframework.core.model.bankaccount.InsufficientBalanceException
import org.jmolecules.example.axonframework.core.model.bankaccount.MaximumBalanceExceededException


/**
 * Operations on bank account available at ATM.
 */
interface AtmService {
  /**
   * Withdraws money from given account.
   * @param accountId account id.
   * @param amount amount to withdraw.
   */
  @Throws(InsufficientBalanceException::class)
  fun withdrawMoney(accountId: AccountId, amount: Amount)

  /**
   * Deposits money on the given account.
   * @param accountId account id.
   * @param amount amount to deposit.
   */
  @Throws(MaximumBalanceExceededException::class)
  fun depositMoney(accountId: AccountId, amount: Amount)
}
