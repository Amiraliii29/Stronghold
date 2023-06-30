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

    
        if(!response.contains("AUTO")){
            setResponseReceived(true);
            return false;
        }

        response = response.replace("AUTO" , "");
        Request request = Request.fromJson(response);

        if(request.normalRequest.equals(NormalRequest.RECEIVE_GLOBAL_MESSAGE)){
            Client.client.globalChats.add(request);
        }
        else if(request.normalRequest.equals(NormalRequest.SEND_PRIVATE_MESSAGE)){
            Client.client.privateChats.add(request);
        }
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
