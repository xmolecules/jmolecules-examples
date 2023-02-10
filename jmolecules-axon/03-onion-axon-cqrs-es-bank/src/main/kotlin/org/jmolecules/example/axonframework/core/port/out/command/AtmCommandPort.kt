package org.jmolecules.example.axonframework.core.port.out.command

import org.jmolecules.example.axonframework.core.model.bankaccount.type.AccountId
import org.jmolecules.example.axonframework.core.model.bankaccount.type.Amount

interface AtmCommandPort {
  fun depositMoney(accountId: AccountId, amount: Amount)
  fun withdrawMoney(accountId: AccountId, amount: Amount)
}
