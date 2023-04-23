package Controller;

import java.security.NoSuchAlgorithmException;

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

    public static ProfileMenuMessages changePassword(String oldPassword,String newPassword) throws NoSuchAlgorithmException {

        if(oldPassword==null || newPassword ==null)
            return ProfileMenuMessages.EMPTY_FIELDS_ERROR;
            
        oldPassword=UserInfoOperator.encodeStringToSha256(newPassword);

        if(!oldPassword.equals(currentUser.getPassword()))
            return ProfileMenuMessages.INCORRECT_PASSWORD_ERROR;

        if(!UserInfoOperator.isPasswordStrong(newPassword))
            return ProfileMenuMessages.WEAK_PASSWORD_ERROR;
        
        newPassword=UserInfoOperator.encodeStringToSha256(newPassword);

        if(newPassword.equals(oldPassword))
            return ProfileMenuMessages.UNCHANGED_FIELD_ERROR;
        
        String passwordConfirmation=ProfileMenu.getPasswordConfirmationFromUser();
        passwordConfirmation=UserInfoOperator.encodeStringToSha256(passwordConfirmation);

        if(!passwordConfirmation.equals(newPassword))
            return ProfileMenuMessages.INCORRECT_PASSWORD_VERIFICATION_ERROR;
        
        currentUser.setPassword(newPassword);
        UserInfoOperator.storeUserDataInJson(currentUser,"src/main/java/jsonData/Users.json");
        return ProfileMenuMessages.SUCCESFUL_CHANGE_PASSWORD;
            
    }

    public static ProfileMenuMessages changeUsername(String newUsername) throws NoSuchAlgorithmException {
        if(newUsername==null)
            return ProfileMenuMessages.EMPTY_FIELDS_ERROR;

        if(!UserInfoOperator.isUsernameFormatValid(newUsername))
          return ProfileMenuMessages.INVALID_USERNAME_ERROR;

        if(newUsername.equals(currentUser.getUsername()))
          return ProfileMenuMessages.UNCHANGED_FIELD_ERROR;

        if(User.getUserByUserName(newUsername)!=null)
          return ProfileMenuMessages.DUPLICATE_USERNAME_ERROR;

        
        currentUser.setUsername(newUsername);
        UserInfoOperator.storeUserDataInJson(currentUser,"src/main/java/jsonData/Users.json");
        return ProfileMenuMessages.SUCCESFUL_CHANGE_USERNAME;
    }

    public static ProfileMenuMessages changeEmail(String newEmail) throws NoSuchAlgorithmException {

        if(newEmail==null)
            return ProfileMenuMessages.EMPTY_FIELDS_ERROR;

        if(!UserInfoOperator.isEmailFormatValid(newEmail))
            return ProfileMenuMessages.INVALID_EMAIL_ERROR;

        if(newEmail.equals(currentUser.getEmail()))
            return ProfileMenuMessages.UNCHANGED_FIELD_ERROR;

        if(User.getUserByEmail(newEmail)!=null)
            return ProfileMenuMessages.DUPLICATE_EMAIL_ERROR;
        
        currentUser.setEmail(newEmail);
        UserInfoOperator.storeUserDataInJson(currentUser,"src/main/java/jsonData/Users.json");
        return ProfileMenuMessages.SUCCESFUL_CHANGE_EMAIL;
    }

    public static ProfileMenuMessages changeNickname(String newNickname) throws NoSuchAlgorithmException {
        if(newNickname==null)
            return ProfileMenuMessages.EMPTY_FIELDS_ERROR;

        if(newNickname.equals(currentUser.getNickName()))
            return ProfileMenuMessages.UNCHANGED_FIELD_ERROR;

        currentUser.setNickName(newNickname);
        UserInfoOperator.storeUserDataInJson(currentUser,"src/main/java/jsonData/Users.json");
        return ProfileMenuMessages.SUCCESFUL_CHANGE_NICKNAME;
    }

    public static ProfileMenuMessages changeSlogan(String newSlogan) throws NoSuchAlgorithmException {
        if(newSlogan==null)
            return ProfileMenuMessages.EMPTY_FIELDS_ERROR;
        
        if(newSlogan.equals(currentUser.getSlogan()))
            return ProfileMenuMessages.UNCHANGED_FIELD_ERROR;
        
        currentUser.setSlogan(newSlogan);
        UserInfoOperator.storeUserDataInJson(currentUser,"src/main/java/jsonData/Users.json");
        return ProfileMenuMessages.SUCCESFUL_CHANGE_SLOGAN;
    }

    public static ProfileMenuMessages removeSlogan() throws NoSuchAlgorithmException {
        currentUser.setSlogan("");
        UserInfoOperator.storeUserDataInJson(currentUser,"src/main/java/jsonData/Users.json");
        return ProfileMenuMessages.SUCCESFUL_REMOVE_SLOGAN;
    }
}
