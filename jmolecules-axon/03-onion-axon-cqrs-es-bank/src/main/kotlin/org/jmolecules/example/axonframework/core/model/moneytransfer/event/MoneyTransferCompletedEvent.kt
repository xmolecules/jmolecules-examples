package org.jmolecules.example.axonframework.core.model.moneytransfer.event

import org.axonframework.serialization.Revision
import org.jmolecules.event.annotation.DomainEvent
import org.jmolecules.example.axonframework.core.model.bankaccount.AccountId
import org.jmolecules.example.axonframework.core.model.bankaccount.Amount
import org.jmolecules.example.axonframework.core.model.moneytransfer.MoneyTransferId

@Revision("1")
@DomainEvent(namespace = "axon.bank", name = "MoneyTransferCompletedEvent")
data class MoneyTransferCompletedEvent(
  val moneyTransferId: MoneyTransferId,
  val sourceAccountId: AccountId,
  val amount: Amount
)
