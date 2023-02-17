package org.jmolecules.example.axonframework.bank.domain.moneytransfer.type

import org.jmolecules.example.axonframework.bank.domain.bankaccount.type.AccountId
import org.jmolecules.example.axonframework.bank.domain.bankaccount.type.Amount

/**
 * Money transfer model.
 */
data class MoneyTransferSummary(
  val moneyTransferId: MoneyTransferId,
  val sourceAccountId: AccountId,
  val targetAccountId: AccountId,
  val amount: Amount,
  val status: MoneyTransferStatus
)
