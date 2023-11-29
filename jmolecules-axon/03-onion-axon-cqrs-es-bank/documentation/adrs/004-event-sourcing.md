# ADR-004 Event Sourcing

## Status

accepted

## Context

This decision is about the main persistence strategy of the application. 

## Decision

We rely on **Event Sourcing** as a primary persistence implementation of the application. This means
that a series of changes to application state, expressed in **Domain Events** is stored instead of storing the 
current state of the application. 

The storage for events is an Event Store. We provide a possibility to use an in-memory event store alongside
with **Axon Server**, a special middleware component acting as event store.

## Consequences

- In order to retrieve the current state of the application, it needs to be rehydrated from event stream. This feature is 
implemented using the functionality of Axon Framework (Event-Sourced aggregates using Sourcing Event Handlers).
- A replay of event stream can be used in order to refill any projection at any point in time.

