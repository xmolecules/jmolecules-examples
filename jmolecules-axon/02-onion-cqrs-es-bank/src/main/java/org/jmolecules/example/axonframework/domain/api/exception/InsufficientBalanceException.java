package org.jmolecules.example.axonframework.domain.api.exception;


public class InsufficientBalanceException extends RuntimeException{

  public InsufficientBalanceException(String accountId, int currentBalance, int withdrawAmount, int minAmount) {
    super(String.format("BankAccount[id=%s, currentBalance=%d]: Withdrawal of amount=%d not allowed, would subceed min. balance of %d",
                        accountId,
                        currentBalance,
                        withdrawAmount,
                        minAmount
    ));
  }
}
