# ADR-006 In and Out Ports

## Status

accepted

## Context

This decision is about component definition on the application core towards in adapter and out adapter.

## Decision

The **primary** or **in** ports are created along the use cases. Every use case is represented by its own **in** port.
The **secondary** or **out** port are created based along target entities and follow the CQRS pattern. Following 
the [ADR-003](./003-cqrs.md), we separate command from query operations. 

A single out port is grouping all query methods of a single read model (projection). A single out port is grouping all 
command methods of a single aggregate root used from one use case. This additional per use-case separation is helpful
to avoid too large command dispatcher.

## Consequences

