# ADR-008 Read Model

## Status

accepted

## Context

This decision is about the implementation of the read model.

## Decision

A read model is a CQRS component responsible for answering the queries. It is supplied with data using
**Domain Events** and uses a component called **Projector** to create a representation suitable for query.
The Projector uses a Repository (an **Out-Port**) to store the domain representation of the read side and load it back.

If a query arrives to the **Query handler** located inside the Projection, it uses a repository to retrieve
the domain representation of the read side and return to the caller (the calling use case inside the 
application layer.)

## Consequences

