package org.jmolecules.example.axonframework.application

import org.jmolecules.example.axonframework.domain.api.command.InsufficientBalanceException
import org.jmolecules.example.axonframework.domain.api.command.MaximumBalanceExceededException
import org.jmolecules.example.axonframework.domain.api.query.BankAccountCurrentBalance
import org.jmolecules.example.axonframework.domain.api.type.AccountId
import org.jmolecules.example.axonframework.domain.api.type.Balance
import java.util.*
import java.util.concurrent.CompletableFuture

/**
 * Application Service to manipulate the bank account.
 */
interface BankAccountService {
    /**
     * Creates a bank account.
     * @param accountId account id.
     * @param initialBalance balance of the account.
     */
    @Throws(MaximumBalanceExceededException::class, InsufficientBalanceException::class)
    fun createBankAccount(accountId: AccountId, initialBalance: Balance)

    /**
     * Retrieves the current balance of the bank account.
     * @param accountId account id.
     * @return current balance.
     */
    fun getCurrentBalance(accountId: AccountId): CompletableFuture<Optional<BankAccountCurrentBalance>>
}
