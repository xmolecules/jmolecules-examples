package org.jmolecules.example.axonframework.infrastructure.adapter.out.commandmodel.moneytransfer

import org.jmolecules.architecture.cqrs.annotation.Command
import org.jmolecules.ddd.annotation.Association
import org.jmolecules.example.axonframework.core.model.bankaccount.type.AccountId
import org.jmolecules.example.axonframework.core.model.moneytransfer.type.MoneyTransferId
import org.jmolecules.example.axonframework.core.model.moneytransfer.type.Reason

/**
 * Internal command to cancel money transfer.
 */
@Command(namespace = "axon.bank", name = "CancelMoneyTransferCommand")
data class CancelMoneyTransferCommand(
  @Association
  val sourceAccountId: AccountId,
  val moneyTransferId: MoneyTransferId,
  val targetAccountId: AccountId,
  val reason: Reason,
)
