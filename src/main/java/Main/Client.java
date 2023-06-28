package Main;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {
    private final ServerAction serverAction;
    private final DataOutputStream dataOutputStream;

    public Client(String host, int port) throws IOException {
        Socket socket = new Socket(host, port);
        dataOutputStream = new DataOutputStream(socket.getOutputStream());

        serverAction = new ServerAction(socket);
        serverAction.start();
    }


}
