
## Storyboard

* the Domain Model Core - The banking application
  * UBL
  * no annotaions, no axon, no hibernate
* the use cases are explicit
* Aggregate
  * not a single axon import
  * generic annotations describing the role of methods and classes
  * uses domain model for operations and state
* Architecture is tested


## Bookmarks:

0. this
1. DomainModel - BankAccount - deposit
2. Application - port/use-case 
3. molecules/archunit
4. BankAccountAggregate
5. use case getCurrentBalance
6. .
7. .
8. .
9. Allards Bike Aggregate

## Links

* <http://127.0.0.1:8081/rest/bank-account/1/current-balance>
* <http://127.0.0.1:8081/rest/bank-account/2/current-balance>
