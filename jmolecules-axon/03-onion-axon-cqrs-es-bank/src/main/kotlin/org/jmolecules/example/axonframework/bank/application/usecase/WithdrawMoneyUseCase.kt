package org.jmolecules.example.axonframework.bank.application.usecase

import org.jmolecules.example.axonframework.bank.application.port.`in`.WithdrawMoneyInPort
import org.jmolecules.example.axonframework.bank.application.port.out.command.AtmCommandPort
import org.jmolecules.example.axonframework.bank.domain.bankaccount.type.AccountId
import org.jmolecules.example.axonframework.bank.domain.bankaccount.type.Amount
import org.jmolecules.example.axonframework.bank.domain.bankaccount.type.InsufficientBalanceException

/**
 * Use case to withdraw money.
 */
class WithdrawMoneyUseCase(
  private val atmOutPort: AtmCommandPort
) : WithdrawMoneyInPort {

  /**
   * Withdraws money from given account.
   * @param accountId account id.
   * @param amount amount to withdraw.
   */
  @Throws(InsufficientBalanceException::class)
  override fun withdrawMoney(accountId: AccountId, amount: Amount) {
    atmOutPort.withdrawMoney(accountId, amount)
  }
}
