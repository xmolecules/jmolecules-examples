package org.jmolecules.example.axonframework.domain.api.command

import org.jmolecules.example.axonframework.domain.api.type.AccountId
import org.jmolecules.example.axonframework.domain.api.type.Amount
import org.jmolecules.example.axonframework.domain.api.type.Balance

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
