package org.jmolecules.example.axonframework.domain.api.command.transfer;

import org.jmolecules.architecture.cqrs.annotation.Command;
import org.jmolecules.ddd.annotation.Association;

@Command(namespace = "axon.bank", name = "CompleteMoneyTransferCommand")
public record CompleteMoneyTransferCommand(

  @Association
  String sourceAccountId,

  String moneyTransferId

) {

}
