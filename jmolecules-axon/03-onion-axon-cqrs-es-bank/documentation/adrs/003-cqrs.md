# ADR-003 Command Query Responsibility Segregation

## Status

accepted

## Context

This decision is affecting the coupling between state mutation and state retrieval.

## Decision

The entire application is following the CQRS pattern. This means that operations for data 
mutations (commands) never return result. Operations for information retrieval (queries)
have no side effects (don't mutate data). The command and the query side of the application 
are not **strong consistent** (ACID) but **eventually consistent** (BASE). For the communication
of the changes between the command and query side we use **Domain Events**.

**Command**, **Domain Event** and **Query** classes are Value Objects and are implemented using Kotlin `data class`
or `object` language element.

## Consequences

- The effort of modification and direct consumption of modified data becomes more complex due to eventually consistency 
between the command and the query side. We accept this complexity in the outer rings as a cost for a cleaner design and 
reduced complexity in the Domain Ring. 


