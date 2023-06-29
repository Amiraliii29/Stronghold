package Main;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import com.google.gson.Gson;

import Model.User;

public class Client {
    public static Client client;
    private String recentResponse;
    private final ServerAction serverAction;
    private final DataOutputStream dataOutputStream;
    private final DataInputStream dataInputStream;
    private final ServerResponseListener serverResponseListener;


    public Client(String host, int port) throws IOException {
        Socket socket = new Socket(host, port);
        dataOutputStream = new DataOutputStream(socket.getOutputStream());
        dataInputStream = new DataInputStream(socket.getInputStream());
        client = this;
        serverResponseListener=new ServerResponseListener(dataInputStream, client);
        serverAction = new ServerAction(socket, dataInputStream , dataOutputStream);
        serverAction.start();
        serverResponseListener.start();
    }

    public DataOutputStream getDataOutputStream() {
        return dataOutputStream;
    }
    public void sendRequestToServer(Request request){
        try {
            dataOutputStream.writeUTF(request.toJson());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setRecentResponse(String response){
        this.recentResponse=response;
    }

    public String getRecentResponse(){
        return recentResponse;
    }

    public String getServerResponse(){
       try {
        return dataInputStream.readUTF();
    } catch (IOException e) {
        e.printStackTrace();
    }
    return null;
    }

    public DataInputStream getDataInputStream() {
        return dataInputStream;
    }

    public void updateUserData(){
        Request request=new Request(NormalRequest.GET_USER_BY_USERNAME);
        request.addToArguments("Username", User.getCurrentUser().getUsername());
        sendRequestToServer(request);
        String response=getServerResponse();
        User updatedUserData=new Gson().fromJson(response, User.class);
        User.setCurrentUser(updatedUserData);
    }
}
