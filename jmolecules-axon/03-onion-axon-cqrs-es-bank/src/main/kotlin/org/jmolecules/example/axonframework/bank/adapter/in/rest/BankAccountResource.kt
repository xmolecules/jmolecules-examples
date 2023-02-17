package org.jmolecules.example.axonframework.bank.adapter.`in`.rest

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.jmolecules.ddd.annotation.ValueObject
import org.jmolecules.example.axonframework.bank.application.port.`in`.CreateBankAccountInPort
import org.jmolecules.example.axonframework.bank.application.port.`in`.RetrieveBankAccountInformationInPort
import org.jmolecules.example.axonframework.bank.domain.bankaccount.type.AccountId
import org.jmolecules.example.axonframework.bank.domain.bankaccount.type.Balance
import org.jmolecules.example.axonframework.bank.domain.bankaccount.type.CurrentBalance
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
class BankAccountResource(
  private val createBankAccountInPort: CreateBankAccountInPort,
  private val retrieveBankAccountInformationInPort: RetrieveBankAccountInformationInPort
) {
  @PostMapping("/create")
  @Operation(
    summary = "Creates a new bank account.",
    responses = [
      ApiResponse(responseCode = "204", description = "Successfully created."),
      ApiResponse(responseCode = "400", description = "Error during account creation.")
    ]
  )
  fun createBankAccount(@RequestBody dto: CreateBankAccountDto): ResponseEntity<String> {
    createBankAccountInPort.createBankAccount(AccountId.of(dto.accountId), Balance.of(dto.initialBalance))
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
    summary = "Retrieves bank account'S balance.",
    responses = [
      ApiResponse(responseCode = "200", description = "Successful response."),
      ApiResponse(responseCode = "404", description = "Account not found.")
    ]
  )
  fun findCurrentBalance(@PathVariable("accountId") accountId: String): ResponseEntity<CurrentBalanceDto> {
    val option = retrieveBankAccountInformationInPort.getCurrentBalance(AccountId.of(accountId)).join()
    return option
      .map { ok(it.toDto()) }
      .orElse(notFound().build())
  }

  @ValueObject
  data class CreateBankAccountDto(
    val accountId: String,
    val initialBalance: Int
  )

  @ValueObject
  data class CurrentBalanceDto(
    val accountId: String,
    val currentBalance: Int
  )

  fun CurrentBalance.toDto() = CurrentBalanceDto(
    accountId = this.accountId.value,
    currentBalance = this.currentBalance.value
  )
}
