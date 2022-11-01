package org.jmolecules.example.axonframework.domain.api.exception;
public class MaximumBalanceExceededException extends RuntimeException {


  public MaximumBalanceExceededException(String accountId, int currentBalance, int depositAmount, int maxAmount) {
    super(String.format("BankAccount[id=%s, currentBalance=%d]: Deposit of amount=%d not allowed, would exceed max. balance of %d",
      accountId,
      currentBalance,
      depositAmount,
      maxAmount
    ));
  }
}
