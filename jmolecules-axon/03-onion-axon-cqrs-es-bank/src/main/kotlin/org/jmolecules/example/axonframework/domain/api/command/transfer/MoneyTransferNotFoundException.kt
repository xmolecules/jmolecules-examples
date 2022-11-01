package org.jmolecules.example.axonframework.domain.api.command.transfer

import org.jmolecules.example.axonframework.domain.api.type.AccountId
import org.jmolecules.example.axonframework.domain.api.type.MoneyTransferId

class MoneyTransferNotFoundException(accountId: AccountId, moneyTransferId: MoneyTransferId) :
    RuntimeException("BankAccount[id=$accountId] is not part of transfer=$moneyTransferId")
