package Main;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {
    final DataInputStream dataInputStream;
    final DataOutputStream dataOutputStream;

    public Client(String host, int port) throws IOException {
        Socket socket = new Socket(host, port);
        dataInputStream = new DataInputStream(socket.getInputStream());
        dataOutputStream = new DataOutputStream(socket.getOutputStream());

        Request.setUserToken(dataInputStream.readUTF());
    }
}
