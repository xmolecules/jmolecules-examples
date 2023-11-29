# ADR-007 Message dispatcher

## Status

accepted

## Context

This decision is about the implementation of the infrastructure out adapters.

## Decision

Following the CQRS pattern described in [ADR-003](./003-cqrs.md) and explicit messaging described in
[ADR-005](./005-command-event-query-bus.md) the out adapters must use **Command** and **Query** messages
to connect the **Out** ports with the infrastructure components, responsible for addressing correct
components (Aggregates or Projections).

The **Out Adapter** (implementing the **Out Ports**, described in [ADR-006](./006-in-and-out-ports.md)) are 
so-called Message Dispatcher. Their responsibility is to collect the parameters, construct **Command** and 
**Query** messages and send them via Axon Framework **Command Gateway** and **Query Gateway**.

In addition, special Event Bus Adapter are provided to publish **Milestone Events** via Event Bus.

## Consequences

