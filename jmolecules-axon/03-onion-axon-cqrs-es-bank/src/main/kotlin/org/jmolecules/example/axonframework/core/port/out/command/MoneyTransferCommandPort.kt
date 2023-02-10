package org.jmolecules.example.axonframework.core.port.out.command

import org.jmolecules.example.axonframework.core.model.bankaccount.type.AccountId
import org.jmolecules.example.axonframework.core.model.bankaccount.type.Amount
import org.jmolecules.example.axonframework.core.model.moneytransfer.state.MoneyTransfer
import org.jmolecules.example.axonframework.core.model.moneytransfer.type.MoneyTransferId
import org.jmolecules.example.axonframework.core.model.moneytransfer.type.Reason

interface MoneyTransferCommandPort {
  fun transferMoney(sourceAccountId: AccountId, targetAccountId: AccountId, amount: Amount): MoneyTransferId

  fun receiveMoneyTransfer(moneyTransfer: MoneyTransfer)

  fun completeMoneyTransfer(moneyTransfer: MoneyTransfer)

  fun cancelMoneyTransfer(moneyTransfer: MoneyTransfer, reason: Reason)
}
