package org.jmolecules.example.axonframework.domain.model.bank

import org.jmolecules.example.axonframework.domain.api.query.BankAccountCurrentBalance
import org.jmolecules.example.axonframework.domain.api.type.AccountId
import java.util.*

/**
 * Repository for bank account current balances.
 */
interface BankAccountCurrentBalanceRepository {

    fun findById(id: AccountId): Optional<BankAccountCurrentBalance>

    fun save(entity: BankAccountCurrentBalance)
}
