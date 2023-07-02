package Main;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

import com.google.gson.Gson;

import Model.User;
import Model.WaitThread;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class Client {
    public static Client client;
    private String recentResponse;
    private final DataOutputStream dataOutputStream;
    private final DataInputStream dataInputStream;
    public final ServerResponseListener serverResponseListener;
    private final Socket socket;
    public BlockingDeque<Request> globalChats = new LinkedBlockingDeque<Request>();
    public BlockingDeque<Request> privateChats = new LinkedBlockingDeque<Request>();
    public BlockingDeque<Request> roomChats = new LinkedBlockingDeque<Request>();
    public ArrayList<Integer> myRoomsID = new ArrayList<>();



    public Client(String host, int port) throws IOException {
        Socket socket = new Socket(host, port);
        this.socket = socket;
        dataOutputStream = new DataOutputStream(socket.getOutputStream());
        dataInputStream = new DataInputStream(socket.getInputStream());
        client = this;
        serverResponseListener = new ServerResponseListener(dataInputStream, client);
        serverResponseListener.start();
    }

    public void sendRequestToServer(Request request, boolean waitForResponse) {
        try {
            serverResponseListener.setResponseReceived(false);
            dataOutputStream.writeUTF(request.toJson());
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (waitForResponse)
            checkResponseRecievement();
    }

    public void setRecentResponse(String response) {
        this.recentResponse = response;
    }

    public String getRecentResponse() {
        return recentResponse;
    }

    public void updateUserData() {
        Request request = new Request(NormalRequest.GET_USER_BY_USERNAME);
        request.addToArguments("Username", User.getCurrentUser().getUsername());
        sendRequestToServer(request, true);
        String response = recentResponse;
        User updatedUserData = new Gson().fromJson(response, User.class);
        User.setCurrentUser(updatedUserData);
    }

    private void checkResponseRecievement() {
        System.out.println("not yet");
        if (serverResponseListener.isResponseReceived()) return;
        WaitThread waitThread = new WaitThread();
        waitThread.start();
        try {
            waitThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        checkResponseRecievement();
    }

    public void sendFile(String fileAddress) {
        int i;
        FileInputStream fis = null;
        try {
            fis = new FileInputStream("/path/to/your/image.jpg");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            while ((i = fis.read()) > -1)
                try {
                    dataOutputStream.write(i);
                } catch (IOException e) {
                    e.printStackTrace();
                }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void startReadingFile(String pathToStore) {
        FileOutputStream fout = null;
        try {
            fout = new FileOutputStream(pathToStore);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        int i;
        try {
            while ((i = this.dataInputStream.read()) > -1) {
                try {
                    fout.write(i);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        try {
            fout.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fout.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public DataOutputStream getDataOutputStream() {
        return dataOutputStream;
    }

    public DataInputStream getDataInputStream() {
        return dataInputStream;
    }

    public  Request getPublicMessageByText (String text){
        for (Request globalChat : globalChats) {
            if(globalChat.argument.get("message").equals(text))
                return globalChat;
        }
        return null;
    }

    public Request getPrivateMessageByText(String text){
        for (Request privateChat : privateChats) {
            if(privateChat.argument.get("message").equals(text))
                return privateChat;
        }
        return null;
    }

    public Request getRoomMessageByText (String text){
        for (Request roomChat : roomChats) {
            if(roomChat.argument.get("message").equals(text))
                return roomChat;
        }
        return null;
    }
}
