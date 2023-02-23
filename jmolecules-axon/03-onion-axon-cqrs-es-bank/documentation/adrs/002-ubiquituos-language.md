# ADR-002 Ubiquitous Language

## Status

accepted

## Context

A guiding principle how the mapping of the problem space concepts is performed into solution space including the implementation.

## Decision

Following the DDD concepts we try to adopt the **Ubiquitous Language** and express the problem concepts 1:1 in the 
solution's domain model. Especially we model all required model types as they are present in the problem space.

We use the following Kotlin constructs for this:

- Value objects based on a single value are represented by a `value class`.
- Value objects based on multiple values are represented by a `data class`, having immutable members.
- Constant values from a limited set are represented by `enum class` or by `sealed class`.
- Entities are represented by `class` and may have mutable or immutable members.
- The only exception is the type `Boolean` which might be used directly. 

The domain and application ring MUST use the domain types only (no other data types are allowed). 

## Consequences

- The mapping between outside adapters and domain types is required (DB, REST/JSON doesn't allow use of custom data types).
