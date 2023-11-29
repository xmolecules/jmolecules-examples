package org.jmolecules.example.axonframework.bank.application.port.`in`

import org.jmolecules.example.axonframework.bank.domain.bankaccount.type.CurrentBalance
import org.jmolecules.example.axonframework.bank.domain.bankaccount.type.AccountId
import java.util.*
import java.util.concurrent.CompletableFuture

/**
 * Port to access UC-005.
 */
interface RetrieveBankAccountInformationInPort {
  /**
   * Retrieves the current balance of the bank account.
   * @param accountId account id.
   * @return current balance.
   */
  fun getCurrentBalance(accountId: AccountId): CompletableFuture<Optional<CurrentBalance>>
}
