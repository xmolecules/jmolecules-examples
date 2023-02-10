package org.jmolecules.example.axonframework.infrastructure.adapter.out.commandmodel.moneytransfer

import org.jmolecules.architecture.cqrs.annotation.Command
import org.jmolecules.ddd.annotation.Association
import org.jmolecules.example.axonframework.core.model.bankaccount.type.AccountId
import org.jmolecules.example.axonframework.core.model.bankaccount.type.Amount
import org.jmolecules.example.axonframework.core.model.moneytransfer.type.MoneyTransferId

/**
 * Invoked by the saga, recipient is target account.
 */
@Command(namespace = "axon.bank", name = "ReceiveMoneyTransferCommand")
data class ReceiveMoneyTransferCommand(
  @Association
  val targetAccountId: AccountId,
  val moneyTransferId: MoneyTransferId,
  val amount: Amount
)
