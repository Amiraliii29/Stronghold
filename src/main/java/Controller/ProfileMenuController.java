package Controller;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import Model.User;
import View.ProfileMenu;
import View.Enums.Messages.ProfileMenuMessages;

public class ProfileMenuController {

    private static User currentUser;
    
    public static void setCurrentUser(User user){
        currentUser=user;
    }

    public static String displayEntireProfile() {
        String output="";
        output=output.concat(displaySolgan()+"\n");
        output=output.concat(displayRank()+"\n");
        output=output.concat(displayHighscore()+"\n");
        return output;
    }

    public static String displaySolgan() {
        String slogan=currentUser.getSlogan();
        if(slogan.equals(""))
            return "slogan is empty!";
        
        return "your slogan is: "+slogan;
    }

    public static String displayRank() {
        int rank=currentUser.getRank();
        return "your rank is: "+rank;
    }

    public static String displayHighscore() {
        int highscore=currentUser.getHighScore();
        return "your highscore is: "+highscore;
    }

    public static ProfileMenuMessages changePassword(String oldPassword,String newPassword,String passwordConfirmation)  {

        if(oldPassword==null || newPassword ==null)
            return ProfileMenuMessages.EMPTY_FIELDS_ERROR;
            
        try {
            oldPassword=UserInfoOperator.encodeStringToSha256(oldPassword);
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        if(!oldPassword.equals(currentUser.getPassword()))
            return ProfileMenuMessages.INCORRECT_PASSWORD_ERROR;

        if(!UserInfoOperator.isPasswordStrong(newPassword))
            return ProfileMenuMessages.WEAK_PASSWORD_ERROR;
        
        try {
            newPassword=UserInfoOperator.encodeStringToSha256(newPassword);
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        if(newPassword.equals(oldPassword))
            return ProfileMenuMessages.UNCHANGED_FIELD_ERROR;
        
        try {
            passwordConfirmation=UserInfoOperator.encodeStringToSha256(passwordConfirmation);
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        if(!passwordConfirmation.equals(newPassword))
            return ProfileMenuMessages.INCORRECT_PASSWORD_VERIFICATION_ERROR;
        
        currentUser.setPassword(newPassword);
        try {
            UserInfoOperator.storeUserDataInJson(currentUser,"src/main/resources/jsonData/Users.json");
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return ProfileMenuMessages.SUCCESFUL_CHANGE_PASSWORD;
    }

    public static ProfileMenuMessages changeUsername(String newUsername)  {
        if(newUsername==null)
            return ProfileMenuMessages.EMPTY_FIELDS_ERROR;

        if(!UserInfoOperator.isUsernameFormatValid(newUsername))
          return ProfileMenuMessages.INVALID_USERNAME_ERROR;

        if(newUsername.equals(currentUser.getUsername()))
          return ProfileMenuMessages.UNCHANGED_FIELD_ERROR;

        if(User.getUserByUserName(newUsername)!=null)
          return ProfileMenuMessages.DUPLICATE_USERNAME_ERROR;

        
        currentUser.setUsername(newUsername);
        try {
            UserInfoOperator.storeUserDataInJson(currentUser,"src/main/resources/jsonData/Users.json");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return ProfileMenuMessages.SUCCESFUL_CHANGE_USERNAME;
    }

    public static ProfileMenuMessages changeEmail(String newEmail)  {

        if(newEmail==null)
            return ProfileMenuMessages.EMPTY_FIELDS_ERROR;

        if(!UserInfoOperator.isEmailFormatValid(newEmail))
            return ProfileMenuMessages.INVALID_EMAIL_ERROR;

        if(newEmail.equals(currentUser.getEmail()))
            return ProfileMenuMessages.UNCHANGED_FIELD_ERROR;

        if(User.getUserByEmail(newEmail)!=null)
            return ProfileMenuMessages.DUPLICATE_EMAIL_ERROR;
        
        currentUser.setEmail(newEmail);
        try {
            UserInfoOperator.storeUserDataInJson(currentUser,"src/main/resources/jsonData/Users.json");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return ProfileMenuMessages.SUCCESFUL_CHANGE_EMAIL;
    }

    public static ProfileMenuMessages changeNickname(String newNickname)  {
        if(newNickname==null)
            return ProfileMenuMessages.EMPTY_FIELDS_ERROR;

        if(newNickname.equals(currentUser.getNickName()))
            return ProfileMenuMessages.UNCHANGED_FIELD_ERROR;

        currentUser.setNickName(newNickname);
        try {
            UserInfoOperator.storeUserDataInJson(currentUser,"src/main/resources/jsonData/Users.json");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return ProfileMenuMessages.SUCCESFUL_CHANGE_NICKNAME;
    }

    public static ProfileMenuMessages changeSlogan(String newSlogan)  {
        if(newSlogan==null)
            return ProfileMenuMessages.EMPTY_FIELDS_ERROR;
        
        if(newSlogan.equals(currentUser.getSlogan()))
            return ProfileMenuMessages.UNCHANGED_FIELD_ERROR;
        
        currentUser.setSlogan(newSlogan);
        try {
            UserInfoOperator.storeUserDataInJson(currentUser,"src/main/resources/jsonData/Users.json");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return ProfileMenuMessages.SUCCESFUL_CHANGE_SLOGAN;
    }

    public static ProfileMenuMessages removeSlogan()  {
        currentUser.setSlogan("");
        try {
            UserInfoOperator.storeUserDataInJson(currentUser,"src/main/resources/jsonData/Users.json");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return ProfileMenuMessages.SUCCESFUL_REMOVE_SLOGAN;
    }

    public static String handleProfileFieldsChange(HashMap<String, String> profileArguments){
        String username=profileArguments.get("Username");
        String email=profileArguments.get("Email");
        String nickname=profileArguments.get("Slogan");
        String slogan=profileArguments.get("Slogan");

        String output="";
        
        if(username!=null)
            if(changeUsername(username).equals(ProfileMenuMessages.SUCCESFUL_CHANGE_USERNAME))
                output=output+"Username ";

        if(email!=null)
            if(changeEmail(email).equals(ProfileMenuMessages.SUCCESFUL_CHANGE_EMAIL))
                output=output+"Email ";

        if(nickname!=null)
            if(changeNickname(nickname).equals(ProfileMenuMessages.SUCCESFUL_CHANGE_NICKNAME))
                output=output+"Nickname ";
        
        if(slogan!=null)
            if(changeSlogan(slogan).equals(ProfileMenuMessages.SUCCESFUL_CHANGE_SLOGAN))
                output=output+"Slogan";
        return output;
    }
}
