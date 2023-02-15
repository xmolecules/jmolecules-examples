package org.jmolecules.example.axonframework.bank.domain.moneytransfer.state

import org.jmolecules.example.axonframework.bank.domain.bankaccount.type.AccountId
import org.jmolecules.example.axonframework.bank.domain.bankaccount.type.Amount
import org.jmolecules.example.axonframework.bank.domain.moneytransfer.type.MoneyTransferId

/**
 * Represents a single money transfer.
 */
data class MoneyTransfer(
  val moneyTransferId: MoneyTransferId,
  val sourceAccountId: AccountId,
  val targetAccountId: AccountId,
  val amount: Amount
)
