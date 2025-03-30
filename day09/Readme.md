# Java Socket Chat Application - Implementation Guide

## Overview

This document provides a comprehensive implementation guide for building a Java-based client-server chat application using sockets. The project consists of three progressive exercises:

1. **Exercise 00: Registration** - Basic socket server and client with user registration functionality
2. **Exercise 01: Messaging** - Multi-user messaging capabilities
3. **Exercise 02: Rooms** - Chatroom management with message history

## Environment Requirements

- Latest LTS version of Java
- Must run on both JVM and GraalVM
- IDE recommended (IntelliJ IDEA)
- Follow Oracle code formatting standards
- Use Maven for project management

## Exercise 00: Registration

### Objective
Create the foundation of the chat system with two separate applications: a socket server and a socket client with basic user registration functionality.

### Server Requirements
- Standalone Maven project that produces a runnable JAR
- Launched using: `java -jar target/socket-server.jar --port=8081`
- Manages user registration with secure password storage
- Uses Spring components for dependency management
- Single client connection support

### Client Requirements
- Standalone Maven project producing a runnable JAR
- Launched using: `java -jar target/socket-client.jar --server-port=8081`
- Provides user registration interface

### Technical Implementation
1. **Server Architecture**:
   - Main server class handling socket connections
   - User model for data representation
   - Service layer for business logic
   - Repository layer for data access
   - Spring configuration for dependency injection

2. **Database Components**:
   - HikariCP as DataSource
   - JdbcTemplate for database operations
   - User table for storing credentials

3. **Security**:
   - PasswordEncoder and BCryptPasswordEncoder for secure password storage
   - Configuration in SocketsApplicationConfig class
   - Integration with UsersService

4. **Project Structure**:
   - Follow the specified directory structure in the PDF
   - Proper separation of models, services, repositories, and configurations

### Expected Behavior
1. Server starts and waits for connection
2. Client connects and receives welcome message
3. User selects signup option
4. User enters username and password
5. Server stores credentials securely
6. Success message displayed to user
7. Connection closes

## Exercise 01: Messaging

### Objective
Extend the application to support multiple users and real-time message exchange between them.

### Server Requirements
- Support multiple concurrent client connections
- Message broadcasting to all connected users
- Message persistence in database
- Session management for logged-in users

### Client Requirements
- User authentication (sign in)
- Real-time message display from all users
- Ability to send messages
- Logout functionality

### Technical Implementation
1. **User Authentication**:
   - Implement sign-in verification against stored credentials
   - Maintain session information for authenticated users
   - Close connection for authentication failures

2. **Message Handling**:
   - Create message model with sender, text, and timestamp
   - Implement message repository for persistence
   - Create message service for business logic

3. **Multi-client Support**:
   - Manage multiple socket connections concurrently
   - Broadcast messages to all connected clients
   - Handle client disconnections gracefully

4. **Database Extensions**:
   - Add messages table to store chat history
   - Link messages to user accounts

### Expected Behavior
1. User registers or signs in
2. After successful authentication, messaging interface appears
3. User can send messages visible to all other connected users
4. Messages from other users appear in real-time
5. User can exit the chat to close the connection

## Exercise 02: Rooms

### Objective
Implement chatroom functionality allowing users to create, join, and leave different conversation spaces, with message history persistence.

### Server Requirements
- Chatroom creation and management
- Room-specific message routing
- Message history retrieval (last 30 messages)
- JSON message format for all communications

### Client Requirements
- Room management interface (create/choose/exit)
- Room selection from available rooms list
- Room-specific messaging
- Message history display when rejoining rooms

### Technical Implementation
1. **Database Extensions**:
   - Create rooms table with room properties
   - Add room-user association table to track membership
   - Update messages table to include room context

2. **JSON Communication**:
   - Design JSON structures for all message types
   - Implement serialization/deserialization
   - Use JSON for all client-server communications

3. **Room Management**:
   - Implement room creation functionality
   - Room selection and joining mechanisms
   - Room exit handling
   - User-room association tracking

4. **Message History**:
   - Store user's last visited room
   - Retrieve and display last 30 messages when user rejoins a room
   - Message persistence with room context

### Expected Behavior
1. User signs in
2. User is presented with room management options (create/choose/exit)
3. User can select an existing room or create a new one
4. Upon room selection, last 30 messages are displayed
5. User can send messages visible only to other users in the same room
6. User can exit a room to return to room selection or exit the application

## Implementation Considerations

### General Architecture
- Follow clear separation of concerns (models, services, repositories)
- Implement proper error handling and edge cases
- Ensure thread safety for concurrent operations
- Use Spring context for dependency management

### Database Design
- Create appropriate schema for users, messages, and rooms
- Implement proper foreign key relationships
- Use indexes for performance optimization
- Consider transaction management for data integrity

### Testing Strategy
- Test with multiple client instances
- Verify message routing in different scenarios
- Test authentication failures and edge cases
- Verify message history retrieval

## Conclusion

This implementation guide covers all aspects required for building the complete chat application across the three exercises. Follow the progression from basic registration to full-featured chatroom functionality, ensuring that each component is properly implemented before moving to the next stage.

Remember to adhere to Java best practices, follow Oracle's coding standards, and implement proper error handling throughout the application. The final result should be a robust, multi-user chat system with room management capabilities and message persistence.