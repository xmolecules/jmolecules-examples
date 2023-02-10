package org.jmolecules.example.axonframework.core.model.bankaccount.read

import org.jmolecules.example.axonframework.core.model.bankaccount.type.AccountId
import org.jmolecules.example.axonframework.core.model.bankaccount.type.Balance

/**
 * Bank account balance.
 */
data class BankAccountCurrentBalance(
  val accountId: AccountId,
  val currentBalance: Balance
)
