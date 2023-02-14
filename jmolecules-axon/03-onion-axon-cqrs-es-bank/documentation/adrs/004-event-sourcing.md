# ADR-004 Event Sourcing

## Status

accepted

## Context

This decision is about the main persistence strategy of the application. 

## Decision

We rely on **Event Sourcing** as a primary persistence implementation of the application. This means 
instead of storing the current state of the application, the series of changes to application state,
expressed in **Domain Events** is stored. 

The storage for events is an Event Store. We provide a possibility to use an in-memory event store alongside
with **Axon Server**, a special middleware component acting as event store and communication bus.

## Consequences

- In order to retrieve the current state of the application, it needs to be rehydrated from event stream.
- A replay of event stream can be used in order to refill any projection at any point in time.

