package org.jmolecules.example.axonframework.domain.api.command.atm;

import org.jmolecules.architecture.cqrs.annotation.Command;
import org.jmolecules.ddd.annotation.Association;

@Command(namespace = "axon.bank", name = "DepositMoneyCommand")
public record DepositMoneyCommand(
  @Association
  String accountId,
  int amount
) {
  // empty
}
