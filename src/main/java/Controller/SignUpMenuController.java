package Controller;


import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import java.util.Timer;
import Main.Client;
import Model.User;
import View.Enums.Messages.SignUpMenuMessages;

public class SignUpMenuController {

    private static int failedAttempts;
    private static int failurePenalty;

    public static SignUpMenuMessages createUserController(String userName, String passWord,
                                                          String nickName, String passWordConfirmation, String email, String slogan,String securityAnswer,Client client) throws NoSuchAlgorithmException {
        email = email.toLowerCase();

        if (User.getUserByUserName(userName) != null)
            return SignUpMenuMessages.DUPLICATE_USERNAME_SIGNUP_ERROR;

        if (!UserInfoOperator.isUsernameFormatValid(userName))
            return SignUpMenuMessages.INVALID_USERNAME_SIGNUP_ERROR;

        if (User.getUserByEmail(email) != null)
            return SignUpMenuMessages.DUPLICATE_EMAIL_SIGNUP_ERROR;

        if (!UserInfoOperator.isPasswordStrong(passWord))
            return SignUpMenuMessages.WEAK_PASSWORD_ERROR;

        if (!UserInfoOperator.isPasswordRepeatedCorrectly(passWordConfirmation, passWord))
            return SignUpMenuMessages.WRONG_PASSWORD_REPEAT_SIGNUP_ERROR;

        if (!UserInfoOperator.isEmailFormatValid(email))
            return SignUpMenuMessages.INVALID_EMAIL_SIGNUP_ERROR;


        passWord = UserInfoOperator.encodeStringToSha256(passWord);
        User newUser = new User(userName, passWord, nickName, email, slogan);
        newUser.setStayLoggedIn(false);
        newUser.setSecurityQuestion(securityAnswer);
        UserInfoOperator.storeUserDataInJson(newUser, "src/main/resources/jsonData/Users.json");
        User.addUser(newUser);
        client.setUser(newUser);
        return SignUpMenuMessages.SUCCESFUL_SIGNUP_STEP;
    }

    public static SignUpMenuMessages userLoginController(String userName, String passWord, Boolean stayLoggedInoption) throws NoSuchAlgorithmException {
        if (userName == null || passWord == null)
            return SignUpMenuMessages.LOGIN_EMPTY_FIELDS_ERROR;

        passWord = UserInfoOperator.encodeStringToSha256(passWord);
        User targetUser = User.getUserByUserName(userName);

        if (targetUser == null)
            return SignUpMenuMessages.LOGIN_INVALID_USERNAME_ERROR;

        if (!passWord.equals(targetUser.getPassword())){
            setNewPenalty();
            return SignUpMenuMessages.LOGIN_INCORRECT_PASSWORD_ERROR;
        }


        if (stayLoggedInoption == true) {
            targetUser.setStayLoggedIn(true);
            UserInfoOperator.storeUserDataInJson(targetUser, "src/main/resources/jsonData/Users.json");
        }

        // User.setCurrentUser(targetUser);
        return SignUpMenuMessages.SUCCESFUL_LOGIN;
    }

    public static void setNewPenalty() {
        failedAttempts++;
        failurePenalty = failedAttempts * 5;
    }

    public static SignUpMenuMessages UserSecurityAnswerController(String securityAnswerComponents, User user) {

        String pickedQuestion = Orders.findFlagOption("-q", securityAnswerComponents);
        String answer = Orders.findFlagOption("-a", securityAnswerComponents);
        String answerRepetition = Orders.findWordAfterFlagSequence("-a", securityAnswerComponents);

        if (answer == null || pickedQuestion == null || answerRepetition == null)
            return SignUpMenuMessages.EMPTY_FIELDS_SECURITY_ERROR;

        if (!answer.equals(answerRepetition))
            return SignUpMenuMessages.WRONG_SECURITY_REPEAT_ERROR;

        user.setSecurityQuestion(answer);

        return SignUpMenuMessages.SUCCESFUL_SECURITY_ANSWER;
    }

    public static int getPenalty() {
        return failurePenalty;
    }

    public static void decreasePenalty() {
        failurePenalty--;
    }

    public static String handleSignupRequest(HashMap<String,String> signupArguments,Client client){
        String username=signupArguments.get("Username");
        String password=signupArguments.get("Password");
        String email=signupArguments.get("Email");
        String slogan=signupArguments.get("Slogan");
        String nickname=signupArguments.get("Nickname");
        String securityAnswer=signupArguments.get("Security");
        
        if(User.getUserByUserName(username) != null)
            return "DUPLICATE_USERNAME";
        
        if(User.getUserByEmail(email) != null)
            return "DUPLICATE_EMAIL";

        try {
            
            createUserController(username, password, nickname, password, email, slogan, securityAnswer,client);
            Client.updateAllClientsData();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "SUCCESS";
    }
}
