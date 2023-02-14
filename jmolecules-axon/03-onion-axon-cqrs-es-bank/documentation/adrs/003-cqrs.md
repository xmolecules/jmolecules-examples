# ADR-003 Command Query Responsibility Segregation

## Status

accepted

## Context

This decision is affecting the coupling between state modification and state retrieval.

## Decision

The entire application is following the CQRS pattern. This means that operations for data 
mutations (commands) never return result. Operations for information retrieval (queries)
have no side effects (don't mutate data). The command and the query side of the application 
are not **strong consistent** (ACID) but **eventually consistent** (BASE). For the communication
of the changes between the command and query side, **Domain Events** are used.

## Consequences

- The effort of modification and direct consumption of modified data becomes more complex due to eventually consistency 
between the command and the query side.  


