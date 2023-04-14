package controller;

import model.User;
import view.ProfileMenuMessages;

public class ProfileMenuController {
    
    private static User targetUser;



    public static ProfileMenuMessages displayEntireProfile();

    public static ProfileMenuMessages displaySolgan();

    public static ProfileMenuMessages displayRank();

    public static ProfileMenuMessages displayHighscore();

    public static ProfileMenuMessages changePassword(String oldPassword);

    public static boolean checkPasswordsMatch(String firstPasswordEntry, String secondPasswordEntry);

    public static ProfileMenuMessages changeUsername(String newUsername);

    public static ProfileMenuMessages changeEmail(String newEmail);
        
    public static ProfileMenuMessages changeNickname(String newNickname);
        
    public static ProfileMenuMessages changeSlogan(String newSlogan);

    public static ProfileMenuMessages removeSlogan();

    public static void setTargetUser(User user);
        
}
