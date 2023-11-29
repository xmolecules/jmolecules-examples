package org.jmolecules.example.axonframework.bank.application.usecase

import org.jmolecules.example.axonframework.bank.application.port.`in`.DepositMoneyInPort
import org.jmolecules.example.axonframework.bank.application.port.out.command.AtmCommandPort
import org.jmolecules.example.axonframework.bank.domain.bankaccount.type.AccountId
import org.jmolecules.example.axonframework.bank.domain.bankaccount.type.Amount
import org.jmolecules.example.axonframework.bank.domain.bankaccount.type.MaximumBalanceExceeded
import java.util.concurrent.CompletableFuture

/**
 * Operations on bank account available at ATM.
 */
class DepositMoneyUseCase(
  private val atmOutPort: AtmCommandPort
) : DepositMoneyInPort {

  /**
   * Deposits money on the given account.
   * @param accountId account id.
   * @param amount amount to deposit.
   */
  @Throws(MaximumBalanceExceeded::class)
  override fun depositMoney(accountId: AccountId, amount: Amount): CompletableFuture<Unit> {
    return atmOutPort.depositMoney(accountId, amount)
  }
}
