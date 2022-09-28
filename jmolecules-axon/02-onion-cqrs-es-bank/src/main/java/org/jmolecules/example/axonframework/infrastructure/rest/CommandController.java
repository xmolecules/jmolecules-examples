package org.jmolecules.example.axonframework.infrastructure.rest;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.jmolecules.architecture.cqrs.annotation.CommandDispatcher;
import org.jmolecules.example.axonframework.domain.api.command.CreateBankAccountCommand;
import org.jmolecules.example.axonframework.domain.api.command.atm.DepositMoneyCommand;
import org.jmolecules.example.axonframework.domain.api.command.atm.WithdrawMoneyCommand;
import org.jmolecules.example.axonframework.domain.api.command.transfer.RequestMoneyTransferCommand;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.noContent;

@RestController
@RequestMapping("/rest/command")
public class CommandController {

  private final CommandGateway commandGateway;

  public CommandController(CommandGateway commandGateway) {
    this.commandGateway = commandGateway;
  }

  @PostMapping("/create-bank-account")
  @CommandDispatcher(dispatches = "axon.bank.CreateBankAccountCommand")
  public ResponseEntity<String> createBankAccount(@RequestBody CreateBankAccountCommand cmd) {
    commandGateway.sendAndWait(cmd);
    return created(
      ServletUriComponentsBuilder
        .fromCurrentContextPath()
        .path("/rest/query/current-balance/{accountId}")
        .buildAndExpand(cmd.accountId())
        .toUri()
    ).build();
  }

  @PutMapping("/withdraw-money")
  @CommandDispatcher(dispatches = "axon.bank.WithdrawMoneyCommand")
  public ResponseEntity<Void> withdrawMoney(@RequestBody WithdrawMoneyCommand cmd) {
    commandGateway.sendAndWait(cmd);
    return noContent().build();
  }

  @PutMapping("/deposit-money")
  @CommandDispatcher(dispatches = "axon.bank.DepositMoneyCommand")
  public ResponseEntity<Void> depositMoney(@RequestBody DepositMoneyCommand cmd) {
    commandGateway.sendAndWait(cmd);
    return noContent().build();
  }

  @PutMapping("/request-money-transfer")
  @CommandDispatcher(dispatches = "axon.bank.RequestMoneyTransferCommand")
  public ResponseEntity<Void> transferMoney(@RequestBody RequestMoneyTransferCommand cmd) {
    commandGateway.sendAndWait(cmd);
    return noContent().build();
  }
}
