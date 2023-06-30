package Model;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Objects;

import com.google.gson.Gson;

import Controller.JsonConverter;
import Controller.Orders;
import Main.Client;
import Main.NormalRequest;
import Main.Request;
import View.ProfileMenu;

public class User {
    private static User currentUser;
    private static SecureRandom randomGenerator=new SecureRandom();
    private static ArrayList<User> users;

    private ArrayList<User> friends;
    private ArrayList<User> usersWithFriendRequest;
    private String avatarFileName;
    private String username;
    private String password;
    private String nickName;
    private String email;
    private String slogan;
    private String securityQuestion;
    private boolean stayLoggedIn;
    private boolean isOnline;
    private int highScore;
    private int rank;


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

    public void setFriends(ArrayList<User> friends) {
        this.friends = friends;
    }

    public void setUsersWithFriendRequest(ArrayList<User> usersWithFriendRequest) {
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

    public void addToFriends(User user){
        friends.add(user);
    }

    public ArrayList<User> getFriends(){
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

    public ArrayList<User> getUsersWithFriendRequest(){
        return usersWithFriendRequest;
    }

    public void addToFriendRequests(User user){
        usersWithFriendRequest.add(user);
    }

    public boolean isFriendsWithCurrentUser(){
        return getFriends().contains(currentUser);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username) && Objects.equals(password, user.password) && Objects.equals(nickName, user.nickName) && Objects.equals(email, user.email) && Objects.equals(slogan, user.slogan) && Objects.equals(securityQuestion, user.securityQuestion);
    }

    public static void getUsersFromServer(){
        Request request=new Request(NormalRequest.GET_USERS_DATA);
        Client.client.sendRequestToServer(request, true);
        String result=Client.client.getRecentResponse();
        ArrayList<User> users= new Gson().fromJson(result, ArrayList.class);
        setUsers(users);
        if(ProfileMenu.profileMenu!=null)
            ProfileMenu.profileMenu.showProfileProtocol();
    }

    public static void sendFriendRequest(User targetFriend){

        if(targetFriend.getFriends().contains(currentUser) ||
           targetFriend.getUsersWithFriendRequest().contains(currentUser)){
                Orders.createNotificationDialog("Result","Friend Request Delivery:","Error: you are either already friends with user or have sent a friend request!",Orders.yellowNotifErrorColor);
                return;
           }

        targetFriend.getUsersWithFriendRequest().add(targetFriend);
        Request request=new Request(NormalRequest.SEND_FRIEND_REQUSET);
        Gson gson=new Gson();
        request.addToArguments("Sender", gson.toJson(currentUser));
        request.addToArguments("Reciever", gson.toJson(targetFriend));
        Client.client.sendRequestToServer(request, false);

        Orders.createNotificationDialog("Result","Friend Request Delivery:","Friend request was sent succesfully!",Orders.greenNotifSuccesColor);
    }

    public static void submitFriendship(User newFriend){
        Request request=new Request(NormalRequest.SUBMIT_FRIENDSHIP);
        Gson gson=new Gson();
        request.addToArguments("User1", gson.toJson(currentUser));
        request.addToArguments("User2", gson.toJson(newFriend));
        Client.client.sendRequestToServer(request, false);
    }

    public static ArrayList<User> getUsers() {
        return users;
    }
}