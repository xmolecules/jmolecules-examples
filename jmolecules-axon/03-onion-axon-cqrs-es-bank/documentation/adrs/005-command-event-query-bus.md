# ADR-005 Decoupling with buses

## Status

accepted

## Context

This decision is about technology of decoupling of different components to allow a later scale-out. 

## Decision

We use explicit messaging instead of method invocation to decouple command from query components.
We use **Command Bus**, **Event Bus** and **Query Bus** to de-couple message dispatch from message receipt.

Here are examples:

- Implementation of the use case (application service) invokes a method on an out command port to modify some
state of the application. The out command port is implemented by a Command Dispatcher, collecting the call parameters
into a command message and sending it via Command Gateway to Command Bus. The Command Handler (e.g. as a method inside 
the Aggregate) is registered on the Command Bus and gets invoked by the Axon Framework.

- During command handling, the Aggregate invoked methods on Domain model classes and emits an Event (delivered to
Event Bus via Axon Framework Thread-Local static construct). The Event is delivered via Axon Framework to the Event 
store (and persisted there) and is delivered to all Event handling components using the configured Event processors. 
Special event handling components are Projectors building query models which are used to retrieve data later.

- Implementation of the use case (application service) invokes a method on an out query port to retrieve some
  state of the application. The out query port is implemented by a Query Dispatcher, collecting the call parameters
  into a query message and sending it via Query Gateway to Query Bus. The Query Handler (e.g. as a method inside
  the Projection) is registered on the Query Bus and gets invoked by the Axon Framework.

## Consequences

- Components MUST NOT reference of invoke each other directly.
- Every invocation inside the component is performed without concurrency (single threaded model). 
