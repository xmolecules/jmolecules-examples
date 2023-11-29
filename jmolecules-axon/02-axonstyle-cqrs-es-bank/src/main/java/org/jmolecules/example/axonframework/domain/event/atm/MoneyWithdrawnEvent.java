package org.jmolecules.example.axonframework.domain.event.atm;

import org.axonframework.serialization.Revision;
import org.jmolecules.event.annotation.DomainEvent;

@Revision("1")
@DomainEvent(namespace = "axon.bank", name = "MoneyWithdrawnEvent")
public record MoneyWithdrawnEvent(
  String accountId,
  int amount
) {
  // empty
}
