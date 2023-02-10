package org.jmolecules.example.axonframework.core.port.out.query

import org.jmolecules.example.axonframework.core.model.bankaccount.read.BankAccountCurrentBalance
import org.jmolecules.example.axonframework.core.model.bankaccount.type.AccountId
import java.util.*
import java.util.concurrent.CompletableFuture

interface BankAccountQueryPort {
  fun getCurrentBalance(accountId: AccountId): CompletableFuture<Optional<BankAccountCurrentBalance>>
}
