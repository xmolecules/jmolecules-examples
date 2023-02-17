package org.jmolecules.example.axonframework.bank.domain.bankaccount.type

/**
 * Bank account current balance.
 */
data class CurrentBalance(
  val accountId: AccountId,
  val currentBalance: Balance
)
