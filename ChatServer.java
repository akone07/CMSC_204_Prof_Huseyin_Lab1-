package lab4;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;

public class ChatServer implements Runnable {
    private static int CHAT_ROOM_PORT;
    private static HashSet<String> names = new HashSet<>();
    private static HashSet<PrintWriter> writers = new HashSet<>();

    ChatServer(int port) {
        CHAT_ROOM_PORT = port;
    }

    public void run() {
        try (ServerSocket listener = new ServerSocket(CHAT_ROOM_PORT)) {
            System.out.println("Chat server is running...");
            while (true) {
                Socket clientSocket = listener.accept();
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                String name = null;
                while (name == null) {
                    out.println("SUBMITNAME");
                    name = in.readLine();
                    if (name == null || name.trim().isEmpty() || names.contains(name)) {
                        out.println("WRONGNAME");
                        name = null;
                    } else {
                        names.add(name);
                    }
                }

                out.println("NAMEACCEPTED");
                writers.add(out);

                new Thread(new ServerThreadForClient(in, out, name)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ServerThreadForClient implements Runnable {
        private BufferedReader in;
        private PrintWriter out;
        private String name;

        ServerThreadForClient(BufferedReader in, PrintWriter out, String name) {
            this.in = in;
            this.out = out;
            this.name = name;
        }

        public void run() {
            try {
                while (true) {
                    String input = in.readLine();
                    if (input == null) return;
                    for (PrintWriter writer : writers) {
                        writer.println("MESSAGE " + name + ": " + input);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                names.remove(name);
                writers.remove(out);
                try {
                    in.close();
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}