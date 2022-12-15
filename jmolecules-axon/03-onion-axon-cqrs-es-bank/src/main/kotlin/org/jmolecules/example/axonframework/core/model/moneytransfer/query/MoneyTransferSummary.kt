package org.jmolecules.example.axonframework.core.model.moneytransfer.query

import org.jmolecules.example.axonframework.core.model.bankaccount.AccountId
import org.jmolecules.example.axonframework.core.model.bankaccount.Amount
import org.jmolecules.example.axonframework.core.model.moneytransfer.MoneyTransferId
import org.jmolecules.example.axonframework.core.model.moneytransfer.Reason

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
