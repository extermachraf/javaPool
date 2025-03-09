# Java Threading Fundamentals 🧵⚙️

<p align="center">
  <img src="https://media.geeksforgeeks.org/wp-content/uploads/20220228232738/InputThread.jpg">
</p>

## Overview
This project explores the fundamentals of multithreading in Java through a series of progressive exercises. It focuses on thread creation, synchronization, computational distribution, and resource management - all essential concepts in concurrent programming.

## Exercise 00: Egg, Hen... or Human? 🥚🐔👤
**Key Learning Objectives:**
- Different approaches to create threads in Java (extending Thread vs implementing Runnable)
- Understanding thread execution order and non-determinism
- Basic thread lifecycle management
- Thread competition in a shared environment

## Exercise 01: Egg, Hen, Egg, Hen... 🔄
**Key Learning Objectives:**
- Thread synchronization techniques
- Implementation of the Producer-Consumer pattern
- Thread communication through wait/notify mechanisms
- Synchronized blocks and methods to ensure orderly execution
- Thread coordination for alternating output

## Exercise 02: Real Multithreading 🧮
**Key Learning Objectives:**
- Computational task distribution across multiple threads
- Dividing work into segments for parallel processing
- Thread coordination for combining computational results
- Performance optimization through parallel execution
- Working with thread-safe accumulation of results

## Exercise 03: Too Many Threads... 📥⏱️
**Key Learning Objectives:**
- Thread pool design and implementation
- Resource management and efficient thread utilization
- Task queuing mechanisms for asynchronous processing
- Handling network operations in a multithreaded environment
- Long-running thread lifecycle management
- Avoiding thread creation overhead through reuse