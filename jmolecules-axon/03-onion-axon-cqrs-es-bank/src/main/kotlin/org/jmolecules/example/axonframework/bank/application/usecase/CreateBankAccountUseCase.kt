package org.jmolecules.example.axonframework.bank.application.usecase

import org.jmolecules.example.axonframework.bank.domain.bankaccount.exception.InsufficientBalanceException
import org.jmolecules.example.axonframework.bank.domain.bankaccount.exception.MaximumBalanceExceededException
import org.jmolecules.example.axonframework.bank.domain.bankaccount.type.AccountId
import org.jmolecules.example.axonframework.bank.domain.bankaccount.type.Balance
import org.jmolecules.example.axonframework.bank.application.port.`in`.CreateBankAccountInPort
import org.jmolecules.example.axonframework.bank.application.port.out.command.BankAccountCommandPort
import org.springframework.stereotype.Component

/**
 * Application Service to manipulate the bank account.
 */
@Component
class CreateBankAccountUseCase(
  private val bankAccountPort: BankAccountCommandPort
) : CreateBankAccountInPort {

  /**
   * Creates a bank account.
   * @param accountId account id.
   * @param initialBalance balance of the account.
   */
  @Throws(MaximumBalanceExceededException::class, InsufficientBalanceException::class)
  override fun createBankAccount(accountId: AccountId, initialBalance: Balance) {
    bankAccountPort.createBankAccount(accountId, initialBalance)
  }
}
