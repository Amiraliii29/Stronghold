package Model;

import java.lang.reflect.Type;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import Controller.JsonConverter;
import Controller.UserInfoOperator;
import Main.Client;
import Main.Request;

public class User {
    private static ArrayList<User> users;
    private static SecureRandom randomGenerator=new SecureRandom();

    private ArrayList<String> friends;
    private ArrayList<String> usersWithFriendRequest;
    private boolean isOnline;
    private String avatarFileName;
    private String username;
    private String password;
    private String nickName;
    private String email;
    private String slogan;
    private String securityQuestion;
    private boolean stayLoggedIn;
    private int highScore;
    private int rank;
    private String lastOnlineTime;

    static {
        users = new ArrayList<>();
    }

    public User(String username, String password, String nickname,String email, String slogan) {
        this.username = username;
        this.password = password;
        this.nickName=nickname;
        this.email = email;
        this.slogan = slogan;
        avatarFileName=Integer.toString(randomGenerator.nextInt(4)+1)+".png";
        this.friends=new ArrayList<>();
        this.usersWithFriendRequest=new ArrayList<>();
        users.add(this);
    }

    public String getAvatarFileName(){
        if(avatarFileName != null)
        return avatarFileName;

        return "1.png";
    }

    public void setAvatarFileName(String name){
        this.avatarFileName=name;
    }

    public String getUsername() {
        return username;
    }

    public String getLastOnlineTime() {
        return lastOnlineTime;
    }

    public void setLastOnlineTime(String lastOnlineTime) {
        this.lastOnlineTime = lastOnlineTime;
    }

    public String getPassword() {
        return password;
    }

    public String getNickName() {
        return nickName;
    }

    public String getEmail() {
        return email;
    }

    public String getSlogan() {
        return slogan;
    }

    public String getSecurityQuestion() {
        return securityQuestion;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public void setHighScore(int highScore){
        this.highScore = highScore;
    }

    public int getRank(){
        return rank;
    }

    public int getHighScore(){
        return highScore;
    }

    public void setStayLoggedIn(boolean status){
        stayLoggedIn=status;
    }

    public boolean getStayLoggedIn(){
        return stayLoggedIn;
    }

    public void setFriends(ArrayList<String> friends) {
        this.friends = friends;
    }

    public void setUsersWithFriendRequest(ArrayList<String> usersWithFriendRequest) {
        this.usersWithFriendRequest = usersWithFriendRequest;
    }

    public void addToFriends(String username){
        friends.add(username);
    }

    public ArrayList<String> getFriends(){
        return friends;
    }

    public boolean isOnline(){
        return isOnline;
    }

    public void setOnline(boolean status){
        isOnline=status; 
    }

    public ArrayList<String> getUsersWithFriendRequest(){
        return usersWithFriendRequest;
    }

    public void addToFriendRequests(String username){
        usersWithFriendRequest.add(username);
    }

    public static void handleFriendRequest(Request request){
        User sender= User.getUserByUserName(request.argument.get("Sender"));
        User reciever=User.getUserByUserName(request.argument.get("Reciever"));
        
        reciever.addToFriendRequests(sender.getUsername());
        try {
            UserInfoOperator.storeUserDataInJson(reciever, "src/main/resources/jsonData/Users.json");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        Client.updateAllClientsData();
    }

    public static void handleSubmitFriendship(Request request){

        
        User sender= User.getUserByUserName(request.argument.get("Sender"));
        User reciever=User.getUserByUserName(request.argument.get("Reciever"));

        if(request.argument.get("IsAccepted").equals("false")){
            reciever.getUsersWithFriendRequest().remove(sender.getUsername());
            sender.getUsersWithFriendRequest().remove(reciever.getUsername());
            return;
        }else{
        sender.addToFriends(reciever.username);
        reciever.addToFriends(sender.username);
        }
        try {
            UserInfoOperator.storeUserDataInJson(sender, "src/main/resources/jsonData/Users.json");
            UserInfoOperator.storeUserDataInJson(reciever, "src/main/resources/jsonData/Users.json");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }finally{
        Client.updateAllClientsData();
        }
    }

    public static User getUserByUserName(String userName) {
        for (User user : users)
            if (user.getUsername().equals(userName))
                return user;
        return null;
    }

    public static User getUserByEmail(String email) {
        for (User user : users)
            if (user.getEmail().equals(email))
                return user;

        return null;
    }

    public static void handleLogin(String username,Client client){
        User user=getUserByUserName(username);
        client.setUser(user);
        user.setOnline(true);
        Client.updateAllClientsData();
    }

    public static ArrayList<User> getUsers() {
        return users;
    }

    public boolean isStayedLoggedIn() {
        return stayLoggedIn;
    }

    public static void addUser(User user){
        users.add(user);
    }

    public static String handleGetUsersRequest(){
        String response=new Gson().toJson(users);
        System.out.println(response);
        return response;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username) && Objects.equals(password, user.password) && Objects.equals(nickName, user.nickName) && Objects.equals(email, user.email) && Objects.equals(slogan, user.slogan) && Objects.equals(securityQuestion, user.securityQuestion);
    }
}