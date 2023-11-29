package org.jmolecules.example.axonframework.bank.domain.moneytransfer.event

import org.axonframework.serialization.Revision
import org.jmolecules.event.annotation.DomainEvent
import org.jmolecules.example.axonframework.bank.domain.bankaccount.type.AccountId
import org.jmolecules.example.axonframework.bank.domain.bankaccount.type.Amount
import org.jmolecules.example.axonframework.bank.domain.moneytransfer.type.MoneyTransferId
import org.jmolecules.example.axonframework.bank.domain.moneytransfer.type.RejectionReason

@Revision("1")
@DomainEvent(namespace = "axon.bank", name = "MoneyTransferFailedEvent")
data class MoneyTransferFailedEvent(
  val moneyTransferId: MoneyTransferId,
  val sourceAccountId: AccountId,
  val targetAccountId: AccountId,
  val amount: Amount,
  val reason: RejectionReason
)
