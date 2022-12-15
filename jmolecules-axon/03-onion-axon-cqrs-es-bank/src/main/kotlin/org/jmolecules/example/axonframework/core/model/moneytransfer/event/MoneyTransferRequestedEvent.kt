package org.jmolecules.example.axonframework.core.model.moneytransfer.event

import org.axonframework.serialization.Revision
import org.jmolecules.event.annotation.DomainEvent
import org.jmolecules.example.axonframework.core.model.bankaccount.AccountId
import org.jmolecules.example.axonframework.core.model.bankaccount.Amount
import org.jmolecules.example.axonframework.core.model.moneytransfer.MoneyTransferId

@Revision("1")
@DomainEvent(namespace = "axon.bank", name = "MoneyTransferRequestedEvent")
data class MoneyTransferRequestedEvent(
  val moneyTransferId: MoneyTransferId,
  val sourceAccountId: AccountId,
  val targetAccountId: AccountId,
  val amount: Amount
)
