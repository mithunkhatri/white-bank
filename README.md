# White Bank
White Bank - Implementation of CQRS using Axon Framework, Axon Server and Spring Boot

# Quick details
## Tech Stack
- Java 11
- Spring Boot 2.7.10
- Axon 4.6.0
- Axon Server (Dockerized)
- MongoDB (Hosted on cloud.mongodb.com) - For query Service

### Modules / Services
- [x] white-bank-cmd : Command service for banking
- [x] white-bank-query : Query service for retrieving account and transaction details
- [x] white-bank-common : Holds common data objects
- [x] Axon Server : Running in a docker container
- [ ] Eureka Server : Service discovery
- [ ] Gateway : Routing of the service requests from the client
  - [ ] Circuit breaker
- [ ] Distributed tracing

### CI
- [x] Multi module maven project
- [x] Github Actions to build maven on commit

## Features Implemented
Command
- [x] Create bank account with initial deposit and credit line
- [x] Create credit transaction, and adjust the balance
- [x] Create debit transaction, and adjust the balance
- [x] Validation on credit line exceed on a debit transaction. Mark the debit transaction as Pending if credit line is exceed. Balance is not changed in this case.
- [ ] Add transaction id to debit and credit transactions

Query
- [x] Retrieve the current account balance of a given bank account
- [x] Test if a pending debit payment would exceed the overdraft limit of that bank account
- [x] Get a list of all transactions booked of a given bank account since a given calendar date.
- [x] Retrieve a list of all bank accounts in the red, i.e., whose account balance is lower than zero

## Assumptions
- Bank account is a single currency type account. Debit, credit and initial balance are all in the same currency. Exchange rate and currency conversion is not required.
- Debit request exceeding the credit line are marked as PENDING, reprocessing of the PENDING transaction is not required
- Account owner details are not maintained

# High Level Diagram
![High Level Diagram](/assets/images/HighLevel.png "High Level Diagram")

# Flow Diagram
TODO

# Postman Collection
TODO