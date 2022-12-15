package org.jmolecules.example.axonframework.core.model.bankaccount

class MaximumBalanceExceededException(
  accountId: AccountId,
  currentBalance: Balance,
  maximumBalance: Balance,
  depositAmount: Amount?
) : RuntimeException(
  if (depositAmount == null) {
    "BankAccount[id=$accountId] can't be created, its balance $currentBalance would exceed max. balance of $maximumBalance"
  } else {
    "BankAccount[id=$accountId, currentBalance=$currentBalance]: Deposit of amount=$depositAmount not allowed, would exceed max. balance of $maximumBalance"
  }
)
