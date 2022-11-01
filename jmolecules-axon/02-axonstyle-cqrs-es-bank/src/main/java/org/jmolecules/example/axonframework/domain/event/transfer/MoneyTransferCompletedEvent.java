package org.jmolecules.example.axonframework.domain.event.transfer;

import org.axonframework.serialization.Revision;
import org.jmolecules.event.annotation.DomainEvent;

@Revision("1")
@DomainEvent(namespace = "axon.bank", name = "MoneyTransferCompletedEvent")
public record MoneyTransferCompletedEvent(
  String moneyTransferId,
  String sourceAccountId,

  int amount
) {

}
