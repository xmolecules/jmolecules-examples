package org.jmolecules.example.axonframework.infrastructure.adapter.out.commandmodel.moneytransfer

import org.jmolecules.architecture.cqrs.annotation.Command
import org.jmolecules.ddd.annotation.Association
import org.jmolecules.example.axonframework.core.model.bankaccount.type.AccountId
import org.jmolecules.example.axonframework.core.model.moneytransfer.type.MoneyTransferId

/**
 * Internal command to complete money transfer.
 */
@Command(namespace = "axon.bank", name = "CompleteMoneyTransferCommand")
data class CompleteMoneyTransferCommand(

  @Association
  val sourceAccountId: AccountId,
  val moneyTransferId: MoneyTransferId

)
