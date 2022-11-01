package org.jmolecules.example.axonframework.infrastructure.application.impl

import org.axonframework.commandhandling.gateway.CommandGateway
import org.axonframework.queryhandling.QueryGateway
import org.jmolecules.example.axonframework.application.BankAccountService
import org.jmolecules.example.axonframework.domain.api.command.CreateBankAccountCommand
import org.jmolecules.example.axonframework.domain.api.query.BankAccountCurrentBalanceQuery
import org.jmolecules.example.axonframework.domain.api.query.BankAccountCurrentBalance
import org.jmolecules.example.axonframework.domain.api.type.AccountId
import org.jmolecules.example.axonframework.domain.api.type.Balance
import org.springframework.stereotype.Component
import java.util.*
import java.util.concurrent.CompletableFuture

/**
 * Implementation of the bank account service using Axon Buses for command and query dispatch.
 */
@Component
class BankAccountServiceImpl(
    val commandGateway: CommandGateway,
    val queryGateway: QueryGateway
) : BankAccountService {

    override fun createBankAccount(accountId: AccountId, initialBalance: Balance) {
        commandGateway.sendAndWait<Any>(
            CreateBankAccountCommand(
                accountId = accountId,
                initialBalance = initialBalance
            )
        )
    }

    override fun getCurrentBalance(accountId: AccountId): CompletableFuture<Optional<BankAccountCurrentBalance>> {
        return queryGateway.query(BankAccountCurrentBalanceQuery(accountId), BankAccountCurrentBalanceQuery.RESPONSE_TYPE)
    }
}
