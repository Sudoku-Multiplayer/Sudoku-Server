# Sudoku-Server

A robust server built with Spring Boot, featuring real-time multiplayer gameplay.

## Technologies Used

- **Spring Boot**: For creating a robust backend server.
- **Spring WebSockets**: STOMP over WebSockets for real-time communication.

## Features:

### Game Board Options
- Supports multiple board sizes:
  - 4x4
  - 6x6
  - 9x9

### Difficulty Levels
- Various levels of difficulty to challenge players:
  - Easy
  - Medium
  - Hard
  - Very Hard
  - Insane

### Game Session Management
- **Separate Threads**: Each game session runs in its own thread to ensure smooth and efficient gameplay.
- **Session Controls**: Start, pause, and resume game sessions with ease.
- **Game Timer**: Game sessions will finish automatically when the time runs out.

### User Management
- **Registration and Authentication**: Secure user registration and authentication processes.
- **Guest Player Option**: Users can play as guests without registration.

### Voting System
- **Submit Game Session**: Players can vote to submit a game session.
- **Display Answer**: Vote to reveal the solution of the current game.

### Real-Time Features
- **In-Game Chat**: Communicate with other players in real-time within a game session.
- **Notifications**: Receive notifications when players join or leave a game session.

### WebSocket Management
- **Session Tracking**: Keeps track of all WebSocket sessions to manage real-time interactions effectively.
