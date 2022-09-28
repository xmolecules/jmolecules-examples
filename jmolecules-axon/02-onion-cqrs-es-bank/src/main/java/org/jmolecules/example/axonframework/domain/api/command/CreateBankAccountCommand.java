package org.jmolecules.example.axonframework.domain.api.command;

import org.axonframework.modelling.command.TargetAggregateIdentifier;
import org.jmolecules.architecture.cqrs.annotation.Command;
import org.jmolecules.ddd.annotation.Association;

@Command(namespace = "axon.bank", name = "CreateBankAccountCommand")
public record CreateBankAccountCommand(
  @Association
  String accountId,
  int initialBalance
) {
  // empty
}
