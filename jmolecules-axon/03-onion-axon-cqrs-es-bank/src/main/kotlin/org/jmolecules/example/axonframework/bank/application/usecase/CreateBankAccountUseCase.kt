package org.jmolecules.example.axonframework.bank.application.usecase

import org.jmolecules.example.axonframework.bank.application.port.`in`.CreateBankAccountInPort
import org.jmolecules.example.axonframework.bank.application.port.out.command.BankAccountCommandPort
import org.jmolecules.example.axonframework.bank.domain.bankaccount.type.AccountId
import org.jmolecules.example.axonframework.bank.domain.bankaccount.type.Balance
import org.jmolecules.example.axonframework.bank.domain.bankaccount.type.InsufficientBalance
import org.jmolecules.example.axonframework.bank.domain.bankaccount.type.MaximumBalanceExceeded
import java.util.concurrent.CompletableFuture

/**
 * Application Service to manipulate the bank account.
 */
class CreateBankAccountUseCase(
  private val bankAccountPort: BankAccountCommandPort
) : CreateBankAccountInPort {

  /**
   * Creates a bank account.
   * @param accountId account id.
   * @param initialBalance balance of the account.
   */
  @Throws(MaximumBalanceExceeded::class, InsufficientBalance::class)
  override fun createBankAccount(accountId: AccountId, initialBalance: Balance): CompletableFuture<Unit> {
    return bankAccountPort.createBankAccount(accountId, initialBalance)
  }
}
