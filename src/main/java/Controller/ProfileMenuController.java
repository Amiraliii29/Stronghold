package Controller;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import Model.User;
import View.Enums.Messages.ProfileMenuMessages;

public class ProfileMenuController {

   

    public static String displayEntireProfile(User user) {
        String output="";
        output=output.concat(displaySolgan(user)+"\n");
        output=output.concat(displayRank(user)+"\n");
        output=output.concat(displayHighscore(user)+"\n");
        return output;
    }

    public static String displaySolgan(User user) {
        String slogan=user.getSlogan();
        if(slogan.equals(""))
            return "slogan is empty!";
        
        return "your slogan is: "+slogan;
    }

    public static String displayRank(User user) {
        int rank=user.getRank();
        return "your rank is: "+rank;
    }

    public static String displayHighscore(User user) {
        int highscore=user.getHighScore();
        return "your highscore is: "+highscore;
    }

    public static ProfileMenuMessages changePassword(String oldPassword,String newPassword,String passwordConfirmation,User user)  {

        if(oldPassword==null || newPassword ==null)
            return ProfileMenuMessages.EMPTY_FIELDS_ERROR;
            
        try {
            oldPassword=UserInfoOperator.encodeStringToSha256(oldPassword);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        if(!oldPassword.equals(user.getPassword()))
            return ProfileMenuMessages.INCORRECT_PASSWORD_ERROR;

        if(!UserInfoOperator.isPasswordStrong(newPassword))
            return ProfileMenuMessages.WEAK_PASSWORD_ERROR;
        
        try {
            newPassword=UserInfoOperator.encodeStringToSha256(newPassword);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        if(newPassword.equals(oldPassword))
            return ProfileMenuMessages.UNCHANGED_FIELD_ERROR;
        
        try {
            passwordConfirmation=UserInfoOperator.encodeStringToSha256(passwordConfirmation);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        if(!passwordConfirmation.equals(newPassword))
            return ProfileMenuMessages.INCORRECT_PASSWORD_VERIFICATION_ERROR;
        
        user.setPassword(newPassword);
        try {
            UserInfoOperator.storeUserDataInJson(user,"src/main/resources/jsonData/Users.json");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return ProfileMenuMessages.SUCCESFUL_CHANGE_PASSWORD;
    }

    public static ProfileMenuMessages changeUsername(String newUsername,User user)  {
        if(newUsername==null)
            return ProfileMenuMessages.EMPTY_FIELDS_ERROR;

        if(!UserInfoOperator.isUsernameFormatValid(newUsername))
          return ProfileMenuMessages.INVALID_USERNAME_ERROR;

        if(newUsername.equals(user.getUsername()))
          return ProfileMenuMessages.UNCHANGED_FIELD_ERROR;

        if(User.getUserByUserName(newUsername)!=null)
          return ProfileMenuMessages.DUPLICATE_USERNAME_ERROR;

        
        user.setUsername(newUsername);
        try {
            UserInfoOperator.storeUserDataInJson(user,"src/main/resources/jsonData/Users.json");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return ProfileMenuMessages.SUCCESFUL_CHANGE_USERNAME;
    }

    public static ProfileMenuMessages changeEmail(String newEmail,User user)  {

        if(newEmail==null)
            return ProfileMenuMessages.EMPTY_FIELDS_ERROR;

        if(!UserInfoOperator.isEmailFormatValid(newEmail))
            return ProfileMenuMessages.INVALID_EMAIL_ERROR;

        if(newEmail.equals(user.getEmail()))
            return ProfileMenuMessages.UNCHANGED_FIELD_ERROR;

        if(User.getUserByEmail(newEmail)!=null)
            return ProfileMenuMessages.DUPLICATE_EMAIL_ERROR;
        
        user.setEmail(newEmail);
        try {
            UserInfoOperator.storeUserDataInJson(user,"src/main/resources/jsonData/Users.json");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return ProfileMenuMessages.SUCCESFUL_CHANGE_EMAIL;
    }

    public static ProfileMenuMessages changeNickname(String newNickname,User user)  {
        if(newNickname==null)
            return ProfileMenuMessages.EMPTY_FIELDS_ERROR;

        if(newNickname.equals(user.getNickName()))
            return ProfileMenuMessages.UNCHANGED_FIELD_ERROR;

        user.setNickName(newNickname);
        try {
            UserInfoOperator.storeUserDataInJson(user,"src/main/resources/jsonData/Users.json");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return ProfileMenuMessages.SUCCESFUL_CHANGE_NICKNAME;
    }

    public static ProfileMenuMessages changeSlogan(String newSlogan,User user)  {
        if(newSlogan==null)
            return ProfileMenuMessages.EMPTY_FIELDS_ERROR;
        
        if(newSlogan.equals(user.getSlogan()))
            return ProfileMenuMessages.UNCHANGED_FIELD_ERROR;
        
        user.setSlogan(newSlogan);
        try {
            UserInfoOperator.storeUserDataInJson(user,"src/main/resources/jsonData/Users.json");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return ProfileMenuMessages.SUCCESFUL_CHANGE_SLOGAN;
    }

    public static ProfileMenuMessages removeSlogan(User user)  {
        user.setSlogan("");
        try {
            UserInfoOperator.storeUserDataInJson(user,"src/main/resources/jsonData/Users.json");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return ProfileMenuMessages.SUCCESFUL_REMOVE_SLOGAN;
    }

    public static String handleProfileFieldsChange(HashMap<String, String> profileArguments,User user){
        String username=profileArguments.get("Username");
        String email=profileArguments.get("Email");
        String nickname=profileArguments.get("Slogan");
        String slogan=profileArguments.get("Slogan");

        String output="";
        
        if(username!=null)
            if(changeUsername(username,user).equals(ProfileMenuMessages.SUCCESFUL_CHANGE_USERNAME))
                output=output+"Username ";

        if(email!=null)
            if(changeEmail(email,user).equals(ProfileMenuMessages.SUCCESFUL_CHANGE_EMAIL))
                output=output+"Email ";

        if(nickname!=null)
            if(changeNickname(nickname,user).equals(ProfileMenuMessages.SUCCESFUL_CHANGE_NICKNAME))
                output=output+"Nickname ";
        
        if(slogan!=null)
            if(changeSlogan(slogan,user).equals(ProfileMenuMessages.SUCCESFUL_CHANGE_SLOGAN))
                output=output+"Slogan";
        return output;
    }

    public static String handleChangePassword(HashMap<String,String> arguments,User user){

        String oldPassword=arguments.get("Old_Password");
        String newPassword=arguments.get("New_Password");
        changePassword(oldPassword, newPassword, newPassword, user);
        return "SUCCESS";
    }
}
