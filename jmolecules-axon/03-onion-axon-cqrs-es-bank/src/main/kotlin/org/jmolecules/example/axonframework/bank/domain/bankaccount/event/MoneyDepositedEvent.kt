package org.jmolecules.example.axonframework.bank.domain.bankaccount.event

import org.axonframework.serialization.Revision
import org.jmolecules.event.annotation.DomainEvent
import org.jmolecules.example.axonframework.bank.domain.bankaccount.type.AccountId
import org.jmolecules.example.axonframework.bank.domain.bankaccount.type.Amount

@Revision("1")
@DomainEvent(namespace = "axon.bank", name = "MoneyDepositedEvent")
data class MoneyDepositedEvent(
  val accountId: AccountId,
  val amount: Amount
)
