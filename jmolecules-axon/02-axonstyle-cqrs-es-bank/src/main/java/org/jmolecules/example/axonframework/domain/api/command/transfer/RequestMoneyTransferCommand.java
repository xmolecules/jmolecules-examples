package org.jmolecules.example.axonframework.domain.api.command.transfer;

import org.jmolecules.architecture.cqrs.annotation.Command;
import org.jmolecules.ddd.annotation.Association;

/**
 * Received by the source account.
 * Reserves amount for transfer and starts saga.
 */
@Command(namespace = "axon.bank", name = "RequestMoneyTransferCommand")
public record RequestMoneyTransferCommand(

  @Association
  String sourceAccountId,

  String targetAccountId,

  int amount
) {

  // empty

}
