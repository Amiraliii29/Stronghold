package Model;

import java.util.ArrayList;

import Controller.JsonConverter;

public class User {
    private static ArrayList<User> users;
    private static User currentUser;

    private String username;
    private String password;
    private String nickName;
    private String email;
    private String slogan;
    private String securityQuestion;
    private boolean stayLoggedIn;
    private int highscore;
    private int rank;

    static {
        users = new ArrayList<>();
        JsonConverter.fillFormerUsersDatabase("src/main/java/jsonData/Users.json");
    }

    public User(String username, String password, String nickname,String email, String slogan) {
        this.username = username;
        this.password = password;
        this.nickName=nickname;
        this.email = email;
        this.slogan = slogan;
        users.add(this);
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

    public void setHighscore(int highscore){
        this.highscore=highscore;
    }

    public int getRank(){
        return rank;
    }

    public int getHighscore(){
        return highscore;
    }

    public void setStayLoggedIn(boolean status){
        stayLoggedIn=status;
    }

    public boolean getStayLoggedIn(){
        return stayLoggedIn;
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

    public static User getCurrentUser(){
        return currentUser;
    }

    public static void setCurrentUser(User user){
        currentUser=user;
    }
}