package org.jmolecules.example.axonframework.core.port.out.command

import org.jmolecules.example.axonframework.core.model.bankaccount.type.AccountId
import org.jmolecules.example.axonframework.core.model.bankaccount.type.Balance

interface BankAccountCommandPort {
  fun createBankAccount(accountId: AccountId, initialBalance: Balance)
}
