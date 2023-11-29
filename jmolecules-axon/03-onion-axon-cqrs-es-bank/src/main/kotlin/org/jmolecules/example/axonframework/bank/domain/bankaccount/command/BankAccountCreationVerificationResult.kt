package org.jmolecules.example.axonframework.bank.domain.bankaccount.command

import org.jmolecules.example.axonframework.bank.domain.bankaccount.type.Balance

/**
 * Bank account verification result.
 */
sealed class BankAccountCreationVerificationResult {

  /**
   * Exceeded balance.
   */
  data class BalanceAmountExceededVerificationResult(
    val maximumBalance: Balance
  ) : BankAccountCreationVerificationResult()

  /**
   * Insufficient balance.
   */
  data class InsufficientBalanceAmountVerificationResult(
    val minimumBalance: Balance
  ) : BankAccountCreationVerificationResult()

  /**
   * Valid account.
   */
  object ValidBalanceAmountVerificationResult : BankAccountCreationVerificationResult()

}
