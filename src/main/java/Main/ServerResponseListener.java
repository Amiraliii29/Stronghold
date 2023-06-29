package Main;

import java.io.DataInputStream;
import java.io.IOException;

public class ServerResponseListener extends Thread {
    
    private DataInputStream dataInputStream;
    private boolean isResponseReceived;
    Client client;

    public ServerResponseListener(DataInputStream dataInputStream,Client client){
        this.dataInputStream=dataInputStream;
        this.setDaemon(true);
    }

    @Override
    public void run(){
        String response;
        while (true) {
            try {
                response=dataInputStream.readUTF();
                if(!handleResponse(response))
                    client.setRecentResponse(response);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private boolean handleResponse(String response){

        setResponseReceived(true);

        if(!response.contains("AUTO"))
            return false;
        

        //TODO: FILL AUTO RESPONSES
        return true;
    }


    public void setResponseReceived(boolean state){
        isResponseReceived=state;
    }

    public boolean isResponseReceived(){
        return isResponseReceived;
    }
}
