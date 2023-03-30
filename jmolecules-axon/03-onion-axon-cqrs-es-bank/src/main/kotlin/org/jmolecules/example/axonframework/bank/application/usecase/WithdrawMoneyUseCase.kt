package org.jmolecules.example.axonframework.bank.application.usecase

import org.jmolecules.example.axonframework.bank.application.port.`in`.WithdrawMoneyInPort
import org.jmolecules.example.axonframework.bank.application.port.out.command.AtmCommandPort
import org.jmolecules.example.axonframework.bank.domain.bankaccount.type.AccountId
import org.jmolecules.example.axonframework.bank.domain.bankaccount.type.Amount
import org.jmolecules.example.axonframework.bank.domain.bankaccount.type.InsufficientBalance
import java.util.concurrent.CompletableFuture

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
  @Throws(InsufficientBalance::class)
  override fun withdrawMoney(accountId: AccountId, amount: Amount): CompletableFuture<Unit> {
    return atmOutPort.withdrawMoney(accountId, amount)
  }
}
