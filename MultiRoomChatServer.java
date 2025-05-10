
import java.io.*;
import java.net.*;
import java.util.*;

public class MultiRoomChatServer {
    private static final int PORT = 12345;
    private static Map<String, Set<PrintWriter>> chatRooms = new HashMap<>();

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server is running...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                new ClientHandler(clientSocket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ClientHandler extends Thread {
        private Socket socket;
        private PrintWriter writer;
        private BufferedReader reader;
        private String username;
        private String currentRoom;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                writer = new PrintWriter(socket.getOutputStream(), true);

                // Authentication logic
                boolean authenticated = authenticateUser();
                if (!authenticated) {
                    return;
                }

                writer.println("Welcome, " + username + "!");
                writer.println("Available chat rooms: room1, room2, room3");

                // Handle joining a chat room
                while (true) {
                    String room = reader.readLine();
                    if (room.startsWith("room")) {
                        currentRoom = room;
                        joinChatRoom();
                        break;
                    }
                }

                // Handle messages
                String message;
                while ((message = reader.readLine()) != null) {
                    broadcastMessage(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (username != null && currentRoom != null) {
                    chatRooms.get(currentRoom).remove(writer);
                    broadcastMessage(username + " has left the chat.", currentRoom);
                }
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private boolean authenticateUser() throws IOException {
            writer.println("Enter username:");
            username = reader.readLine();
            // Add authentication logic here (e.g., check username and password)
            return true; // For simplicity, always authenticate
        }

        private void joinChatRoom() {
            chatRooms.putIfAbsent(currentRoom, new HashSet<>());
            chatRooms.get(currentRoom).add(writer);
            broadcastMessage(username + " has joined the chat.", currentRoom);
        }

        private void broadcastMessage(String message) {
            for (PrintWriter writer : chatRooms.get(currentRoom)) {
                writer.println(username + ": " + message);
            }
        }

        private void broadcastMessage(String message, String room) {
            for (PrintWriter writer : chatRooms.get(room)) {
                writer.println(message);
            }
        }
    }
}


/* 
import java.io.*;
import java.net.*;
import java.util.*;

public class MultiRoomChatServer {
    private static final int PORT = 12345;
    private static Map<String, Set<PrintWriter>> chatRooms = new HashMap<>();

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server is running...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                new ClientHandler(clientSocket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ClientHandler extends Thread {
        private Socket socket;
        private PrintWriter writer;
        private BufferedReader reader;
        private String username;
        private String currentRoom;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                writer = new PrintWriter(socket.getOutputStream(), true);

                // Authentication logic
                boolean authenticated = authenticateUser();
                if (!authenticated) {
                    return;
                }

                writer.println("Welcome, " + username + "!");
                writer.println("Available chat rooms: room1, room2, room3");

                // Handle joining a chat room
                while (true) {
                    String room = reader.readLine();
                    if (room.startsWith("room")) {
                        currentRoom = room;
                        joinChatRoom();
                        break;
                    }
                }

                // Handle messages
                String message;
                while ((message = reader.readLine())!= null) {
                    broadcastMessage(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (username!= null && currentRoom!= null) {
                    chatRooms.get(currentRoom).remove(writer);
                    broadcastMessage(username + " has left the chat.", currentRoom);
                }
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private boolean authenticateUser() throws IOException {
            writer.println("Enter username:");
            username = reader.readLine();
            // Add authentication logic here (e.g., check username and password)
            return true; // For simplicity, always authenticate
        }

        private void joinChatRoom() {
            chatRooms.putIfAbsent(currentRoom, new HashSet<>());
            chatRooms.get(currentRoom).add(writer);
            broadcastMessage(username + " has joined the chat.", currentRoom);
        }

        private void broadcastMessage(String message) {
            String colorCode = "";
            if (currentRoom.equals("room1")) {
                colorCode = "\u001B[31m"; // red color code
            } else if (currentRoom.equals("room2")) {
                colorCode = "\u001B[32m"; // green color code
            } else if (currentRoom.equals("room3")) {
                colorCode = "\u001B[34m"; // blue color code
            }
            for (PrintWriter writer : chatRooms.get(currentRoom)) {
                writer.println(colorCode + username + ": " + message + "\u001B[0m"); // reset color code to default
            }
        }

        private void broadcastMessage(String message, String room) {
            for (PrintWriter writer : chatRooms.get(room)) {
                writer.println(message);
            }
        }
    }
}
    */