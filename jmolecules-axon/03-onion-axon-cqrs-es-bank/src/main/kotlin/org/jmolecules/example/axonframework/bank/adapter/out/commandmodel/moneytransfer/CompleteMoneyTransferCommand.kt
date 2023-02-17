package org.jmolecules.example.axonframework.bank.adapter.out.commandmodel.moneytransfer

import org.jmolecules.architecture.cqrs.annotation.Command
import org.jmolecules.ddd.annotation.Association
import org.jmolecules.example.axonframework.bank.domain.bankaccount.type.AccountId
import org.jmolecules.example.axonframework.bank.domain.moneytransfer.type.MoneyTransferId

/**
 * Internal command to complete money transfer.
 */
@Command(namespace = "axon.bank", name = "CompleteMoneyTransferCommand")
data class CompleteMoneyTransferCommand(
  @Association
  val sourceAccountId: AccountId,
  val moneyTransferId: MoneyTransferId
)
