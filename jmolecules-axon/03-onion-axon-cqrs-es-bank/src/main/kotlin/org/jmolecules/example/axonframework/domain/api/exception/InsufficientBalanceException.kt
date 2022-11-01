package org.jmolecules.example.axonframework.domain.api.exception

import org.jmolecules.example.axonframework.domain.api.type.AccountId
import org.jmolecules.example.axonframework.domain.api.type.Amount
import org.jmolecules.example.axonframework.domain.api.type.Balance

class InsufficientBalanceException(
    accountId: AccountId,
    currentBalance: Balance,
    withdrawAmount: Amount?,
    minimumBalance: Balance
) : RuntimeException(
    if (withdrawAmount == null) {
        "BankAccount[id=$accountId] can't be created, its balance $currentBalance would subceed min. balance of $minimumBalance"
    } else {
        "BankAccount[id=$accountId, currentBalance=$currentBalance]: Withdrawal of amount=$withdrawAmount not allowed, would subceed min. balance of $minimumBalance"
    }
)
