package org.jmolecules.example.axonframework.bank.adapter.`in`.rest

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.jmolecules.ddd.annotation.ValueObject
import org.jmolecules.example.axonframework.bank.adapter.`in`.rest.MoneyTransferResource.MoneyTransferStatusDto.*
import org.jmolecules.example.axonframework.bank.application.port.`in`.TransferMoneyInPort
import org.jmolecules.example.axonframework.bank.domain.bankaccount.type.AccountId
import org.jmolecules.example.axonframework.bank.domain.bankaccount.type.Amount
import org.jmolecules.example.axonframework.bank.domain.moneytransfer.type.MoneyTransferId
import org.jmolecules.example.axonframework.bank.domain.moneytransfer.type.MoneyTransferSummary
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
  private val moneyTransferInPort: TransferMoneyInPort,
) {
  @PutMapping("/execute")
  @Operation(
    summary = "Transfers money from source account to target account.",
    responses = [
      ApiResponse(responseCode = "201", description = "Successfully started."),
      ApiResponse(responseCode = "404", description = "Source account not found.")
    ]
  )
  fun transferMoney(@RequestBody dto: RequestMoneyTransferDto): ResponseEntity<Void> {
    val transferId = moneyTransferInPort.transferMoney(
      AccountId.of(dto.sourceAccountId),
      AccountId.of(dto.targetAccountId),
      Amount.of(dto.amount)
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
  @Operation(
    summary = "Finds money transfer by id.",
    responses = [
      ApiResponse(responseCode = "200", description = "Successful operation."),
      ApiResponse(responseCode = "404", description = "Source account not found.")
    ]
  )
  fun findMoneyTransfer(@PathVariable("moneyTransferId") moneyTransferId: String): ResponseEntity<MoneyTransferDto> {
    val moneyTransferOption = moneyTransferInPort.getMoneyTransfer(MoneyTransferId.of(moneyTransferId)).join()
    return moneyTransferOption
      .map { ok(it.toDto()) }
      .orElse(notFound().build())
  }


  @GetMapping("/account/{accountId}")
  @Operation(
    summary = "Finds money transfer by account id.",
    responses = [
      ApiResponse(responseCode = "200", description = "Successful operation."),
      ApiResponse(responseCode = "404", description = "Source account not found.")
    ]
  )
  fun findMoneyTransfers(@PathVariable("accountId") accountId: String): ResponseEntity<List<MoneyTransferDto>> {
    val summaries = moneyTransferInPort.getMoneyTransfers(AccountId.of(accountId)).join()
    return ok(
      summaries.elements.map { it.toDto() }
    )
  }

  @ValueObject
  data class RequestMoneyTransferDto(
    val sourceAccountId: String,
    val targetAccountId: String,
    val amount: Int
  )

  @ValueObject
  data class MoneyTransferDto(
    val moneyTransferId: String,
    val sourceAccountId: String,
    val targetAccountId: String,
    val amount: Int,
    val status: MoneyTransferStatusDto,
    val errorMessage: String?
  )

  @ValueObject
  enum class MoneyTransferStatusDto {
    SUCCESS,
    FAILURE
  }

  fun MoneyTransferSummary.toDto() = MoneyTransferDto(
    moneyTransferId = this.moneyTransferId.value,
    sourceAccountId = this.sourceAccountId.value,
    targetAccountId = this.targetAccountId.value,
    amount = this.amount.value,
    status = when {
      this.success -> SUCCESS
      else -> FAILURE
    },
    errorMessage = this.errorMessage?.value
  )
}
