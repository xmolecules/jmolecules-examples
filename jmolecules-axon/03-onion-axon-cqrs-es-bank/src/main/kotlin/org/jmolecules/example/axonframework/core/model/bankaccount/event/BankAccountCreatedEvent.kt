package org.jmolecules.example.axonframework.core.model.bankaccount.event

import org.axonframework.serialization.Revision
import org.jmolecules.event.annotation.DomainEvent
import org.jmolecules.example.axonframework.core.model.bankaccount.AccountId
import org.jmolecules.example.axonframework.core.model.bankaccount.Balance

@Revision("1")
@DomainEvent(namespace = "axon.bank", name = "BankAccountCreatedEvent")
data class BankAccountCreatedEvent(
  val accountId: AccountId,
  val initialBalance: Balance
)
