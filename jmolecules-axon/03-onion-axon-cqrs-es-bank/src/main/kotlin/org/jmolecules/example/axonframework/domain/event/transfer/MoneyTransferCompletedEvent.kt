package org.jmolecules.example.axonframework.domain.event.transfer

import org.axonframework.serialization.Revision
import org.jmolecules.event.annotation.DomainEvent
import org.jmolecules.example.axonframework.domain.api.type.AccountId
import org.jmolecules.example.axonframework.domain.api.type.Amount
import org.jmolecules.example.axonframework.domain.api.type.MoneyTransferId

@Revision("1")
@DomainEvent(namespace = "axon.bank", name = "MoneyTransferCompletedEvent")
data class MoneyTransferCompletedEvent(
    val moneyTransferId: MoneyTransferId,
    val sourceAccountId: AccountId,
    val amount: Amount
)
