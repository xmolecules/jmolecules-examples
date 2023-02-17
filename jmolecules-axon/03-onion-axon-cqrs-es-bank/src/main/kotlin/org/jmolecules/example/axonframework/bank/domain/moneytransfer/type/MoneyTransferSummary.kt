package org.jmolecules.example.axonframework.bank.domain.moneytransfer.type

import org.jmolecules.example.axonframework.bank.domain.bankaccount.type.AccountId
import org.jmolecules.example.axonframework.bank.domain.bankaccount.type.Amount

/**
 * Public visible money transfer.
 */
// FIXME -> get rid of optional nullable field and status? use different types for that?
data class MoneyTransferSummary(
  val moneyTransferId: MoneyTransferId,
  val sourceAccountId: AccountId,
  val targetAccountId: AccountId,
  val amount: Amount,
  val success: Boolean,
  val errorMessage: RejectionReason? = null
)
