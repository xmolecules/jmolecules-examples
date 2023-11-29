package org.jmolecules.example.axonframework.bank.application.port.out.command

import org.jmolecules.architecture.hexagonal.SecondaryPort
import org.jmolecules.example.axonframework.bank.domain.bankaccount.type.AccountId
import org.jmolecules.example.axonframework.bank.domain.bankaccount.type.Balance
import java.util.concurrent.CompletableFuture

/**
 * Direct account manipulation.
 */
@SecondaryPort
interface BankAccountCommandPort {
  /**
   * Creates a new bank account.
   * @param accountId account id.
   * @param initialBalance initial balance.
   */
  fun createBankAccount(accountId: AccountId, initialBalance: Balance): CompletableFuture<Unit>
}
