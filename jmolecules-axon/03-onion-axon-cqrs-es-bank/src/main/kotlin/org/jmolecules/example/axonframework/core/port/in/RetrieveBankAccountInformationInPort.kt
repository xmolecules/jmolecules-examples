package org.jmolecules.example.axonframework.core.port.`in`

import org.jmolecules.example.axonframework.core.model.bankaccount.read.BankAccountCurrentBalance
import org.jmolecules.example.axonframework.core.model.bankaccount.type.AccountId
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
  fun getCurrentBalance(accountId: AccountId): CompletableFuture<Optional<BankAccountCurrentBalance>>
}
