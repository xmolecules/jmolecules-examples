package org.jmolecules.example.axonframework.domain.event.atm

import org.axonframework.serialization.Revision
import org.jmolecules.event.annotation.DomainEvent
import org.jmolecules.example.axonframework.domain.api.type.AccountId
import org.jmolecules.example.axonframework.domain.api.type.Amount

@Revision("1")
@DomainEvent(namespace = "axon.bank", name = "MoneyDepositedEvent")
data class MoneyDepositedEvent (
    val accountId: AccountId,
    val amount: Amount
)
