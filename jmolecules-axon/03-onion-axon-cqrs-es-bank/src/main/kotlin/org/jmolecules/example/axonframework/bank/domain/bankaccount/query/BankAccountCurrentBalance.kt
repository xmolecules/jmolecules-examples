package org.jmolecules.example.axonframework.bank.domain.bankaccount.query

import org.jmolecules.ddd.annotation.Entity
import org.jmolecules.ddd.annotation.Identity
import org.jmolecules.example.axonframework.bank.domain.bankaccount.type.AccountId
import org.jmolecules.example.axonframework.bank.domain.bankaccount.type.Amount
import org.jmolecules.example.axonframework.bank.domain.bankaccount.type.Balance

/**
 * Entity to store bank account current balance.
 */
@Entity
class BankAccountCurrentBalance(
  @Identity
  val accountId: AccountId,
  val balance: Balance
) {
  fun decreaseBalance(amount: Amount): BankAccountCurrentBalance {
    return BankAccountCurrentBalance(accountId, balance - amount)
  }

  fun increaseBalance(amount: Amount): BankAccountCurrentBalance {
    return BankAccountCurrentBalance(accountId, balance + amount)
  }
}
