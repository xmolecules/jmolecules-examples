package org.jmolecules.example.axonframework.domain.model.moneytransfer

import org.jmolecules.example.axonframework.domain.api.type.Amount
import org.jmolecules.example.axonframework.domain.api.type.MoneyTransferId

data class MoneyTransfers(
    private val activeMoneyTransfers: MutableMap<MoneyTransferId, Amount> = mutableMapOf()
) {
    fun getReservedAmount(): Amount = Amount(activeMoneyTransfers.values.sumOf { it.value })

    fun getAmountForTransfer(moneyTransferId: MoneyTransferId): Amount? = activeMoneyTransfers[moneyTransferId]

    fun hasMoneyTransfer(moneyTransferId: MoneyTransferId): Boolean = activeMoneyTransfers.containsKey(moneyTransferId)

    fun initTransfer(moneyTransferId: MoneyTransferId, amount: Amount) {
        activeMoneyTransfers[moneyTransferId] = amount
    }

    fun completeTransfer(moneyTransferId: MoneyTransferId) {
        activeMoneyTransfers.remove(moneyTransferId)
    }

    fun cancelTransfer(moneyTransferId: MoneyTransferId) {
        activeMoneyTransfers.remove(moneyTransferId)
    }

}
