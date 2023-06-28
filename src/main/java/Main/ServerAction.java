package Main;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class ServerAction extends Thread{
    private final Socket socket;
    private final DataInputStream dataInputStream;

    public ServerAction(Socket socket, DataInputStream dataInputStream) throws IOException {
        this.socket = socket;
        this.dataInputStream = dataInputStream;

        Request.setUserToken(dataInputStream.readUTF());
    }

    @Override
    public void run() {

    }

    public Socket getSocket() {
        return socket;
    }

    public DataInputStream getDataInputStream() {
        return dataInputStream;
    }
}
