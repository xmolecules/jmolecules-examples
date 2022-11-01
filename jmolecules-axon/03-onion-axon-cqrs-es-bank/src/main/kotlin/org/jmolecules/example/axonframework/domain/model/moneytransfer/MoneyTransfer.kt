package org.jmolecules.example.axonframework.domain.model.moneytransfer

import org.jmolecules.example.axonframework.domain.api.type.AccountId
import org.jmolecules.example.axonframework.domain.api.type.Amount
import org.jmolecules.example.axonframework.domain.api.type.MoneyTransferId

data class MoneyTransfer(
    val moneyTransferId: MoneyTransferId,
    val sourceAccountId: AccountId,
    val targetAccountId: AccountId,
    val amount: Amount
)
