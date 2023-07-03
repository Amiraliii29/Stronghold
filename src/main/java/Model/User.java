package Model;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import Controller.JsonConverter;
import Controller.Orders;
import Controller.ProfileMenuController;
import Main.Client;
import Main.NormalRequest;
import Main.Request;
import View.GameRoom;
import View.ProfileMenu;
import View.SignUpMenu;
import javafx.application.Platform;

public class User {
    private static User currentUser;
    private static SecureRandom randomGenerator=new SecureRandom();
    private static ArrayList<User> users;
    private ArrayList<String> friends;
    private ArrayList<String> usersWithFriendRequest;
    private String avatarFileName;
    private String username;
    private String password;
    private String nickName;
    private String email;
    private String slogan;
    private String securityQuestion;
    private boolean stayLoggedIn;
    private boolean isOnline;
    private String lastOnlineTime;
    private int highScore;
    private int rank;

    static{
        users=new ArrayList<>();
    }

    public User(String username, String password, String nickname,String email, String slogan) {
        this.username = username;
        this.password = password;
        this.nickName=nickname;
        this.email = email;
        this.slogan = slogan;
        this.friends=new ArrayList<>();
        this.usersWithFriendRequest=new ArrayList<>();
        avatarFileName=Integer.toString(randomGenerator.nextInt(4)+1)+".png";
    }

    public String getLastOnlineTime(){
        return lastOnlineTime;
    }

    public void setLastOnlineTime(String time){
        this.lastOnlineTime=time;
    }


    public String getAvatarFileName(){
        if(avatarFileName != null)
            return avatarFileName;

        return "1.png";
    }

    public void setAvatarFileName(String name){
        this.avatarFileName=name;
    }

    public void setOnlineStatus(boolean status){
        this.isOnline=status;
    }

    public boolean isOnline(){
        return isOnline;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public static SecureRandom getRandomGenerator() {
        return randomGenerator;
    }

    public static void setRandomGenerator(SecureRandom randomGenerator) {
        User.randomGenerator = randomGenerator;
    }

    public void setFriends(ArrayList<String> friends) {
        this.friends = friends;
    }

    public void setUsersWithFriendRequest(ArrayList<String> usersWithFriendRequest) {
        this.usersWithFriendRequest = usersWithFriendRequest;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    public boolean isStayLoggedIn() {
        return stayLoggedIn;
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


    public static void setUsers(ArrayList<User> users) {
        User.users=users;
    }

    public void addToFriends(String username){
        friends.add(username);
    }

    public ArrayList<String> getFriends(){
        return friends;
    }

    public boolean isStayedLoggedIn() {
        return stayLoggedIn;
    }

    public static User getUserByUserName(String username){
        for (User user: users)
            if(user.getUsername().equals(username)) return user;
        return null;
    }

    public static User getUserByEmail(String email){
        for (User user: users)
            if(user.getEmail().equals(email)) return user;
        return null;
    }

    public ArrayList<String> getUsersWithFriendRequest(){
        return usersWithFriendRequest;
    }

    public void addToFriendRequests(String username){
        usersWithFriendRequest.add(username);
    }

    public boolean isFriendsWithCurrentUser(){
        return getFriends().contains(currentUser.username);
    }

    public static void addUser(User user){
        users.add(user);
    }

    public static User getCurrentUser(){
        return currentUser;
    }

    public static void setCurrentUser(User user){
        currentUser=user;
    }


    public static void getUsersFromServer(){
        Request request=new Request(NormalRequest.GET_USERS_DATA);
        Client.client.sendRequestToServer(request, true);
        String result=Client.client.getRecentResponse();
        users.clear();
        users= new Gson().fromJson(result, new TypeToken<List<User>>(){}.getType());
    }

    public static void setUsersFromJson(String usersInJson){
        users.clear();
        users= new Gson().fromJson(usersInJson, new TypeToken<List<User>>(){}.getType());
        currentUser=getUserByUserName(currentUser.username);
        ProfileMenuController.setCurrentUser(currentUser);
            
            if(ProfileMenu.profileMenu!=null)
            if(ProfileMenu.isMenuDisplayed)
               Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        ProfileMenu.profileMenu.restart();
                    }
            });  
    }

    public static void sendFriendRequest(User targetFriend){

        if(targetFriend.getFriends().contains(currentUser.username) ||
           targetFriend.getUsersWithFriendRequest().contains(currentUser.username)||
           targetFriend.getUsername().equals(currentUser.getUsername())){

            Orders.createNotificationDialog("Result","Friend Request Delivery:","Error: you are either already friends with user or have sent a friend request!",Orders.yellowNotifErrorColor);
            return;
        }

        targetFriend.getUsersWithFriendRequest().add(currentUser.getUsername());
        Request request=new Request(NormalRequest.SEND_FRIEND_REQUSET);
        request.addToArguments("Sender", currentUser.getUsername());
        request.addToArguments("Reciever", targetFriend.getUsername());
        
        Client.client.sendRequestToServer(request, false);
        Orders.createNotificationDialog("Result","Friend Request Delivery:","Friend request was sent succesfully!",Orders.greenNotifSuccesColor);
    }

    public static void submitFriendship(User newFriend,boolean isAccepted){

        String acceptance="true";
        if(!isAccepted) acceptance="false";

        Request request=new Request(NormalRequest.SUBMIT_FRIENDSHIP);
        request.addToArguments("Sender", currentUser.getUsername());
        request.addToArguments("Reciever",newFriend.getUsername() );
        request.addToArguments("IsAccepted", acceptance);
        Client.client.sendRequestToServer(request, false);
    }

    public static ArrayList<User> getUsers() {
        return users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username) && Objects.equals(password, user.password) && Objects.equals(nickName, user.nickName) && Objects.equals(email, user.email) && Objects.equals(slogan, user.slogan) && Objects.equals(securityQuestion, user.securityQuestion);
    }
}