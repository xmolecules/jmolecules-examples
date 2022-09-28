package org.jmolecules.example.axonframework.domain.api.command;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public record CreateBankAccountCommand(
  @TargetAggregateIdentifier
  String accountId,
  int initialBalance
) {
  // empty
}
