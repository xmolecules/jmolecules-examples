package org.jmolecules.example.axonframework.infrastructure.adapter.rest

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.jmolecules.architecture.cqrs.annotation.CommandDispatcher
import org.jmolecules.ddd.annotation.ValueObject
import org.jmolecules.example.axonframework.core.model.bankaccount.AccountId
import org.jmolecules.example.axonframework.core.model.bankaccount.Amount
import org.jmolecules.example.axonframework.core.usecase.atm.AtmService
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
  @Operation(
    summary = "Withdraws money from account.",
    responses = [
      ApiResponse(responseCode = "204", description = "Successful response."),
      ApiResponse(responseCode = "400", description = "Insufficient balance."),
      ApiResponse(responseCode = "404", description = "Account not found.")
    ]
  )
  fun withdrawMoney(@RequestBody dto: WithdrawMoneyDto): ResponseEntity<Void> {
    atmService.withdrawMoney(AccountId(dto.accountId), Amount(dto.amount))
    return ResponseEntity.noContent().build()
  }

  @PutMapping("/deposit-money")
  @Operation(
    summary = "Deposits money to account.",
    responses = [
      ApiResponse(responseCode = "204", description = "Successful response."),
      ApiResponse(responseCode = "400", description = "Maximum balance exceeded."),
      ApiResponse(responseCode = "404", description = "Account not found.")
    ]
  )
  fun depositMoney(@RequestBody dto: DepositMoneyDto): ResponseEntity<Void> {
    atmService.depositMoney(AccountId(dto.accountId), Amount(dto.amount))
    return ResponseEntity.noContent().build()
  }

  @ValueObject
  data class DepositMoneyDto(
    val accountId: String,
    val amount: Int
  )

  @ValueObject
  data class WithdrawMoneyDto(
    val accountId: String,
    val amount: Int
  )
}
