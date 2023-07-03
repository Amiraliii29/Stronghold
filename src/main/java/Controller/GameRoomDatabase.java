package Controller;

import java.util.ArrayList;
import java.util.HashMap;

import Main.Client;
import Main.Request;
import Model.GameRoomLife;
import Model.User;

public class GameRoomDatabase {
    private static ArrayList<GameRoomDatabase> gameRoomDatabases=new ArrayList<>();
    
    private User admin;
    private boolean isPublic;
    private final String roomId;
    private final int roomCapacity;
    private ArrayList<User> usersInRoom;
    private HashMap<String,Boolean> usersPlayingStatus;
    // private transient GameRoomLife gameRoomLife;

    public GameRoomDatabase(User admin,String roomId, int roomCapacity,boolean isPublic){

        String correctId=getCorrectId(roomId);
        this.admin=admin;
        this.roomId=correctId;
        this.roomCapacity=roomCapacity;
        this.isPublic=isPublic;

        usersInRoom=new ArrayList<>();
        usersPlayingStatus=new HashMap<>();

        usersInRoom.add(admin);
        usersPlayingStatus.put(admin.getUsername(), true);
        gameRoomDatabases.add(this);
        // gameRoomLife=new GameRoomLife(this);
        // gameRoomLife.start();
    }

    public ArrayList<User> getUsersInRoom(){
        return usersInRoom;
    }

    public void setPrivace(boolean status){
        this.isPublic=status;
    }

    public boolean isPublic(){
        return isPublic;
    }

    public int getRoomCapacity(){
        return roomCapacity;
    }

    public void AddtoUsers(User user){
        // gameRoomLife.resetTimer();
        usersInRoom.add(user);
        usersPlayingStatus.put(user.getUsername(), true);
    }
    
    public String getRoomId(){
        return roomId;
    }

    public void setAdmin(User user){
        admin=user;
    }

    public User getAdmin(){
        return admin;
    }

    public void removeUser(User user){
        usersPlayingStatus.remove(user.getUsername());
        usersInRoom.remove(user);

        if(admin.getUsername().equals(user.getUsername()) && usersInRoom.size()>0);
            admin=usersInRoom.get(0);
    }

    public boolean isUserPlaying(User user){
        return usersPlayingStatus.get(user.getUsername());
    }

    public static String handleCreateRoom(HashMap<String,String> arguments){

        String roomId=arguments.get("RoomId");
        User admin=User.getUserByUserName(arguments.get("Admin"));
        int capacity=Integer.parseInt(arguments.get("Capacity"));

        new GameRoomDatabase(admin, roomId, capacity,true);

        Client.updateGameRoomsForClients();
        return "true";
    }

    public static ArrayList<GameRoomDatabase> getAllRoomDatabases(){
        return gameRoomDatabases;
    }

    public static GameRoomDatabase getRoomById(String id){
        for (GameRoomDatabase gameRoomDatabase : gameRoomDatabases) 
            if(gameRoomDatabase.getRoomId().equals(id))
                return gameRoomDatabase;
        return null;
    }

    private static String getCorrectId(String baseId){
        if(getRoomById(baseId)==null) return baseId;
        return getCorrectId(UserInfoOperator.addRandomizationToString(baseId));
    }

    public static String handleJoinGameRoomRequest(Request request){
        String id=request.argument.get("RoomId");
        String username=request.argument.get("Username");
        User joiningUser=User.getUserByUserName(username);
        if(getRoomById(id).roomCapacity==getRoomById(id).usersInRoom.size())
            return "full";
        getRoomById(id).AddtoUsers(joiningUser);
        Client.updateGameRoomsForClients();
        return "true";
    }

    public static void handleRoomPrivacyChange(Request request){
        String roomId=request.argument.get("RoomId");
        boolean state=true;

        if(request.argument.get("State").equals("false"))
            state=false;

        getRoomById(roomId).setPrivace(state);
        Client.updateGameRoomsForClients();
    }

    public static void handleLeaveGameRoom(Request request){

        String roomId=request.argument.get("RoomId");
        String username=request.argument.get("Username");

        User leavingUser=User.getUserByUserName(username);
        GameRoomDatabase targetRoom=getRoomById(roomId);

        targetRoom.getUsersInRoom().remove(leavingUser);
        
        if(targetRoom.getUsersInRoom().size()==0)
            gameRoomDatabases.remove(targetRoom);
        
        else if(targetRoom.getAdmin().getUsername().equals(leavingUser.getUsername()))
            targetRoom.setAdmin(targetRoom.getUsersInRoom().get(0));

        Client.updateGameRoomsForClients();
    }

    public static void handleSpectatingStatusChange(Request request){
        String roomId=request.argument.get("RoomId");
        String username=request.argument.get("Username");

        GameRoomDatabase targetRoom=getRoomById(roomId);

        boolean state=true;
        if(request.argument.get("Status").equals("false"))
            state=false;

        targetRoom.usersPlayingStatus.put(username, state);
        Client.updateGameRoomsForClients();
    }


}
