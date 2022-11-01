package org.jmolecules.example.axonframework.domain.event;

import org.axonframework.serialization.Revision;
import org.jmolecules.event.annotation.DomainEvent;

@Revision("1")
@DomainEvent(namespace = "axon.bank", name = "BankAccountCreatedEvent")
public record BankAccountCreatedEvent(
  String accountId,
  int initialBalance
) {
  // empty
}
