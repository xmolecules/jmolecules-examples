package org.jmolecules.example.axonframework.domain.api.query

import org.jmolecules.example.axonframework.domain.api.type.AccountId
import org.jmolecules.example.axonframework.domain.api.type.Amount
import org.jmolecules.example.axonframework.domain.api.type.MoneyTransferId
import org.jmolecules.example.axonframework.domain.api.type.Reason

/**
 * Public visible money transfer.
 */
data class MoneyTransferSummary(
    val moneyTransferId: MoneyTransferId,
    val sourceAccountId: AccountId,
    val targetAccountId: AccountId,
    val amount: Amount,
    val success: Boolean?,
    val errorMessage: Reason? = null
)
