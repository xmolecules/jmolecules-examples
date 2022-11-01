package org.jmolecules.example.axonframework.application.atm

import org.jmolecules.example.axonframework.domain.api.command.InsufficientBalanceException
import org.jmolecules.example.axonframework.domain.api.command.MaximumBalanceExceededException
import org.jmolecules.example.axonframework.domain.api.type.AccountId
import org.jmolecules.example.axonframework.domain.api.type.Amount


/**
 * Operations on bank account available at ATM.
 */
interface AtmService {
    /**
     * Withdraws money from given account.
     * @param accountId account id.
     * @param amount amount to withdraw.
     */
    @Throws(InsufficientBalanceException::class)
    fun withdrawMoney(accountId: AccountId, amount: Amount)

    /**
     * Deposits money on the given account.
     * @param accountId account id.
     * @param amount amount to deposit.
     */
    @Throws(MaximumBalanceExceededException::class)
    fun depositMoney(accountId: AccountId, amount: Amount)
}
