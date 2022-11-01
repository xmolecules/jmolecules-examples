package org.jmolecules.example.axonframework.domain.event.transfer

import org.axonframework.serialization.Revision
import org.jmolecules.event.annotation.DomainEvent
import org.jmolecules.example.axonframework.domain.api.type.MoneyTransferId
import org.jmolecules.example.axonframework.domain.api.type.Reason

@Revision("1")
@DomainEvent(namespace = "axon.bank", name = "MoneyTransferCancelledEvent")
data class MoneyTransferCancelledEvent(
    val moneyTransferId: MoneyTransferId,
    val reason: Reason
)
