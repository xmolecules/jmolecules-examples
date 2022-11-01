package org.jmolecules.example.axonframework.domain.event.transfer;

import org.axonframework.serialization.Revision;
import org.jmolecules.event.annotation.DomainEvent;

@Revision("1")
@DomainEvent(namespace = "axon.bank", name = "MoneyTransferCancelledEvent")
public record MoneyTransferCancelledEvent(
  String moneyTransferId,
  String reason
) {

}
