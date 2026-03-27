# Spring Boot Microservices Project
A complete Java Spring Boot Microservices Architecture project demonstrating how to build scalable distributed systems using Spring Cloud components, Service Discovery, API Gateway, Security, Fault Tolerance, and HashiCorp Vault for secrets management.

This project simulates a Hotel Rating Platform, where users can view hotels and ratings through multiple microservices communicating with each other.

## Project Overview

The system is built using three core microservices:

### 1️⃣ User Service

Handles user-related operations and aggregates data from other services.

### 2️⃣ Hotel Service

Manages hotel information.

### 3️⃣ Rating Service

Stores and provides ratings given by users to hotels.

The User Service communicates internally with the Hotel Service and Rating Service to build a complete response.

## Features
### Core Microservices Concepts
- Microservice Architecture using Spring Boot
- Service Registry (Eureka Server)
- Service Discovery
- API Gateway
- Centralized Configuration Server
- Externalized secure configuration using Vault
### Communication Between Services
- OpenFeign Client
- RestTemplate
- Load-balanced service calls via Eureka
### Fault Tolerance (Resilience4J)
- Circuit Breaker
- Retry Mechanism
- Rate Limiter
### Security
- Spring Security
- OKTA OAuth2 Authentication
- Gateway-level security
- Service-level security
- Token-based authentication
### Secrets Management
Integrated HashiCorp Vault for secure handling of sensitive data.
What is secured via Vault:
- Database credentials
- API keys
- Service secrets
- Tokens
#### Benefits:
- No hardcoded secrets in code
- Centralized secret management
- Dynamic configuration updates
- Secure production-ready setup

## Tech Stack
### Technology	Usage
- Java	Programming Language
- Spring Boot	Microservice framework
- Spring Cloud	Distributed system tools
- Eureka Server	Service Registry
- Spring Cloud Gateway	API Gateway
- Spring Cloud Config	Centralized Configuration
- HashiCorp Vault	Secrets Management
- OpenFeign	Service communication
- Resilience4J	Fault tolerance
- Spring Security	Security
- OKTA	Authentication Provider
- Maven	Dependency management

## Microservices
### 1️⃣ User Service

Responsible for:

- Managing users
- Fetching hotel and rating data
- Aggregating responses

#### Communicates with:

- Hotel Service
- Rating Service

### 2️⃣ Hotel Service

Responsible for:

- Managing hotel data
- Providing hotel details

### 3️⃣ Rating Service

Responsible for:

- Managing ratings
- Storing user ratings

## Infrastructure Services
### 🔹 Service Registry

Uses Eureka Server for:

- Service registration
- Service discovery

### 🔹 API Gateway

Handles:

- Routing requests
- Security
- Rate limiting

### 🔹 Config Server

Provides:

- Centralized configuration
- Externalized properties

### 🔹 HashiCorp Vault

Key Features:

- Uses KV Secret Engine

- Secrets stored under:

- secret/{application-name}

## Security Implementation

Security is implemented using:

- Spring Security
- OKTA OAuth2 Authentication
- Gateway-level security
- Service-level security

### Features:
- Token-based authentication
- Secured APIs
- Secure microservice communication

## 📡 Example API Flow
<pre>
Client Request
      │
      ▼
API Gateway
      │
      ▼
User Service
      │
      ├──► Rating Service
      │
      └──► Hotel Service
</pre>

## 📂 Project Structure
<pre>
microservices-project
│
├── api-gateway
├── config-server
├── service-registry
│
├── user-service
├── hotel-service
└── rating-service
</pre>

## 🎯 Learning Outcomes

From this project you will learn:

- Microservice architecture design
- Service discovery using Eureka
- API Gateway implementation
- Inter-service communication (Feign)
- Fault tolerance using Resilience4J
- Centralized configuration
- Secure secret management using HashiCorp Vault
- Securing microservices using OKTA & Spring Security

## 👨‍💻 Author

Shaswata Das

Java Developer | Microservices | Cloud | Spring Boot
