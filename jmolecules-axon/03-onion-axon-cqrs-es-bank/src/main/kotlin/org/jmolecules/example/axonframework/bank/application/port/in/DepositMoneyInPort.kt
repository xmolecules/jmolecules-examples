package org.jmolecules.example.axonframework.bank.application.port.`in`

import org.jmolecules.example.axonframework.bank.domain.bankaccount.type.MaximumBalanceExceeded
import org.jmolecules.example.axonframework.bank.domain.bankaccount.type.AccountId
import org.jmolecules.example.axonframework.bank.domain.bankaccount.type.Amount
import java.util.concurrent.CompletableFuture

/**
 * Port to address UC-002 Deposit Money (ATM).
 */
interface DepositMoneyInPort {
  /**
   * Deposits money on the given account.
   * @param accountId account id.
   * @param amount amount to deposit.
   */
  @Throws(MaximumBalanceExceeded::class)
  fun depositMoney(accountId: AccountId, amount: Amount): CompletableFuture<Unit>
}
