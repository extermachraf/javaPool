# 🌟 Java Module 05 - SQL/JDBC

## 📚 Overview

This module focuses on working with PostgreSQL database management system through JDBC (Java Database Connectivity). Throughout the exercises, you'll build a chat application with various database operations while learning key concepts in database interaction and object-relational mapping.

## 🎯 Learning Objectives

By completing this module, you will:

- Understand how to connect Java applications to PostgreSQL databases
- Learn to implement the Data Access Object (DAO) pattern
- Gain experience with CRUD operations (Create, Read, Update, Delete)
- Learn how to handle one-to-many and many-to-many relationships
- Implement pagination for efficient data retrieval
- Work with connection pooling using HikariCP
- Understand how to map relational data to object-oriented models

## 📋 Project Structure

```
Chat/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── fr/
│   │   │       └── 42/
│   │   │           └── chat/
│   │   │               ├── models/
│   │   │               ├── repositories/
│   │   │               └── app/
│   │   │                   └── Program.java
│   │   └── resources/
│   │       ├── schema.sql
│   │       └── data.sql
└── pom.xml
```

## 🔍 Exercise Summary

### Exercise 00: Tables and Entities 🏗️

**What you'll do:** Create database tables and Java entity classes for the chat application.

**Key concepts:**
- Database schema design
- Entity relationship modeling
- One-to-many and many-to-many relationships
- Object-oriented representation of database entities
- Proper implementation of Java object methods (equals, hashCode, toString)

### Exercise 01: Read/Find 📖

**What you'll do:** Implement the Repository pattern with a findById method.

**Key concepts:**
- Data Access Object (DAO) pattern
- Repository design pattern
- HikariCP connection pooling
- Mapping database results to Java objects
- Handling optional returns for potentially missing data

### Exercise 02: Create/Save ✅

**What you'll do:** Implement a save method for messages.

**Key concepts:**
- Persisting data to a database
- Handling entity relationships during persistence
- ID generation and assignment
- Custom exception handling for data integrity
- Validating entity relationships

### Exercise 03: Update 🔄

**What you'll do:** Implement an update method for messages.

**Key concepts:**
- Updating existing database records
- Handling null values in updates
- Maintaining data integrity during updates
- Optimistic locking (optional)

### Exercise 04: Find All with Pagination 📑

**What you'll do:** Implement a findAll method with pagination support.

**Key concepts:**
- Database pagination techniques
- Efficient data retrieval with LIMIT and OFFSET
- Complex queries with multiple joins
- Common Table Expressions (CTEs) in PostgreSQL
- Handling object graphs with multiple relationships
- Converting relational data to object hierarchies in a single query

## 🚀 Technologies Used

- Java (Latest LTS version)
- PostgreSQL
- JDBC
- HikariCP
- JVM and GraalVM

## 💡 Tips for Success

- Understand the entity relationships before implementing the database schema
- Properly handle resource closing to prevent database connection leaks
- Use prepared statements to prevent SQL injection
- Implement proper error handling
- Follow the Oracle coding standards for Java
- Test your code thoroughly with various scenarios

## 🔗 Additional Resources

- [PostgreSQL Documentation](https://www.postgresql.org/docs/)
- [JDBC API Documentation](https://docs.oracle.com/javase/8/docs/technotes/guides/jdbc/)
- [HikariCP GitHub](https://github.com/brettwooldridge/HikariCP)
- [Common Table Expressions in PostgreSQL](https://www.postgresql.org/docs/current/queries-with.html)

Happy coding! 🎉