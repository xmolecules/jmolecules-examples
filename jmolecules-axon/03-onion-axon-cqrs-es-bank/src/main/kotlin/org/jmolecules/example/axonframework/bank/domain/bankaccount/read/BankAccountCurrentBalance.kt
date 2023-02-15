package org.jmolecules.example.axonframework.bank.domain.bankaccount.read

import org.jmolecules.example.axonframework.bank.domain.bankaccount.type.AccountId
import org.jmolecules.example.axonframework.bank.domain.bankaccount.type.Balance

/**
 * Bank account balance.
 */
data class BankAccountCurrentBalance(
  val accountId: AccountId,
  val currentBalance: Balance
)
