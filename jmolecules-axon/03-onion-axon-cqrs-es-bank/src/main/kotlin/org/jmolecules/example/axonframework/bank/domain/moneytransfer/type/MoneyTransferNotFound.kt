package org.jmolecules.example.axonframework.bank.domain.moneytransfer.type

import org.jmolecules.example.axonframework.bank.domain.bankaccount.type.AccountId

class MoneyTransferNotFound(
  val accountId: AccountId,
  val moneyTransferId: MoneyTransferId
) : RuntimeException("BankAccount[id=$accountId] is not part of transfer=$moneyTransferId")
