package org.jmolecules.example.axonframework.core.port.`in`

import org.jmolecules.example.axonframework.core.model.bankaccount.exception.InsufficientBalanceException
import org.jmolecules.example.axonframework.core.model.bankaccount.exception.MaximumBalanceExceededException
import org.jmolecules.example.axonframework.core.model.bankaccount.type.AccountId
import org.jmolecules.example.axonframework.core.model.bankaccount.type.Balance

/**
 * Port to address UC-001 Create Bank Account.
 */
interface CreateBankAccountInPort {
  /**
   * Creates a bank account.
   * @param accountId account id.
   * @param initialBalance balance of the account.
   */
  @Throws(MaximumBalanceExceededException::class, InsufficientBalanceException::class)
  fun createBankAccount(accountId: AccountId, initialBalance: Balance)
}
