package org.jmolecules.example.axonframework.domain.event.transfer;

import org.axonframework.serialization.Revision;

@Revision("1")
public record MoneyTransferReceivedEvent(
  String moneyTransferId,
  String targetAccountId,
  int amount
) {

}
