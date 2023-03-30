package org.jmolecules.example.axonframework.bank.application.port.out.command

import org.jmolecules.architecture.hexagonal.SecondaryPort
import org.jmolecules.example.axonframework.bank.domain.bankaccount.type.InsufficientBalance
import org.jmolecules.example.axonframework.bank.domain.bankaccount.type.MaximumBalanceExceeded
import org.jmolecules.example.axonframework.bank.domain.bankaccount.type.AccountId
import org.jmolecules.example.axonframework.bank.domain.bankaccount.type.Amount
import java.util.concurrent.CompletableFuture
import kotlin.jvm.Throws

/**
 * Port to send ATM commands.
 */
@SecondaryPort
interface AtmCommandPort {
  /**
   * Deposits money to the account.
   * @param accountId account id.
   * @param amount amount to deposit.
   * @throws MaximumBalanceExceeded if the resulting balance is above the maximum amount.
   */
  @Throws(MaximumBalanceExceeded::class)
  fun depositMoney(accountId: AccountId, amount: Amount): CompletableFuture<Unit>

  /**
   * Withdraws money from account.
   * @param accountId account id.
   * @param amount to withdraw.
   * @throws InsufficientBalance the remaining balance is below minimum account balance.
   */
  @Throws(InsufficientBalance::class)
  fun withdrawMoney(accountId: AccountId, amount: Amount): CompletableFuture<Unit>
}
