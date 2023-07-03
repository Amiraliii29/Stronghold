package Main;

import Controller.JsonConverter;
import Model.Buildings.Building;
import Model.Buildings.Defence;
import Model.ChatRoom;
import Model.DataBase;
import Model.Map;
import Model.Units.Unit;
import Model.User;
import java.io.*;
import java.lang.ref.ReferenceQueue;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;

import Controller.GameRoomDatabase;
import Controller.ProfileMenuController;
import Controller.SignUpMenuController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class Client extends Thread {
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
        loadGlobalChats();
    }

    private void loadGlobalChats() throws FileNotFoundException {
    }


    @Override
    public void run() {
        try {
            dataOutputStream.writeUTF(token);
            sendData();

            String json = dataInputStream.readUTF();
            Request request = Request.fromJson(json);

             while (!request.verify(token)) {
                 json = dataInputStream.readUTF();
                 request = Request.fromJson(json);
             }

            requestHandler(request);

            while (!request.normalRequest.equals(NormalRequest.CLOSE)) {
                json = dataInputStream.readUTF();
                request = Request.fromJson(json);

                while (!request.verify(token)) {
                    json = dataInputStream.readUTF();
                    request = Request.fromJson(json);
                }
                requestHandler(request);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private void sendData() throws IOException {
        Gson gson = new Gson();

        String jsonUnit = gson.toJson(Unit.getAllUnits());
        dataOutputStream.writeUTF(jsonUnit);

        String jsonBuilding = gson.toJson(Building.getBuildingsNames());
        dataOutputStream.writeUTF(jsonBuilding);

        String defences = gson.toJson(Defence.getDefencesName());
        dataOutputStream.writeUTF(defences);
    }


    private void requestHandler(Request request) throws IOException {

        String result = "";// was "AUTO"
        System.out.println(request.normalRequest + "=====");

        if (request.normalRequest.equals(NormalRequest.SIGNUP))
            result = SignUpMenuController.handleSignupRequest(request.argument, this);

        else if(request.normalRequest.equals(NormalRequest.LOGIN))
            User.handleLogin(request.argument.get("Username"), this);

        else if(request.normalRequest.equals(NormalRequest.CHANGE_PROFILE_FIELDS))
            result=ProfileMenuController.handleProfileFieldsChange(request.argument,user);
        
        else if(request.normalRequest.equals(NormalRequest.REMOVE_SLOGAN))
            result=ProfileMenuController.removeSlogan(user).toString();
        
        else if(request.normalRequest.equals(NormalRequest.CHANGE_PASSWORD))
            result=ProfileMenuController.handleChangePassword(request.argument, user);
        
        else if(request.normalRequest.equals(NormalRequest.GET_USER_BY_USERNAME))
            result=new Gson().toJson(User.getUserByUserName(request.argument.get("Username")));

        else if(request.normalRequest.equals(NormalRequest.GET_USERS_DATA) )
            result=User.handleGetUsersRequest();

        else if (request.normalRequest.equals(NormalRequest.CHANGE_PROFILE_FIELDS))
            result = ProfileMenuController.handleProfileFieldsChange(request.argument, user);

        else if (request.normalRequest.equals(NormalRequest.REMOVE_SLOGAN))
            result = ProfileMenuController.removeSlogan(user).toString();

        else if (request.normalRequest.equals(NormalRequest.CHANGE_PASSWORD))
            result = ProfileMenuController.handleChangePassword(request.argument, user);

        else if (request.normalRequest.equals(NormalRequest.GET_USERS_DATA) || request.normalRequest.equals(NormalRequest.LOAD_ALL_USERS_DATA))
            result = new Gson().toJson(User.handleGetUsersRequest());

        else if (request.normalRequest.equals(NormalRequest.SEND_FRIEND_REQUSET))
            User.handleFriendRequest(request);

        else if(request.normalRequest.equals(NormalRequest.SUBMIT_FRIENDSHIP))
            User.handleSubmitFriendship(request);

        else if (request.normalRequest.equals(NormalRequest.SEND_GLOBAL_MESSAGE))
            sendGlobalMessage(request);

        else if (request.normalRequest.equals(NormalRequest.SEND_PRIVATE_MESSAGE))
            sendPrivateMessage(request);

        else if (request.normalRequest.equals(NormalRequest.CREATE_ROOM))
            createRoom(request);

        else if (request.normalRequest.equals(NormalRequest.SEND_ROOM_MESSAGE))
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

        else if (request.normalRequest.equals(NormalRequest.MAP_NAME))
            sendMapNames();

        else if (request.normalRequest.equals(NormalRequest.GET_MAP))
            sendMap(request);

        else if (request.normalRequest.equals(NormalRequest.CHECK_MAP_NAME))
            checkMapName(request);

        else if (request.normalRequest.equals(NormalRequest.SAVE_MAP))
            saveMap(request);

        else if(request.normalRequest.equals(NormalRequest.SEEN_PUBLIC_MESSAGE))
            seenPublicMessage(request);

        else if(request.normalRequest.equals(NormalRequest.SEEN_PRIVATE_MESSAGE))
            seenPrivateMessage(request);

        else if(request.normalRequest.equals(NormalRequest.SEEN_ROOM_MESSAGE))
            seenRoomMessage(request);

        else if(request.normalRequest.equals(NormalRequest.LOGOUT))
            logout(request);

        else if (request.normalRequest.equals(NormalRequest.CREATE_GAMEROOM))
            createUnit(request);

        else if(request.normalRequest.equals(NormalRequest.SAVE_PUBLIC_CHAT))
            savePublicChats(request);

        else if (request.gameRequest.equals(GameRequest.CHANGE_MONEY))
            userDataBase.getGovernment().changeMoney(Integer.parseInt(request.argument.get("money")));

        else if (request.gameRequest.equals(GameRequest.CREATE_UNIT))
            createUnit(request);

        else if (request.gameRequest.equals(GameRequest.CREATE_UNIT))
            createUnit(request);

        //TODO: FILL THE REST;


        try {
            if (!result.equals("")) dataOutputStream.writeUTF(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void logout(Request request) {
        User user = User.getUserByUserName(request.argument.get("userName"));
        Client client = DataBase.getClientByUserName(request.argument.get("userName"));

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
        LocalDateTime now = LocalDateTime.now();
        user.setLastOnlineTime(dtf.format(now));
        DataBase.getAllClients().remove(client);
        user.setOnline(false);
        JsonConverter.putUserDataInFile(user , "src/main/resources/jsonData/Users.json");
        updateAllClientsData();
    }
    private  void savePublicChats(Request request){
        String string = request.argument.get("string");
        try{
            File file=new File("src/main/resources/jsonData/PublicChats.json");
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(string);
            fileWriter.flush();
            fileWriter.close();
        } catch ( IOException e) {
            e.printStackTrace();
        }
    }

    private void seenRoomMessage(Request request) throws IOException {
        Client sender = DataBase.getClientByUserName(request.argument.get("userName"));
        Request requestToSend = new Request(null , NormalRequest.SEEN_ROOM_MESSAGE);
        requestToSend.argument.put("message" , request.argument.get("message"));
        requestToSend.argument.put("userName" , request.argument.get("userName"));
        sender.dataOutputStream.writeUTF("AUTO" + requestToSend.toJson());
    }

    private void seenPrivateMessage(Request request) throws IOException {
        Client sender = DataBase.getClientByUserName(request.argument.get("userName"));
        Request requestToSend = new Request(null , NormalRequest.SEEN_PRIVATE_MESSAGE);
        requestToSend.argument.put("message" , request.argument.get("message"));
        requestToSend.argument.put("userName" , request.argument.get("userName"));
        sender.dataOutputStream.writeUTF("AUTO" + requestToSend.toJson());
    }

    private void seenPublicMessage(Request request) throws IOException {
        Client sender = DataBase.getClientByUserName(request.argument.get("userName"));
        Request requestToSend = new Request(null , NormalRequest.SEEN_PUBLIC_MESSAGE);
        requestToSend.argument.put("message" , request.argument.get("message"));
        requestToSend.argument.put("userName" , request.argument.get("userName"));
        sender.dataOutputStream.writeUTF("AUTO" + requestToSend.toJson());
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
            Request requestToSend = new Request(null, NormalRequest.EDIT_GLOBAL_MESSAGE);
            requestToSend.argument.put("userName", request.argument.get("userName"));
            requestToSend.argument.put("avatar", request.argument.get("avatar"));
            requestToSend.argument.put("message", request.argument.get("message"));
            requestToSend.argument.put("newMessage" , request.argument.get("newMessage"));
            allClient.dataOutputStream.writeUTF("AUTO" + requestToSend.toJson());
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

    private void saveMap(Request request) {
        String name = request.argument.get("name");

        try {
            int jsonLength = dataInputStream.readInt();
            byte[] jsonBytes = new byte[jsonLength];
            dataInputStream.readFully(jsonBytes);

            ObjectMapper objectMapper = new ObjectMapper();
            Map map = objectMapper.readValue(jsonBytes, Map.class);
            map.setName(name);

            Map.saveMap(map,name);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void checkMapName(Request request) throws IOException {
        String fileName = request.argument.get("name") + ".json";

        File folder = new File("src/main/resources/Map");

        String[] fileNames = folder.list();
        for(String file : fileNames) {
            if (file.equals(fileName)) {
                dataOutputStream.writeUTF("false");
                return;
            }
        }
        dataOutputStream.writeUTF("true");
    }

    private void sendMap(Request request) {
        String mapName = request.argument.get("name");
        Map map = Map.loadMap(mapName);

        ObjectMapper objectMapper = new ObjectMapper();
        Object json = map;

        try {
            byte[] jsonBytes = objectMapper.writeValueAsBytes(json);

            dataOutputStream.writeInt(jsonBytes.length);
            dataOutputStream.write(jsonBytes);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendMapNames() {
        File folder = new File("src/main/resources/Map");

        String[] fileNames = folder.list();

        String names = "";

        for(String file : fileNames) {
            for (char c : file.toCharArray()) {
                if (c == '.') {
                    names += ",";
                    break;
                }
                names += c;
            }
        }

        try {
            dataOutputStream.writeUTF(names);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void deleteGlobalMessage() {
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
        if (!request.argument.get("user1").equals(""))
            clientsInRoom.add(DataBase.getClientByUserName(request.argument.get("user1")));
        if (!request.argument.get("user2").equals(""))
            clientsInRoom.add(DataBase.getClientByUserName(request.argument.get("user2")));
        if (!request.argument.get("user3").equals(""))
            clientsInRoom.add(DataBase.getClientByUserName(request.argument.get("user3")));
        if (!request.argument.get("user4").equals(""))
            clientsInRoom.add(DataBase.getClientByUserName(request.argument.get("user4")));
        if (!request.argument.get("user5").equals(""))
            clientsInRoom.add(DataBase.getClientByUserName(request.argument.get("user5")));
        if (!request.argument.get("user6").equals(""))
            clientsInRoom.add(DataBase.getClientByUserName(request.argument.get("user6")));
        if (!request.argument.get("user7").equals(""))
            clientsInRoom.add(DataBase.getClientByUserName(request.argument.get("user7")));

        ChatRoom room = new ChatRoom(clientsInRoom);
        Request request1 = new Request(null, NormalRequest.ADD_ROOM_TO_CLIENT);
        request1.argument.put("ID", room.getID() + "");
        for (Client client : clientsInRoom) {
            client.dataOutputStream.writeUTF("AUTO" + request1.toJson());
        }
    }

    private void sendPrivateMessage(Request request) throws IOException {
        Client targetClient = DataBase.getClientByUserName(request.argument.get("receiverUserName"));
        Client senderClient = DataBase.getClientByUserName(request.argument.get("userName"));

        targetClient.dataOutputStream.writeUTF("AUTO" + request.toJson());
        senderClient.dataOutputStream.writeUTF("AUTO" + request.toJson());
    }

    private void sendGlobalMessage(Request request) throws IOException {
        for (Client allClient : DataBase.getAllClients()) {
            Request requestToSend = new Request(null, NormalRequest.RECEIVE_GLOBAL_MESSAGE);
            requestToSend.argument.put("userName", request.argument.get("userName"));
            requestToSend.argument.put("avatar", request.argument.get("avatar"));
            requestToSend.argument.put("message", request.argument.get("message"));
            requestToSend.argument.put("seen" , request.argument.get("seen"));
            requestToSend.argument.put("time" , request.argument.get("time"));

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

    public static void updateAllClientsData(){
        Gson gson=new Gson();
        for (Client client : DataBase.getAllClients()) 
            try {
                Request req=new Request(null, NormalRequest.UPDATE_YOUR_DATA);
                req.argument.put("Users", gson.toJson(User.getUsers()));
                client.dataOutputStream.writeUTF("AUTO"+gson.toJson(req));
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    public void createUnit(Request request) {

    }

    public static void updateGameRoomsForClients(){
        Gson gson=new Gson();
        for (Client client : DataBase.getAllClients())
            try {
                Request req=new Request(null, NormalRequest.TRANSFER_GAMEROOMS_DATA);
                req.argument.put("GameRooms", gson.toJson(GameRoomDatabase.getAllRoomDatabases()));
                client.dataOutputStream.writeUTF("AUTO"+gson.toJson(req));
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
}
