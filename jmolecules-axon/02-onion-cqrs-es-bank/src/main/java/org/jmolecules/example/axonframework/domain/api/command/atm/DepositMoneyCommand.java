package org.jmolecules.example.axonframework.domain.api.command.atm;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public record DepositMoneyCommand(
  @TargetAggregateIdentifier
  String accountId,
  int amount
) {
  // empty
}
