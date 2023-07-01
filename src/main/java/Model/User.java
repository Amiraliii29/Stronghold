package Model;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Objects;

import com.google.gson.Gson;

import Controller.JsonConverter;
import Main.Client;
import Main.Request;

public class User {
    private static ArrayList<User> users;
    private static SecureRandom randomGenerator=new SecureRandom();

    private ArrayList<User> friends;
    private ArrayList<User> usersWithFriendRequest;
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

    static {
        users = new ArrayList<>();
        JsonConverter.fillFormerUsersDatabase("src/main/resources/jsonData/Users.json");
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

    public void setFriends(ArrayList<User> friends) {
        this.friends = friends;
    }

    public void setUsersWithFriendRequest(ArrayList<User> usersWithFriendRequest) {
        this.usersWithFriendRequest = usersWithFriendRequest;
    }

    public void addToFriends(User user){
        friends.add(user);
    }

    public ArrayList<User> getFriends(){
        return friends;
    }

    public ArrayList<User> getUsersWithFriendRequest(){
        return usersWithFriendRequest;
    }

    public void addToFriendRequests(User user){
        usersWithFriendRequest.add(user);
    }

    public static void handleFriendRequest(Request request){
        Gson gson=new Gson();
        User sender=gson.fromJson(request.argument.get("Sender"), User.class);
        User reciever=gson.fromJson(request.argument.get("Reviever"), User.class);
        reciever.addToFriendRequests(sender);
        Client.updateAllClientsData();
    }

    public static void handleSubmitFriendship(Request request){
        Gson gson=new Gson();
        User sender=gson.fromJson(request.argument.get("User1"), User.class);
        User reciever=gson.fromJson(request.argument.get("User2"), User.class);
        sender.addToFriends(reciever);
        reciever.addToFriends(sender);
        Client.updateAllClientsData();
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