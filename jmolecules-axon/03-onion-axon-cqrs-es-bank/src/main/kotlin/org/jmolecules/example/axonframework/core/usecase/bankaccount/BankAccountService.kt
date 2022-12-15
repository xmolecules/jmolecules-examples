package org.jmolecules.example.axonframework.core.usecase.bankaccount

import org.jmolecules.example.axonframework.core.model.bankaccount.AccountId
import org.jmolecules.example.axonframework.core.model.bankaccount.Balance
import org.jmolecules.example.axonframework.core.model.bankaccount.InsufficientBalanceException
import org.jmolecules.example.axonframework.core.model.bankaccount.MaximumBalanceExceededException
import org.jmolecules.example.axonframework.core.model.bankaccount.query.BankAccountCurrentBalance
import java.util.*
import java.util.concurrent.CompletableFuture

/**
 * Application Service to manipulate the bank account.
 */
interface BankAccountService {
  /**
   * Creates a bank account.
   * @param accountId account id.
   * @param initialBalance balance of the account.
   */
  @Throws(MaximumBalanceExceededException::class, InsufficientBalanceException::class)
  fun createBankAccount(accountId: AccountId, initialBalance: Balance)

  /**
   * Retrieves the current balance of the bank account.
   * @param accountId account id.
   * @return current balance.
   */
  fun getCurrentBalance(accountId: AccountId): CompletableFuture<Optional<BankAccountCurrentBalance>>
}
