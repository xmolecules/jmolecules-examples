package org.jmolecules.example.axonframework.core.model.bankaccount.query

import org.jmolecules.example.axonframework.core.model.bankaccount.AccountId
import org.jmolecules.example.axonframework.core.model.bankaccount.Balance

/**
 * Bank account balance.
 */
data class BankAccountCurrentBalance(
  val accountId: AccountId,
  val currentBalance: Balance
)
