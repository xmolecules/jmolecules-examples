package org.jmolecules.example.axonframework.bank.adapter.out.commandmodel.moneytransfer

import org.jmolecules.architecture.cqrs.Command
import org.jmolecules.ddd.annotation.Association
import org.jmolecules.example.axonframework.bank.domain.bankaccount.type.AccountId
import org.jmolecules.example.axonframework.bank.domain.bankaccount.type.Amount
import org.jmolecules.example.axonframework.bank.domain.moneytransfer.type.MoneyTransferId

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
