 MultiRoom Chat Application

A multi-room chat application where users can join different chatrooms, send and receive messages in real-time. This application is built using Java and demonstrates basic client-server communication, handling multiple chatrooms, user authentication, and message broadcasting to active participants in the chatroom.

---

## Features

* **Multiple Chat Rooms**: Users can choose from multiple available chatrooms (e.g., `room1`, `room2`, `room3`).
* **Real-Time Messaging**: Messages are broadcasted to all users in the active chatroom in real-time.
* **User Authentication**: Users are prompted to enter a username to authenticate before joining a chatroom.
* **Threaded Client Handling**: Each client connection is handled by a separate thread on the server to allow multiple users to interact simultaneously.

---

## Prerequisites

* Java 8 or higher

---

## How to Run

### 1. Start the Server

Run the **MultiRoomChatServer** class to start the chat server:

```bash
javac MultiRoomChatServer.java
java MultiRoomChatServer
```

The server will start listening on port `12345`.

### 2. Start the Client

Run the **MultiRoomChatClientGUI** class to start the chat client:

```bash
javac MultiRoomChatClientGUI.java
java MultiRoomChatClientGUI
```

* The client will prompt for a **username**.
* After authenticating, users will be shown available chatrooms and can select one to join.
* u can create multiple instances of this file

---

## Code Explanation

### Server Side (MultiRoomChatServer)

* The server listens for incoming client connections on port `12345`.
* For each client, the server spawns a new thread (`ClientHandler`) to handle communication.
* The server maintains a map of chatrooms, where each chatroom has a set of active clients.
* When a user sends a message, it is broadcasted to all users in the same chatroom.

### Client Side (MultiRoomChatClientGUI)

* The client is a GUI-based application that uses Java Swing for the user interface.
* The client connects to the server on `localhost` at port `12345`.
* Users can send and receive messages in real-time using a text area and input field.

---

## Example Usage

1. Run the **MultiRoomChatServer** to start the server.
2. Run **MultiRoomChatClientGUI** to open a client window.
3. Enter a username when prompted and select a chatroom (e.g., `room1`).
4. Start sending messages, and see them broadcasted to others in the same room.

---

## Enhancements

* **Password-based authentication**: Implement login credentials (username/password) for more secure access.
* **User list per chatroom**: Display the list of active users in each chatroom.
* **Private messaging**: Allow users to send direct messages to specific individuals within the chatroom.

---
