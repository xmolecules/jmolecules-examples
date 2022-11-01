package org.jmolecules.example.axonframework.infrastructure.query.impl

import org.jmolecules.example.axonframework.domain.api.query.MoneyTransferSummary
import org.jmolecules.example.axonframework.domain.model.moneytransfer.MoneyTransferRepository
import org.jmolecules.example.axonframework.domain.api.type.AccountId
import org.jmolecules.example.axonframework.domain.api.type.MoneyTransferId
import org.springframework.stereotype.Component
import java.util.*
import java.util.concurrent.ConcurrentHashMap

@Component
class MoneyTransferRepositoryImpl : MoneyTransferRepository {

    private val store: MutableMap<MoneyTransferId, MoneyTransferSummary> = ConcurrentHashMap()

    override fun findById(id: MoneyTransferId): Optional<MoneyTransferSummary> {
        return Optional.ofNullable(store[id])
    }

    override fun findByAccountId(accountId: AccountId): List<MoneyTransferSummary> {
        return store.values.stream()
            .filter { it: MoneyTransferSummary -> it.sourceAccountId == accountId || it.targetAccountId == accountId }
            .toList()
    }

    override fun findAll(): List<MoneyTransferSummary> {
        return store.values.toList()
    }

    override fun save(entity: MoneyTransferSummary) {
        store[entity.moneyTransferId] = entity
    }
}
