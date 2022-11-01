package org.jmolecules.example.axonframework.domain.event

import org.axonframework.serialization.Revision
import org.jmolecules.event.annotation.DomainEvent
import org.jmolecules.example.axonframework.domain.api.type.AccountId
import org.jmolecules.example.axonframework.domain.api.type.Balance

@Revision("1")
@DomainEvent(namespace = "axon.bank", name = "BankAccountCreatedEvent")
data class BankAccountCreatedEvent(
    val accountId: AccountId,
    val initialBalance: Balance
)
