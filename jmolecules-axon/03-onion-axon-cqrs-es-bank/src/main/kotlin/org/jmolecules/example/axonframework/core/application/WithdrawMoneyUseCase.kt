package org.jmolecules.example.axonframework.core.application

import org.jmolecules.example.axonframework.core.model.bankaccount.exception.InsufficientBalanceException
import org.jmolecules.example.axonframework.core.model.bankaccount.type.AccountId
import org.jmolecules.example.axonframework.core.model.bankaccount.type.Amount
import org.jmolecules.example.axonframework.core.port.`in`.WithdrawMoneyInPort
import org.jmolecules.example.axonframework.core.port.out.command.AtmCommandPort
import org.springframework.stereotype.Component

/**
 * Use case to withdraw money.
 */
@Component
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
