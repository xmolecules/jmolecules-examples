# ADR-002 Ubiquitous Language

## Status

accepted

## Context

How the mapping of the problem space concepts is performed into solution space including the implementation.

## Decision

Following the DDD concepts we try to adopt the **Ubiquitous Language** and express the problem concepts 1:1 in the 
solution's domain model. Especially we model all required model types as they are present in the problem space.

We use the following Kotlin constructs for this:

- Value objects based on a single value are represented by a `value class`.
- Value objects based on multiple values are represented by a `data class`.
- Constant values from a limited set are represented either by `enum class`.

The domain and application ring must use the domain types only (no other data types are allowed).

## Consequences

- The mapping between outside adapters and domain types is required (DB, REST/JSON doesn't allow use of custom data types).
