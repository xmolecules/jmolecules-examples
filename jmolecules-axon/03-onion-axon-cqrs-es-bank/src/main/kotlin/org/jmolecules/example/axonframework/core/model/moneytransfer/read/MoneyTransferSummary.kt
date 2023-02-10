package org.jmolecules.example.axonframework.core.model.moneytransfer.read

import org.jmolecules.example.axonframework.core.model.bankaccount.type.AccountId
import org.jmolecules.example.axonframework.core.model.bankaccount.type.Amount
import org.jmolecules.example.axonframework.core.model.moneytransfer.type.MoneyTransferId
import org.jmolecules.example.axonframework.core.model.moneytransfer.type.Reason

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
