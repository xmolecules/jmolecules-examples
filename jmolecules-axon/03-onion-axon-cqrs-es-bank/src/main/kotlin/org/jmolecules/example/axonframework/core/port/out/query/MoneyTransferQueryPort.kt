package org.jmolecules.example.axonframework.core.port.out.query

import org.jmolecules.example.axonframework.core.model.bankaccount.type.AccountId
import org.jmolecules.example.axonframework.core.model.moneytransfer.read.MoneyTransferSummary
import org.jmolecules.example.axonframework.core.model.moneytransfer.type.MoneyTransferId
import java.util.*
import java.util.concurrent.CompletableFuture

interface MoneyTransferQueryPort {
  fun getMoneyTransfers(accountId: AccountId): CompletableFuture<List<MoneyTransferSummary>>
  fun getMoneyTransfer(moneyTransferId: MoneyTransferId): CompletableFuture<Optional<MoneyTransferSummary>>
}
