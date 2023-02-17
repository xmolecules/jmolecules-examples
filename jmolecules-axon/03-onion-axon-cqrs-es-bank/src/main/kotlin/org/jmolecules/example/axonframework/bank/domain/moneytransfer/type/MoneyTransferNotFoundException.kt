package org.jmolecules.example.axonframework.bank.domain.moneytransfer.type

import org.jmolecules.example.axonframework.bank.domain.bankaccount.type.AccountId
import org.jmolecules.example.axonframework.bank.domain.moneytransfer.type.MoneyTransferId

class MoneyTransferNotFoundException(accountId: AccountId, moneyTransferId: MoneyTransferId) :
  RuntimeException("BankAccount[id=$accountId] is not part of transfer=$moneyTransferId")
