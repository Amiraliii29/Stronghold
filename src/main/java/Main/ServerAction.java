package Main;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ServerAction extends Thread{
    private final Socket socket;
    private final DataInputStream dataInputStream;
    private final DataOutputStream dataOutputStream;

    public ServerAction(Socket socket, DataInputStream dataInputStream , DataOutputStream dataOutputStream) throws IOException {
        this.socket = socket;
        this.dataInputStream = dataInputStream;
        this.dataOutputStream = dataOutputStream;

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

    public DataOutputStream getDataOutputStream() {
        return dataOutputStream;
    }
}
