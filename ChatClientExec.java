package lab4;

/**
 * The client follows the Chat Protocol which is as follows.
 * When the server sends "SUBMITNAME" the client replies with the
 * desired screen name.  The server will keep sending "SUBMITNAME"
 * requests as long as the client submits screen names that are
 * already in use.  When the server sends a line beginning
 * with "NAMEACCEPTED" the client is now allowed to start
 * sending the server arbitrary strings to be broadcast to all
 * chatters connected to the server.  When the server sends a
 * line beginning with "MESSAGE " then all characters following
 * this string should be displayed in its message area.
 */



public class ChatClientExec implements ChatClientExecInterface {
    private int CHAT_ROOM_PORT;
    private static double clientX = 30.0;
    private static double clientY = 10.0;

    public ChatClientExec(int port) {
        CHAT_ROOM_PORT = port;
    }

    public void startClient() throws Exception {
        setClientY(getClientY() + 50.0);
        setClientX(getClientX() + 50.0);
        ChatClient client = new ChatClient(CHAT_ROOM_PORT);
        new Thread(client).start(); // Run client in a separate thread
    }

    public static double getClientX() {
        return clientX;
    }

    public static void setClientX(double clientX) {
        ChatClientExec.clientX = clientX;
    }

    public static double getClientY() {
        return clientY;
    }

    public static void setClientY(double clientY) {
        ChatClientExec.clientY = clientY;
    }
}