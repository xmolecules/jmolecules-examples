package org.jmolecules.example.axonframework.domain.query;

import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.jmolecules.example.axonframework.domain.event.*;
import org.jmolecules.example.axonframework.domain.event.atm.*;
import org.jmolecules.example.axonframework.domain.event.transfer.*;
import org.jmolecules.example.axonframework.domain.api.query.CurrentBalanceQuery;
import org.jmolecules.example.axonframework.domain.api.query.CurrentBalanceResponse;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class CurrentBalanceProjection {

  private final Map<String, CurrentBalanceResponse> store = new ConcurrentHashMap<>();

  @QueryHandler
  public Optional<CurrentBalanceResponse> query(CurrentBalanceQuery query) {
    return Optional.ofNullable(store.get(query.accountId()));
  }

  @EventHandler
  public void on(BankAccountCreatedEvent evt) {
    store.put(evt.accountId(), new CurrentBalanceResponse(evt.accountId(), evt.initialBalance()));
  }

  @EventHandler
  public void on(MoneyWithdrawnEvent evt) {
    modifyCurrentBalance(evt.accountId(), -evt.amount());
  }

  @EventHandler
  public void on(MoneyDepositedEvent evt) {
    modifyCurrentBalance(evt.accountId(), +evt.amount());
  }

  @EventHandler
  public void on(MoneyTransferredEvent evt) {
    modifyCurrentBalance(evt.sourceAccountId(), -evt.amount());
    modifyCurrentBalance(evt.targetAccountId(), +evt.amount());
  }

  private void modifyCurrentBalance(String accountId, int amount) {
    store.computeIfPresent(accountId, (key, value) -> new CurrentBalanceResponse(key, value.currentBalance() + amount));
  }
}
