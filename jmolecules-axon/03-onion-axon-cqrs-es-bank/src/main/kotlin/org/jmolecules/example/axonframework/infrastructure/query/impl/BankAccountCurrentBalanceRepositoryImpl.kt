package org.jmolecules.example.axonframework.infrastructure.query.impl

import org.jmolecules.example.axonframework.domain.api.query.BankAccountCurrentBalance
import org.jmolecules.example.axonframework.domain.model.bank.BankAccountCurrentBalanceRepository
import org.jmolecules.example.axonframework.domain.api.type.AccountId
import org.springframework.stereotype.Component
import java.util.*
import java.util.concurrent.ConcurrentHashMap

@Component
class BankAccountCurrentBalanceRepositoryImpl : BankAccountCurrentBalanceRepository {

    private val store: MutableMap<AccountId, BankAccountCurrentBalance> = ConcurrentHashMap()


    override fun findById(id: AccountId): Optional<BankAccountCurrentBalance> {
        return Optional.ofNullable(store[id])
    }

    override fun save(entity: BankAccountCurrentBalance) {
        store[entity.accountId] = entity
    }
}
