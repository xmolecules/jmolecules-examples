package org.jmolecules.example.axonframework.domain.event.transfer;

import org.axonframework.serialization.Revision;

@Revision("1")
public record MoneyTransferCancelledEvent(
  String moneyTransferId,
  String reason
) {

}
