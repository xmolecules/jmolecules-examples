package org.jmolecules.example.axonframework.core.model.moneytransfer

import org.jmolecules.example.axonframework.core.model.bankaccount.Amount

data class BankAccountMoneyTransfers(
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
