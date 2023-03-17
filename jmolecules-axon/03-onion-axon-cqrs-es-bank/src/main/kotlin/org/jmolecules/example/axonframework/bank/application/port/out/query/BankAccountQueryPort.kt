package org.jmolecules.example.axonframework.bank.application.port.out.query

import org.jmolecules.architecture.hexagonal.SecondaryPort
import org.jmolecules.example.axonframework.bank.domain.bankaccount.type.CurrentBalance
import org.jmolecules.example.axonframework.bank.domain.bankaccount.type.AccountId
import java.util.*
import java.util.concurrent.CompletableFuture

/**
 * Query information about bank account.
 */
@SecondaryPort
interface BankAccountQueryPort {
  /**
   * Retrieves current balance.
   */
  fun findCurrentBalance(accountId: AccountId): CompletableFuture<Optional<CurrentBalance>>
}
