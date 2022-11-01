package org.jmolecules.example.axonframework.domain.api.query

import org.jmolecules.example.axonframework.domain.api.type.AccountId
import org.jmolecules.example.axonframework.domain.api.type.Balance

/**
 * Bank account balance.
 */
data class BankAccountCurrentBalance(
    val accountId: AccountId,
    val currentBalance: Balance
)
