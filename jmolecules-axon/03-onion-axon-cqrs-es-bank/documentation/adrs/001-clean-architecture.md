# ADR-001 Clean Architecture

## Status

accepted

## Context

The question is about the main principle and architecture style applied during the creation of the application.
We consider the application to be used and deployed as a self-contained system in a larger application context.

## Decision

Use clean architecture as a main architectural principle / blueprint. To classify it in more detail, we consider
Clean Architecture to be a principle to separates software components into ring structure, by one ring being enclosing
the other. The rings from inside to outside are: **domain ring**, **application ring**, **adapter ring**, *
*infrastructure ring**.

The main rule of Clean Architecture is the dependency direction from outside to inside (inner rings MUST NOT
reference outer rings).

In addition, the infrastructure and adapter rings are separated into **in** and **out** (sometimes also called
driving and driven or primary and secondary), which is a separation from Hexagonal / Port And Adapters architecture.

Finally, the **in Adapters** call the application layer via **in ports** which are implemented by the **Use Cases**.

## Consequences

- Due to the clear separation of responsibilities some code duplication may occur and mappers are required to adopt
  elements between rings.
