package org.jmolecules.example.axonframework.core.port.out.command

import org.jmolecules.example.axonframework.core.model.bankaccount.type.AccountId
import org.jmolecules.example.axonframework.core.model.bankaccount.type.Balance

/**
 * Direct account manipulation.
 */
interface BankAccountCommandPort {
  /**
   * Creates a new bank account.
   * @param accountId account id.
   * @param initialBalance initial balance.
   */
  fun createBankAccount(accountId: AccountId, initialBalance: Balance)
}
