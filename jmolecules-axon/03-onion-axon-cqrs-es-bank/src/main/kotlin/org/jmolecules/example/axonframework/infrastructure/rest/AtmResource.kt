package org.jmolecules.example.axonframework.infrastructure.rest

import org.jmolecules.architecture.cqrs.annotation.CommandDispatcher
import org.jmolecules.example.axonframework.application.atm.AtmService
import org.jmolecules.example.axonframework.domain.api.type.AccountId
import org.jmolecules.example.axonframework.domain.api.type.Amount
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/**
 * ATM Resource.
 */
@RestController
@RequestMapping("/rest/atm")
class AtmResource(
    private val atmService: AtmService,
) {

    @PutMapping(value = ["/withdraw-money"])
    @CommandDispatcher(dispatches = "axon.bank.WithdrawMoneyCommand")
    fun withdrawMoney(@RequestBody dto: WithdrawMoneyDto): ResponseEntity<Void> {
        atmService.withdrawMoney(AccountId(dto.accountId), Amount(dto.amount))
        return ResponseEntity.noContent().build()
    }

    @PutMapping("/deposit-money")
    @CommandDispatcher(dispatches = "axon.bank.DepositMoneyCommand")
    fun depositMoney(@RequestBody dto: DepositMoneyDto): ResponseEntity<Void> {
        atmService.depositMoney(AccountId(dto.accountId), Amount(dto.amount))
        return ResponseEntity.noContent().build()
    }


    data class DepositMoneyDto(
        val accountId: String,
        val amount: Int
    )

    data class WithdrawMoneyDto(
        val accountId: String,
        val amount: Int
    )
}
