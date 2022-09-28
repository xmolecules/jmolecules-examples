package org.jmolecules.example.axonframework.domain.api.command.transfer;

import org.jmolecules.architecture.cqrs.annotation.Command;
import org.jmolecules.ddd.annotation.Association;

@Command(namespace = "axon.bank", name = "CancelMoneyTransferCommand")
public record CancelMoneyTransferCommand(

  @Association
  String sourceAccountId,

  String moneyTransferId,

  String reason

) {

}
