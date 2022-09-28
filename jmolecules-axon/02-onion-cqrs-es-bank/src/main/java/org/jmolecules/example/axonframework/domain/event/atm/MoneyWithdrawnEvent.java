package org.jmolecules.example.axonframework.domain.event.atm;

import org.axonframework.serialization.Revision;

@Revision("1")
public record MoneyWithdrawnEvent(
  String accountId,
  int amount
) {
  // empty
}
