package org.jmolecules.example.axonframework.domain.event;

import org.axonframework.serialization.Revision;

@Revision("1")
public record BankAccountCreatedEvent(
  String accountId,
  int initialBalance
) {
  // empty
}
