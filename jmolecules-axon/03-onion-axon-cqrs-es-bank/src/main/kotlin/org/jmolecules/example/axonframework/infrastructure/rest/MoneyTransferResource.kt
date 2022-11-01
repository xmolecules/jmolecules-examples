package org.jmolecules.example.axonframework.infrastructure.rest

import org.jmolecules.architecture.cqrs.annotation.CommandDispatcher
import org.jmolecules.example.axonframework.application.moneytransfer.MoneyTransferService
import org.jmolecules.example.axonframework.domain.api.query.*
import org.jmolecules.example.axonframework.domain.api.type.AccountId
import org.jmolecules.example.axonframework.domain.api.type.Amount
import org.jmolecules.example.axonframework.domain.api.type.MoneyTransferId
import org.jmolecules.example.axonframework.infrastructure.rest.MoneyTransferResource.MoneyTransferStatusDto.*
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.notFound
import org.springframework.http.ResponseEntity.ok
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder

/**
 * Money transfer resource.
 */
@RestController
@RequestMapping("/rest/money-transfer")
class MoneyTransferResource(
    private val moneyTransferService: MoneyTransferService,
) {
    @PutMapping("/execute")
    @CommandDispatcher(dispatches = "axon.bank.RequestMoneyTransferCommand")
    fun transferMoney(@RequestBody dto: RequestMoneyTransferDto): ResponseEntity<Void> {
        val transferId = moneyTransferService.transferMoney(
            AccountId(dto.sourceAccountId),
            AccountId(dto.targetAccountId),
            Amount(dto.amount)
        )
        return ResponseEntity.created(
            ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/rest/money-transfer/{moneyTransferId}")
                .buildAndExpand(transferId.value)
                .toUri()

        ).build()
    }

    @GetMapping("/{moneyTransferId}")
    fun findMoneyTransfer(
        @PathVariable("moneyTransferId") moneyTransferId: String
    ): ResponseEntity<MoneyTransferDto> {
        val moneyTransferOption = moneyTransferService.getMoneyTransfer(MoneyTransferId(moneyTransferId)).join()
        return moneyTransferOption
            .map { ok(it.toDto()) }
            .orElse(notFound().build())
    }


    @GetMapping("/account/{accountId}")
    fun findMoneyTransfers(
        @PathVariable("accountId") accountId: String
    ): ResponseEntity<List<MoneyTransferDto>> {
        val transfers = moneyTransferService.getMoneyTransfers(AccountId(accountId)).join()
        return ok(
            transfers.map { it.toDto() }
        )
    }


    data class RequestMoneyTransferDto(
        val sourceAccountId: String,
        val targetAccountId: String,
        val amount: Int
    )

    data class MoneyTransferDto(
        val moneyTransferId: String,
        val sourceAccountId: String,
        val targetAccountId: String,
        val amount: Int,
        val status: MoneyTransferStatusDto,
        val errorMessage: String?
    )

    enum class MoneyTransferStatusDto {
        IN_PROGRESS, SUCCESS, FAILURE
    }

    fun MoneyTransferSummary.toDto() = MoneyTransferDto(
        moneyTransferId = this.moneyTransferId.value,
        sourceAccountId = this.sourceAccountId.value,
        targetAccountId = this.targetAccountId.value,
        amount = this.amount.value,
        status = when {
            this.success == null -> IN_PROGRESS
            this.success -> SUCCESS
            else -> FAILURE
        },
        errorMessage = this.errorMessage?.value
    )


}
