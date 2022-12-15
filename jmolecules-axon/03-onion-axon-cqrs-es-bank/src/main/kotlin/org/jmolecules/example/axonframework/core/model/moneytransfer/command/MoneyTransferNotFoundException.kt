package org.jmolecules.example.axonframework.core.model.moneytransfer.command

import org.jmolecules.example.axonframework.core.model.bankaccount.AccountId
import org.jmolecules.example.axonframework.core.model.moneytransfer.MoneyTransferId

class MoneyTransferNotFoundException(accountId: AccountId, moneyTransferId: MoneyTransferId) :
  RuntimeException("BankAccount[id=$accountId] is not part of transfer=$moneyTransferId")
