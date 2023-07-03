package Controller;

import java.util.ArrayList;
import java.util.HashMap;

import Main.Client;
import Model.User;

public class GameRoomDatabase {
    private static ArrayList<GameRoomDatabase> gameRoomDatabases=new ArrayList<>();
    
    private User admin;
    private boolean isPublic;
    private final String roomId;
    private final int roomCapacity;
    private ArrayList<User> usersInRoom;
    private HashMap<String,Boolean> usersPlayingStatus;

    public GameRoomDatabase(User admin,String roomId, int roomCapacity,boolean isPublic){
        this.admin=admin;
        this.roomId=roomId;
        this.roomCapacity=roomCapacity;
        this.isPublic=isPublic;
        usersInRoom=new ArrayList<>();
        usersPlayingStatus=new HashMap<>();
        gameRoomDatabases.add(this);
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

    public static ArrayList<GameRoomDatabase> getAllRoomDatabases(){
        return gameRoomDatabases;
    }


}
