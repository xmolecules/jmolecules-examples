package org.jmolecules.example.axonframework.bank.domain.moneytransfer.read

import org.jmolecules.example.axonframework.bank.domain.bankaccount.type.AccountId
import org.jmolecules.example.axonframework.bank.domain.bankaccount.type.Amount
import org.jmolecules.example.axonframework.bank.domain.moneytransfer.type.MoneyTransferId
import org.jmolecules.example.axonframework.bank.domain.moneytransfer.type.Reason

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
