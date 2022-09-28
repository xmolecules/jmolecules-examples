package org.jmolecules.example.axonframework.domain.api.command.transfer;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public record CompleteMoneyTransferCommand(

  @TargetAggregateIdentifier
  String sourceAccountId,

  String moneyTransferId

) {

}
