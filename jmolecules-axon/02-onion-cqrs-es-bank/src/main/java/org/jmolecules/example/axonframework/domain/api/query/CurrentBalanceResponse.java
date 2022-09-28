package org.jmolecules.example.axonframework.domain.api.query;

public record CurrentBalanceResponse(
  String accountId,
  int currentBalance
) {
  // empty
}
