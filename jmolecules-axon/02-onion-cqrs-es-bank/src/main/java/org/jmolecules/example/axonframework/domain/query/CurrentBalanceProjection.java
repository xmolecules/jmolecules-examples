package org.jmolecules.example.axonframework.domain.query;

import org.axonframework.queryhandling.QueryHandler;
import org.jmolecules.architecture.cqrs.annotation.QueryModel;
import org.jmolecules.event.annotation.DomainEventHandler;
import org.jmolecules.example.axonframework.domain.api.query.CurrentBalanceQuery;
import org.jmolecules.example.axonframework.domain.api.query.CurrentBalanceResponse;
import org.jmolecules.example.axonframework.domain.event.BankAccountCreatedEvent;
import org.jmolecules.example.axonframework.domain.event.atm.MoneyDepositedEvent;
import org.jmolecules.example.axonframework.domain.event.atm.MoneyWithdrawnEvent;
import org.jmolecules.example.axonframework.domain.event.transfer.MoneyTransferredEvent;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@QueryModel
public class CurrentBalanceProjection {

  private final Map<String, CurrentBalanceResponse> store = new ConcurrentHashMap<>();

  @QueryHandler
  public Optional<CurrentBalanceResponse> query(CurrentBalanceQuery query) {
    return Optional.ofNullable(store.get(query.accountId()));
  }

  @DomainEventHandler(namespace = "axon.bank", name = "BankAccountCreatedEvent")
  public void on(BankAccountCreatedEvent evt) {
    store.put(evt.accountId(), new CurrentBalanceResponse(evt.accountId(), evt.initialBalance()));
  }

  @DomainEventHandler(namespace = "axon.bank", name = "MoneyWithdrawnEvent")
  public void on(MoneyWithdrawnEvent evt) {
    modifyCurrentBalance(evt.accountId(), -evt.amount());
  }

  @DomainEventHandler(namespace = "axon.bank", name = "MoneyDepositedEvent")
  public void on(MoneyDepositedEvent evt) {
    modifyCurrentBalance(evt.accountId(), +evt.amount());
  }

  @DomainEventHandler(namespace = "axon.bank", name = "MoneyTransferredEvent")
  public void on(MoneyTransferredEvent evt) {
    modifyCurrentBalance(evt.sourceAccountId(), -evt.amount());
    modifyCurrentBalance(evt.targetAccountId(), +evt.amount());
  }

  private void modifyCurrentBalance(String accountId, int amount) {
    store.computeIfPresent(accountId, (key, value) -> new CurrentBalanceResponse(key, value.currentBalance() + amount));
  }
}
