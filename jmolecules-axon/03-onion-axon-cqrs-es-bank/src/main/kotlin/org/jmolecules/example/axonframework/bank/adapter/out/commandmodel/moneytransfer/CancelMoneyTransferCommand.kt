package org.jmolecules.example.axonframework.bank.adapter.out.commandmodel.moneytransfer

import org.jmolecules.architecture.cqrs.annotation.Command
import org.jmolecules.ddd.annotation.Association
import org.jmolecules.example.axonframework.bank.domain.bankaccount.type.AccountId
import org.jmolecules.example.axonframework.bank.domain.moneytransfer.type.MoneyTransferId
import org.jmolecules.example.axonframework.bank.domain.moneytransfer.type.RejectionReason

/**
 * Internal command to cancel money transfer.
 */
@Command(namespace = "axon.bank", name = "CancelMoneyTransferCommand")
data class CancelMoneyTransferCommand(
  @Association
  val sourceAccountId: AccountId,
  val moneyTransferId: MoneyTransferId,
  val targetAccountId: AccountId,
  val rejectionReason: RejectionReason,
)
