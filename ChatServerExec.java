package lab4;




public class ChatServerExec {
    private int CHAT_ROOM_PORT;

    public ChatServerExec(int port) {
        CHAT_ROOM_PORT = port;
    }

    public void startServer() {
        ChatServer server = new ChatServer(CHAT_ROOM_PORT);
        new Thread(server).start(); // Run server in a separate thread
    }
}