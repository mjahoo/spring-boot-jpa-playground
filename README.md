# Spring Boot 3 with JPA playground

A simple project to quickly try out JPA (hibernate) using in memory H2 database.

## Running

    ./gradlew bootRun

## Database tables

Database tables are created automatically from entity classes.

## Viewing the database

Project has H2 console enabled, go to http://localhost:8080/h2-console to view database.

Use these settings:
- jdbc url: jdbc:h2:mem:testdb
- username:sa 
- password:

Password is not used, use empty value.
