package org.jmolecules.example.axonframework.core.model.moneytransfer.exception

import org.jmolecules.example.axonframework.core.model.bankaccount.type.AccountId
import org.jmolecules.example.axonframework.core.model.moneytransfer.type.MoneyTransferId

class MoneyTransferNotFoundException(accountId: AccountId, moneyTransferId: MoneyTransferId) :
  RuntimeException("BankAccount[id=$accountId] is not part of transfer=$moneyTransferId")
