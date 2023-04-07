[![Java CI with Maven](https://github.com/mithunkhatri/white-bank/actions/workflows/maven.yml/badge.svg)](https://github.com/mithunkhatri/white-bank/actions/workflows/maven.yml)


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
- [x] Eureka Server : Service discovery
- [x] Gateway : Routing of the service requests from the client
- [x] Distributed tracing

### Builds and CI
- [x] Multi module maven project
- [x] Github Actions to build maven on commit
- [x] Dockerize the services

```
[INFO] Reactor Summary for White Bank Parent 0.0.1-SNAPSHOT:
[INFO] 
[INFO] White Bank Common Component ........................ SUCCESS [  6.829 s]
[INFO] White Bank Parent .................................. SUCCESS [  2.639 s]
[INFO] White Bank Command Service ......................... SUCCESS [ 55.468 s]
[INFO] White Bank Query Service ........................... SUCCESS [ 24.799 s]
[INFO] White Bank Eureka Server ........................... SUCCESS [ 12.827 s]
[INFO] White Bank Gateway ................................. SUCCESS [ 21.953 s]
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  02:06 min
[INFO] Finished at: 2023-04-06T00:31:51+05:30
[INFO] ------------------------------------------------------------------------
```

### Quick start the services
- Take a clone of the repository and cd into the folder
- [Run this startup script](/assets/scripts/startup.sh)
  Startup script performs following tasks
    - Run `mvn clean install` to build the jars
    - `docker-compose build` --> to build the images directly from local
    - `docker-compose up -d` --> starts the docker images

## Features Implemented
Command
- [x] Create bank account with initial deposit and credit line
- [x] Create credit transaction, and adjust the balance
- [x] Create debit transaction, and adjust the balance
- [x] Validation on credit line exceed on a debit transaction. Mark the debit transaction as Pending if credit line is exceed. Balance is not changed in this case.
- [x] Add transaction id to debit and credit transactions

Query
- [x] Retrieve the current account balance of a given bank account
- [x] Test if a pending debit payment would exceed the overdraft limit of that bank account
- [x] Get a list of all transactions booked of a given bank account since a given calendar date.
- [x] Retrieve a list of all bank accounts in the red, i.e., whose account balance is lower than zero

## Assumptions
- Bank account is a single currency type account. Debit, credit and initial balance are all in the same currency. Exchange rate and currency conversion is not required.
- Debit request exceeding the credit line are marked as PENDING, reprocessing of the PENDING transaction is not required
- If the credit line exceeds on a debit transaction, transaction is still accepted and kept in pending state. 
- Account owner details management is not required
- Once account is created its always active. Handling inactive or closing of account is not required.

# High Level Diagram
![High Level Diagram](/assets/images/HighLevel.png "High Level Diagram")

# Flow Diagram
TODO

# Postman Collection
[Json](/assets/postman/White%20Bank.postman_collection.json)