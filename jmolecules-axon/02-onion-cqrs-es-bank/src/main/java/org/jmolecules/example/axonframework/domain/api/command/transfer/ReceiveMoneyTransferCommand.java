package org.jmolecules.example.axonframework.domain.api.command.transfer;

import org.jmolecules.architecture.cqrs.annotation.Command;
import org.jmolecules.ddd.annotation.Association;

/**
 * Invoked by the saga, recipient is target account.
 */
@Command(namespace = "axon.bank", name = "ReceiveMoneyTransferCommand")
public record ReceiveMoneyTransferCommand(
  @Association
  String targetAccountId,

  String moneyTransferId,

  int amount
) {

}
