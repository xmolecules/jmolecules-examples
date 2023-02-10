package org.jmolecules.example.axonframework.core.model.bankaccount.state

import org.jmolecules.example.axonframework.core.model.bankaccount.type.Amount
import org.jmolecules.example.axonframework.core.model.bankaccount.state.BankAccountCreationVerificationResult.*
import org.jmolecules.example.axonframework.core.model.bankaccount.type.Balance
import org.jmolecules.example.axonframework.core.model.bankaccount.type.ReservedAmount

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
  fun canDecrease(amount: Amount, reserved: ReservedAmount) = currentBalance - reserved - amount >= minimumBalance

  fun increase(amount: Amount) = copy(currentBalance = currentBalance + amount)
  fun decrease(amount: Amount) = copy(currentBalance = currentBalance + amount)
}
