package org.jmolecules.example.axonframework.bank.application.usecase

import org.jmolecules.example.axonframework.bank.domain.bankaccount.type.AccountId
import org.jmolecules.example.axonframework.bank.domain.bankaccount.type.CurrentBalance
import org.jmolecules.example.axonframework.bank.application.port.`in`.RetrieveBankAccountInformationInPort
import org.jmolecules.example.axonframework.bank.application.port.out.query.BankAccountQueryPort
import java.util.*
import java.util.concurrent.CompletableFuture

/**
 * Account information retrieval use case.
 */
class RetrieveAccountInformationUseCase(
  private val bankAccountQueryPort: BankAccountQueryPort
) : RetrieveBankAccountInformationInPort {

  /**
   * Retrieves the current balance of the bank account.
   * @param accountId account id.
   * @return current balance.
   */
  override fun getCurrentBalance(accountId: AccountId): CompletableFuture<Optional<CurrentBalance>> {
    return bankAccountQueryPort.findCurrentBalance(accountId)
  }
}
