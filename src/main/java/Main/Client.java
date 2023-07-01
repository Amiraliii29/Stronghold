package Main;

import Model.ChatRoom;
import Model.DataBase;
import Model.User;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;

import com.google.gson.Gson;

import Controller.ProfileMenuController;
import Controller.SignUpMenuController;
import javafx.scene.transform.Rotate;

public class Client extends Thread{
    private final String token;
    private final Socket socket;
    private final DataInputStream dataInputStream;
    private final DataOutputStream dataOutputStream;
    private User user;
    private UserDataBase userDataBase;


    public Client(Socket socket) throws IOException {
        this.socket = socket;
        dataInputStream = new DataInputStream(socket.getInputStream());
        dataOutputStream = new DataOutputStream(socket.getOutputStream());
        DataBase.addToAllClients(this);
        token = encodeToken(generateToken());
    }


    @Override
    public void run() {
        try {
            dataOutputStream.writeUTF(token);

            String json = dataInputStream.readUTF();
            Request request = Request.fromJson(json);

            while (!request.verify(token)) {
                json = dataInputStream.readUTF();
                request = Request.fromJson(json);
            }

            while (!request.normalRequest.equals(NormalRequest.CLOSE)) {

                requestHandler(request);

                json = dataInputStream.readUTF();
                request = Request.fromJson(json);

                while (!request.verify(token)) {
                    json = dataInputStream.readUTF();
                    request = Request.fromJson(json);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private void requestHandler(Request request) throws IOException {

        String result="AUTO";

        if(request.normalRequest.equals(NormalRequest.SIGNUP))
            result=SignUpMenuController.handleSignupRequest(request.argument,this);

        else if(request.normalRequest.equals(NormalRequest.CHANGE_PROFILE_FIELDS))
            result=ProfileMenuController.handleProfileFieldsChange(request.argument,user);
        
        else if(request.normalRequest.equals(NormalRequest.REMOVE_SLOGAN))
            result=ProfileMenuController.removeSlogan(user).toString();
        
        else if(request.normalRequest.equals(NormalRequest.CHANGE_PASSWORD))
            result=ProfileMenuController.handleChangePassword(request.argument, user);
        
        else if(request.normalRequest.equals(NormalRequest.GET_USER_BY_USERNAME)) {
            result = new Gson().toJson(User.getUserByUserName(request.argument.get("Username")));
            updateAllClientsData();
        }

        else if(request.normalRequest.equals(NormalRequest.GET_USERS_DATA) || request.normalRequest.equals(NormalRequest.LOAD_ALL_USERS_DATA) )
            result=new Gson().toJson(User.handleGetUsersRequest());

        else if(request.normalRequest.equals(NormalRequest.SEND_FRIEND_REQUSET))
            User.handleFriendRequest(request);

        else if(request.normalRequest.equals(NormalRequest.SUBMIT_FRIENDSHIP))
            User.handleFriendRequest(request);

        else if(request.normalRequest.equals(NormalRequest.SEND_GLOBAL_MESSAGE))
            sendGlobalMessage(request);

        else if(request.normalRequest.equals(NormalRequest.SEND_PRIVATE_MESSAGE))
            sendPrivateMessage(request);

        else if(request.normalRequest.equals(NormalRequest.CREATE_ROOM))
            createRoom(request);
        else if(request.normalRequest.equals(NormalRequest.SEND_ROOM_MESSAGE))
            sendRoomMessage(request);
        else if(request.normalRequest.equals(NormalRequest.DELETE_PUBLIC_MESSAGE))
            deleteGlobalMessage(request);
        else if(request.normalRequest.equals(NormalRequest.DELETE_PRIVATE_MESSAGE))
            deletePrivateMessage(request);
        else if(request.normalRequest.equals(NormalRequest.DELETE_ROOM_MESSAGE))
            deleteRoomMessage(request);
        else if(request.normalRequest.equals(NormalRequest.EDIT_GLOBAL_MESSAGE))
            editGlobalMessage(request);
        else if(request.normalRequest.equals(NormalRequest.EDIT_PRIVATE_MESSAGE))
            editPrivateMessage(request);
        else if(request.normalRequest.equals(NormalRequest.EDIT_ROOM_MESSAGE))
            editRoomMessage(request);

        //TODO: FILL THE REST;




        try {
            dataOutputStream.writeUTF(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void editRoomMessage(Request request) throws IOException {
        int roomID = Integer.parseInt(request.argument.get("ID"));
        ChatRoom chatRoom = ChatRoom.getRoomByID(roomID);
        for (Client client : chatRoom.getClientsInRoom()) {
            client.dataOutputStream.writeUTF("AUTO" + request.toJson());
        }
    }

    private void editPrivateMessage(Request request) throws IOException {
        Client receiver = DataBase.getClientByUserName(request.argument.get("receiverUserName"));
        Client sender = DataBase.getClientByUserName(request.argument.get("userName"));

        sender.dataOutputStream.writeUTF("AUTO" + request.toJson());
        receiver.dataOutputStream.writeUTF("AUTO" + request.toJson());
    }

    private void editGlobalMessage(Request request) throws IOException {
        for (Client allClient : DataBase.getAllClients()) {
            allClient.dataOutputStream.writeUTF("AUTO" + request.toJson());
        }
    }

    private void deleteRoomMessage(Request request) throws IOException {
        int roomID = Integer.parseInt(request.argument.get("ID"));
        ChatRoom chatRoom = ChatRoom.getRoomByID(roomID);
        for (Client client : chatRoom.getClientsInRoom()) {
            client.dataOutputStream.writeUTF("AUTO" + request.toJson());
        }
    }

    private void deletePrivateMessage(Request request) throws IOException {
        String receiverUserName = request.argument.get("receiverUserName");
        Client receiver = DataBase.getClientByUserName(receiverUserName);
        Client sender = DataBase.getClientByUserName(request.argument.get("userName"));
        receiver.dataOutputStream.writeUTF("AUTO" + request.toJson());
        sender.dataOutputStream.writeUTF("AUTO" + request.toJson());
    }

    private void deleteGlobalMessage(Request request) throws IOException {
        for (Client allClient : DataBase.getAllClients()) {
            allClient.dataOutputStream.writeUTF("AUTO" + request.toJson());
        }
    }

    private void sendRoomMessage(Request request) throws IOException {
        int roomID = Integer.parseInt(request.argument.get("ID"));

        ChatRoom chatRoom = ChatRoom.getRoomByID(roomID);

        for (Client client : chatRoom.getClientsInRoom()) {
            client.dataOutputStream.writeUTF("AUTO" + request.toJson());
        }
    }

    private void createRoom(Request request) throws IOException {
        ArrayList<Client> clientsInRoom = new ArrayList<>();

        clientsInRoom.add(DataBase.getClientByUserName(request.argument.get("user0")));
        if(request.argument.get("user1") != null)
            clientsInRoom.add(DataBase.getClientByUserName(request.argument.get("user1")));
        if(request.argument.get("user2") != null)
            clientsInRoom.add(DataBase.getClientByUserName(request.argument.get("user2")));
        if(request.argument.get("user3") != null)
            clientsInRoom.add(DataBase.getClientByUserName(request.argument.get("user3")));
        if(request.argument.get("user4") != null)
            clientsInRoom.add(DataBase.getClientByUserName(request.argument.get("user4")));
        if(request.argument.get("user5") != null)
            clientsInRoom.add(DataBase.getClientByUserName(request.argument.get("user5")));
        if(request.argument.get("user6") != null)
            clientsInRoom.add(DataBase.getClientByUserName(request.argument.get("user6")));
        if(request.argument.get("user7") != null)
            clientsInRoom.add(DataBase.getClientByUserName(request.argument.get("user7")));

        ChatRoom room = new ChatRoom(clientsInRoom);
        Request request1 = new Request(null , NormalRequest.ADD_ROOM_TO_CLIENT);
        request1.argument.put("ID" , room.getID() + "");
        for (Client client : clientsInRoom) {
            client.dataOutputStream.writeUTF(request1.toJson());
        }
    }

    private void sendPrivateMessage(Request request) throws IOException {
        Client targetClient = null;
        Client senderClient = null;

        for (Client allClient : DataBase.getAllClients()) {
            if(allClient.getUser().getUsername().equals(request.argument.get("receiverUserName")))
                targetClient = allClient;
            else if(allClient.getUser().getUsername().equals(request.argument.get("userName")))
                senderClient = allClient;
        }

        targetClient.dataOutputStream.writeUTF("AUTO" + request.toJson());
        senderClient.dataOutputStream.writeUTF("AUTO" + request.toJson());
    }

    private void sendGlobalMessage(Request request) throws IOException {
        for (Client allClient : DataBase.getAllClients()) {
            Request requestToSend = new Request(null , NormalRequest.RECEIVE_GLOBAL_MESSAGE);
            requestToSend.argument.put("userName" , request.argument.get("userName"));
            requestToSend.argument.put("avatar" , request.argument.get("avatar"));
            requestToSend.argument.put("message" , request.argument.get("message"));

            allClient.getDataOutputStream().writeUTF("AUTO" + requestToSend.toJson());
        }
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

    public User getUser() {
        return user;
    }

    public UserDataBase getUserDataBase() {
        return userDataBase;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setUserDataBase(UserDataBase userDataBase) {
        this.userDataBase = userDataBase;
    }

    private static String generateToken() {
        String alphanumeric = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        String specialChars = "!@#$%^&*()_+";
        String symbols = "~`-={}[]\\|;:'\",.<>?/";

        String combinedChars = alphanumeric + specialChars + symbols;

        SecureRandom random = new SecureRandom();

        StringBuilder password = new StringBuilder();
        for (int i = 0; i < 12; i++) {
            int index = random.nextInt(combinedChars.length());
            password.append(combinedChars.charAt(index));
        }

        return password.toString();
    }

    private static String encodeToken(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = digest.digest(password.getBytes());

            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }

            return sb.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void startReadingFile(String pathToStore){
        FileOutputStream fout=null;
        try {
            fout = new FileOutputStream(pathToStore);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
            int i;
            try {
                while ( (i = this.dataInputStream.read()) > -1) {
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

    public void sendFile (String fileAddress) {
        int i;
        FileInputStream fis=null;
        try {
            fis = new FileInputStream ("/path/to/your/image.jpg");
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


    public static void updateAllClientsData(){
        Gson gson=new Gson();
        for (Client client : DataBase.getAllClients()) 
            try {
                Request req=new Request(null, NormalRequest.UPDATE_YOUR_DATA);
                client.dataOutputStream.writeUTF("AUTO"+gson.toJson(req));
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
 
}
