package org.jmolecules.example.axonframework.domain.event.transfer;

import org.axonframework.serialization.Revision;

@Revision("1")
public record MoneyTransferRequestedEvent(
  String moneyTransferId,
  String sourceAccountId,
  String targetAccountId,
  int amount
) {
  // empty
}
