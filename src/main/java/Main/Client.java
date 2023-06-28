package Main;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {
    public static Client client;
    public final ServerAction serverAction;
    public final DataOutputStream dataOutputStream;
    public final DataInputStream dataInputStream;

    public Client(String host, int port) throws IOException {
        Socket socket = new Socket(host, port);
        dataOutputStream = new DataOutputStream(socket.getOutputStream());
        dataInputStream = new DataInputStream(socket.getInputStream());
        client=this;

        serverAction = new ServerAction(socket, dataInputStream);
        serverAction.start();
    }

    public void sendRequestToServer(Request request){
        try {
            dataOutputStream.writeUTF(request.toJson());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getServerResponse(){
       try {
        return dataInputStream.readUTF();
    } catch (IOException e) {
        e.printStackTrace();
    }
    return null;
    }

}
