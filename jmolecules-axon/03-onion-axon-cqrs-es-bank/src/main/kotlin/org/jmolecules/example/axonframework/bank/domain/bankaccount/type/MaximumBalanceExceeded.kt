package org.jmolecules.example.axonframework.bank.domain.bankaccount.type

class MaximumBalanceExceeded(
  val accountId: AccountId,
  val currentBalance: Balance,
  val maximumBalance: Balance,
  val depositAmount: Amount?
) : RuntimeException(
  if (depositAmount == null) {
    "BankAccount[id=$accountId] can't be created, its balance $currentBalance would exceed max. balance of $maximumBalance"
  } else {
    "BankAccount[id=$accountId, currentBalance=$currentBalance]: Deposit of amount=$depositAmount not allowed, would exceed max. balance of $maximumBalance"
  }
)
