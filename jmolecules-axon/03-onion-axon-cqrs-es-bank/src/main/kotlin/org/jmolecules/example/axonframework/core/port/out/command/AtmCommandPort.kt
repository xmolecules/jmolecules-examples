package org.jmolecules.example.axonframework.core.port.out.command

import org.jmolecules.example.axonframework.core.model.bankaccount.exception.InsufficientBalanceException
import org.jmolecules.example.axonframework.core.model.bankaccount.exception.MaximumBalanceExceededException
import org.jmolecules.example.axonframework.core.model.bankaccount.type.AccountId
import org.jmolecules.example.axonframework.core.model.bankaccount.type.Amount
import kotlin.jvm.Throws

/**
 * Port to send ATM commands.
 */
interface AtmCommandPort {
  /**
   * Deposits money to the account.
   * @param accountId account id.
   * @param amount amount to deposit.
   * @throws MaximumBalanceExceededException if the resulting balance is above the maximum amount.
   */
  @Throws(MaximumBalanceExceededException::class)
  fun depositMoney(accountId: AccountId, amount: Amount)

  /**
   * Withdraws money from account.
   * @param accountId account id.
   * @param amount to withdraw.
   * @throws InsufficientBalanceException the remaining balance is below minimum account balance.
   */
  @Throws(InsufficientBalanceException::class)
  fun withdrawMoney(accountId: AccountId, amount: Amount)
}
