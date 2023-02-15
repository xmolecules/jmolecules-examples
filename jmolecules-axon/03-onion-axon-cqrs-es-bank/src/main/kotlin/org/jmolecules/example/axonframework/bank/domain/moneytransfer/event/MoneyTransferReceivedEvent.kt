package org.jmolecules.example.axonframework.bank.domain.moneytransfer.event

import org.axonframework.serialization.Revision
import org.jmolecules.event.annotation.DomainEvent
import org.jmolecules.example.axonframework.bank.domain.bankaccount.type.AccountId
import org.jmolecules.example.axonframework.bank.domain.bankaccount.type.Amount
import org.jmolecules.example.axonframework.bank.domain.moneytransfer.type.MoneyTransferId

@Revision("1")
@DomainEvent(namespace = "axon.bank", name = "MoneyTransferReceivedEvent")
data class MoneyTransferReceivedEvent(
  val moneyTransferId: MoneyTransferId,
  val targetAccountId: AccountId,
  val amount: Amount
)
