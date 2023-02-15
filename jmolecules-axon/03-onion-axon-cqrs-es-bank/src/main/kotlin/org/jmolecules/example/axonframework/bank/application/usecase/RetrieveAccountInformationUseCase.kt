package org.jmolecules.example.axonframework.bank.application.usecase

import org.jmolecules.example.axonframework.bank.domain.bankaccount.type.AccountId
import org.jmolecules.example.axonframework.bank.domain.bankaccount.read.BankAccountCurrentBalance
import org.jmolecules.example.axonframework.bank.application.port.`in`.RetrieveBankAccountInformationInPort
import org.jmolecules.example.axonframework.bank.application.port.out.query.BankAccountQueryPort
import org.springframework.stereotype.Component
import java.util.*
import java.util.concurrent.CompletableFuture

/**
 * Account information retrieval use case.
 */
@Component
class RetrieveAccountInformationUseCase(
  private val bankAccountQueryPort: BankAccountQueryPort
) : RetrieveBankAccountInformationInPort {

  /**
   * Retrieves the current balance of the bank account.
   * @param accountId account id.
   * @return current balance.
   */
  override fun getCurrentBalance(accountId: AccountId): CompletableFuture<Optional<BankAccountCurrentBalance>> {
    return bankAccountQueryPort.getCurrentBalance(accountId)
  }
}
