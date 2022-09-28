package org.jmolecules.example.axonframework.domain.event.transfer;

import org.axonframework.serialization.Revision;

@Revision("1")
public record MoneyTransferredEvent(
  String moneyTransferId,
  String sourceAccountId,
  String targetAccountId,
  int amount
) {

}
