package org.jmolecules.example.axonframework.domain.api.query;

public record MoneyTransfer(
  String moneyTransferId,
  String sourceAccountId,
  String targetAccountId,
  int amount,
  Boolean success,
  String message
) {

  public MoneyTransfer(String moneyTransferId, String sourceAccountId, String targetAccountId, int amount) {
    this(moneyTransferId, sourceAccountId, targetAccountId, amount, null, null);
  }

  public MoneyTransfer(String moneyTransferId, String sourceAccountId, String targetAccountId, int amount, String message) {
    this(moneyTransferId, sourceAccountId, targetAccountId, amount, message == null, message);
  }

}
