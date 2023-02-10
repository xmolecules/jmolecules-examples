package org.jmolecules.example.axonframework.core.application

import org.jmolecules.example.axonframework.core.model.bankaccount.type.AccountId
import org.jmolecules.example.axonframework.core.model.bankaccount.type.Amount
import org.jmolecules.example.axonframework.core.model.bankaccount.exception.MaximumBalanceExceededException
import org.jmolecules.example.axonframework.core.port.out.command.AtmCommandPort
import org.springframework.stereotype.Component

/**
 * Operations on bank account available at ATM.
 */
@Component
class DepositMoneyUseCase(
  private val atmOutPort: AtmCommandPort
) {

  /**
   * Deposits money on the given account.
   * @param accountId account id.
   * @param amount amount to deposit.
   */
  @Throws(MaximumBalanceExceededException::class)
  fun depositMoney(accountId: AccountId, amount: Amount) {
    atmOutPort.depositMoney(accountId, amount)
  }
}
