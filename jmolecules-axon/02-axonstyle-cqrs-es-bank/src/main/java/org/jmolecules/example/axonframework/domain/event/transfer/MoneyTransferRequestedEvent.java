package org.jmolecules.example.axonframework.domain.event.transfer;

import org.axonframework.serialization.Revision;
import org.jmolecules.event.annotation.DomainEvent;

@Revision("1")
@DomainEvent(namespace = "axon.bank", name = "MoneyTransferRequestedEvent")
public record MoneyTransferRequestedEvent(
  String moneyTransferId,
  String sourceAccountId,
  String targetAccountId,
  int amount
) {
  // empty
}
