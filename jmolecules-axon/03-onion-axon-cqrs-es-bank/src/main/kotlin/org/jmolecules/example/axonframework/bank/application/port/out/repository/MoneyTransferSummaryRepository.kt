package org.jmolecules.example.axonframework.bank.application.port.out.repository

import org.jmolecules.ddd.annotation.Repository
import org.jmolecules.example.axonframework.bank.domain.bankaccount.type.AccountId
import org.jmolecules.example.axonframework.bank.domain.moneytransfer.read.BankAccountMoneyTransfer
import org.jmolecules.example.axonframework.bank.domain.moneytransfer.type.MoneyTransferId
import java.util.*

/**
 * Repository for money transfers.
 */
@Repository
interface MoneyTransferSummaryRepository {
  /**
   * Finds transfer by id.
   * @param id id of the transfer
   * @return transfer option
   */
  fun findById(id: MoneyTransferId): Optional<BankAccountMoneyTransfer>

  /**
   * Finds all money transfers for given account.
   * @param accountId id of the account.
   * @return list of all transfers.
   */
  fun findByAccountId(accountId: AccountId): List<BankAccountMoneyTransfer>

  /**
   * Finds all money transfers.
   * @return list of all transfers.
   */
  fun findAll(): List<BankAccountMoneyTransfer>

  /**
   * Stores a money transfer.
   * @param entity transfer to store.
   */
  fun save(entity: BankAccountMoneyTransfer)
}
