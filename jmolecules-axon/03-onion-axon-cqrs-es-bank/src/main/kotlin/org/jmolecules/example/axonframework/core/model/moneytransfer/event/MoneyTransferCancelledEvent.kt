package org.jmolecules.example.axonframework.core.model.moneytransfer.event

import org.axonframework.serialization.Revision
import org.jmolecules.event.annotation.DomainEvent
import org.jmolecules.example.axonframework.core.model.moneytransfer.MoneyTransferId
import org.jmolecules.example.axonframework.core.model.moneytransfer.Reason

@Revision("1")
@DomainEvent(namespace = "axon.bank", name = "MoneyTransferCancelledEvent")
data class MoneyTransferCancelledEvent(
  val moneyTransferId: MoneyTransferId,
  val reason: Reason
)
