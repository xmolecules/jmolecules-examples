package org.jmolecules.example.axonframework.core.application

import org.jmolecules.example.axonframework.core.model.bankaccount.type.AccountId
import org.jmolecules.example.axonframework.core.model.bankaccount.read.BankAccountCurrentBalance
import org.jmolecules.example.axonframework.core.port.out.command.BankAccountCommandPort
import org.jmolecules.example.axonframework.core.port.out.query.BankAccountQueryPort
import org.springframework.stereotype.Component
import java.util.*
import java.util.concurrent.CompletableFuture

/**
 * Account information retrieval use case.
 */
@Component
class RetrieveAccountInformationUseCase(
  private val bankAccountQueryPort: BankAccountQueryPort
) {

  /**
   * Retrieves the current balance of the bank account.
   * @param accountId account id.
   * @return current balance.
   */
  fun getCurrentBalance(accountId: AccountId): CompletableFuture<Optional<BankAccountCurrentBalance>> {
    return bankAccountQueryPort.getCurrentBalance(accountId)
  }
}
