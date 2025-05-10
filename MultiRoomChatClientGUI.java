
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class MultiRoomChatClientGUI extends JFrame {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int PORT = 12345;

    private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;
    private JTextArea chatArea;
    private JTextField messageField;

    public MultiRoomChatClientGUI() {
        setTitle("Multi-room Chat Client");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500, 400);

        // Layout
        setLayout(new BorderLayout());

        // Chat area
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setLineWrap(true);
        chatArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(chatArea);
        add(scrollPane, BorderLayout.CENTER);

        // Message input field
        messageField = new JTextField();
        messageField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });
        add(messageField, BorderLayout.SOUTH);

        // Connect to server
        connectToServer();

        setVisible(true);
    }

    private void connectToServer() {
        try {
            socket = new Socket(SERVER_ADDRESS, PORT);
            writer = new PrintWriter(socket.getOutputStream(), true);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Authentication
            authenticateUser();

            // Thread to read messages from the server
            Thread readerThread = new Thread(() -> {
                try {
                    String message;
                    while ((message = reader.readLine()) != null) {
                        chatArea.append(message + "\n");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            readerThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void authenticateUser() throws IOException {
        String username = JOptionPane.showInputDialog("Enter username:");
        // Add authentication logic here (e.g., prompt for password)
        writer.println(username);
    }

    private void sendMessage() {
        String message = messageField.getText().trim();
        if (!message.isEmpty()) {
            writer.println(message);
            messageField.setText("");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MultiRoomChatClientGUI();
            }
        });
    }
}



