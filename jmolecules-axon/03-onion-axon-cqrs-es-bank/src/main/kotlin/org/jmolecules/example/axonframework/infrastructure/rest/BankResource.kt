package org.jmolecules.example.axonframework.infrastructure.rest

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.jmolecules.architecture.cqrs.annotation.CommandDispatcher
import org.jmolecules.example.axonframework.application.BankAccountService
import org.jmolecules.example.axonframework.domain.api.query.BankAccountCurrentBalance
import org.jmolecules.example.axonframework.domain.api.type.AccountId
import org.jmolecules.example.axonframework.domain.api.type.Balance
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.notFound
import org.springframework.http.ResponseEntity.ok
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder

/**
 * Bank account resource.
 */
@RestController
@RequestMapping("/rest/bank-account")
class BankResource(
    private val bankAccountService: BankAccountService,
) {
    @PostMapping("/create")
    @Operation(
        method = "Creates a new bank account.",
        responses = [
            ApiResponse(responseCode = "204", description = "Successfully created."),
            ApiResponse(responseCode = "400", description = "Error during account creation.")
        ]
    )
    @CommandDispatcher(dispatches = "axon.bank.CreateBankAccountCommand")
    fun createBankAccount(@RequestBody dto: CreateBankAccountDto): ResponseEntity<String> {
        bankAccountService.createBankAccount(AccountId(dto.accountId), Balance(dto.initialBalance))
        return ResponseEntity.created(
            ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/rest/bank-account/{accountId}/current-balance")
                .buildAndExpand(dto.accountId)
                .toUri()
        ).build()
    }

    @GetMapping("/{accountId}/current-balance")
    @Operation(
        method = "Retrieves bank account.",
        responses = [
            ApiResponse(responseCode = "200", description = "Successful response."),
            ApiResponse(responseCode = "404", description = "Account not found.")
        ]
    )
    fun findCurrentBalance(
        @PathVariable("accountId") accountId: String
    ): ResponseEntity<BalanceDto> {
        val option = bankAccountService.getCurrentBalance(AccountId(accountId)).join()
        return option
            .map { ok(it.toDto()) }
            .orElse(notFound().build())
    }

    data class CreateBankAccountDto(
        val accountId: String,
        val initialBalance: Int
    )

    data class BalanceDto(
        val accountId: String,
        val currentBalance: Int
    )

    fun BankAccountCurrentBalance.toDto() = BalanceDto(
        accountId = this.accountId.value,
        currentBalance = this.currentBalance.value
    )
}
