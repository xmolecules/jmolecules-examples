package org.jmolecules.example.axonframework.core.model.bankaccount

import org.jmolecules.example.axonframework.core.model.bankaccount.BankAccountCreationVerificationResult.*

/**
 * Balance of a bank account: current and the upper and lower limits.
 */
data class BankAccountBalance(
  internal val currentBalance: Balance,
  internal val maximumBalance: Balance,
  internal val minimumBalance: Balance
) {

  companion object {

    fun validateInitialBalance(
      balance: Balance,
      maximumBalance: Balance,
      minimumBalance: Balance
    ): BankAccountCreationVerificationResult {
      return when {
        balance < minimumBalance -> InsufficientBalanceAmountVerificationResult(
          minimumBalance
        )

        balance > maximumBalance -> BalanceAmountExceededVerificationResult(
          maximumBalance
        )

        else -> ValidBalanceAmountVerificationResult
      }
    }


  }


  fun canIncrease(amount: Amount) = currentBalance + amount <= maximumBalance
  fun canDecrease(amount: Amount, reserved: Amount) = currentBalance - reserved - amount >= minimumBalance

  fun increase(amount: Amount) = copy(currentBalance = currentBalance + amount)
  fun decrease(amount: Amount) = copy(currentBalance = currentBalance + amount)
}
