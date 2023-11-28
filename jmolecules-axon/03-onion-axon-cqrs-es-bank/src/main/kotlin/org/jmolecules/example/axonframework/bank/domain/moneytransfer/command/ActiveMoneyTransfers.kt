package org.jmolecules.example.axonframework.bank.domain.moneytransfer.command

import org.jmolecules.architecture.onion.classical.DomainModelRing
import org.jmolecules.example.axonframework.bank.adapter.out.commandmodel.bankaccount.CreateBankAccountCommand
import org.jmolecules.example.axonframework.bank.domain.bankaccount.type.AccountId
import org.jmolecules.example.axonframework.bank.domain.bankaccount.type.Amount
import org.jmolecules.example.axonframework.bank.domain.bankaccount.type.Balance
import org.jmolecules.example.axonframework.bank.domain.bankaccount.type.ReservedAmount
import org.jmolecules.example.axonframework.bank.domain.moneytransfer.type.MoneyTransferId

/**
 * Represents money transfers of a bank account.
 */
data class ActiveMoneyTransfers(
  private val activeMoneyTransfers: MutableMap<MoneyTransferId, Amount> = mutableMapOf()
) {
  fun getReservedAmount(): ReservedAmount = ReservedAmount.of(activeMoneyTransfers.values.sumOf { it.value })

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
