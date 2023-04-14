package Model;

import Citizen.*;
import java.util.ArrayList;
import java.util.HashMap;

public class User {

    private String username;
    private String password;
    private String nickName;
    private String email;
    private String slogan;
    private String securityQuestion;
    private int highscore;
    private int rank;


    private ArrayList<Building> Buildings;
    private HashMap <String , Integer> Resources;
    private ArrayList <Troop> Army;
    private Government Government;
    private int gold;
    private int availablePopulation;
    private int totalPopulation;


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

    public User( String username, String password,String email ,String slogan ){
        this.username=username;
        this.password=password;
        this.email=email;
        this.slogan=slogan;
    }

    public void setRank(int rank){
        this.rank=rank;
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


}