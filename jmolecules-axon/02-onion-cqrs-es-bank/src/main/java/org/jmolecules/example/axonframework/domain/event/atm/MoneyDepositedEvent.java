package org.jmolecules.example.axonframework.domain.event.atm;

import org.axonframework.serialization.Revision;
import org.jmolecules.event.annotation.DomainEvent;

@Revision("1")
@DomainEvent(namespace = "axon.bank", name = "MoneyDepositedEvent")
public record MoneyDepositedEvent(
  String accountId,
  int amount
) {
  // empty
}
