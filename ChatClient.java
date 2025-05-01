package lab4;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

public class ChatClient implements Runnable, ChatClientInterface {
    private static int CHAT_ROOM_PORT;
    BufferedReader in;
    PrintWriter out;
    BorderPane frame = new BorderPane();
    TextField textField = new TextField();
    TextArea messageArea = new TextArea();
    Stage stage;
    String myScreenName = "";

    ChatClient(int port) {
        CHAT_ROOM_PORT = port;
        stage = new Stage();
        stage.setScene(new Scene(frame, 500, 200));
        stage.setX(ChatClientExec.getClientX());
        stage.setY(ChatClientExec.getClientY());
        stage.setTitle("Chat Client");
        stage.show();
        textField.setEditable(false);
        messageArea.setEditable(false);
        frame.setTop(textField);
        frame.setCenter(messageArea);
        frame.setVisible(true);

        textField.setOnAction(event -> {
            if (out != null) {
                out.println(textField.getText());
                textField.setText("");
            }
        });
    }

    private String getServerAddress() {
        return "localhost";
    }

    public String getName() {
        return JOptionPane.showInputDialog(null, "Choose a screen name:", "Screen Name Selection",
                JOptionPane.PLAIN_MESSAGE);
    }

    public void run() {
        try {
            Socket socket = new Socket(getServerAddress(), CHAT_ROOM_PORT);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            while (true) {
                String line = in.readLine();
                if (line == null) break;

                if (line.startsWith("SUBMITNAME")) {
                    myScreenName = getName();
                    out.println(myScreenName);
                } else if (line.startsWith("NAMEACCEPTED")) {
                    textField.setEditable(true);
                } else if (line.startsWith("WRONGNAME")) {
                    JOptionPane.showMessageDialog(null, "Screen Name " + myScreenName + " is already in use.");
                } else if (line.startsWith("MESSAGE")) {
                    messageArea.appendText(line.substring(8) + "\n");
                }
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getServerPort() {
        return CHAT_ROOM_PORT;
    }
}