package Controller;


import java.security.NoSuchAlgorithmException;
import java.util.Timer;
import java.util.regex.Matcher;

import Model.DataBase;
import Model.User;
import View.SignUpMenu;
import View.Enums.Messages.SignUpMenuMessages;

public class SignUpMenuController {

    private static int failedAttempts;
    private static int failurePenalty;

    //username password email slogan
    public static SignUpMenuMessages createUserController(String userName , String passWord,
                            String nickName, String passWordConfirmation ,String email , String slogan) throws NoSuchAlgorithmException{
        email=email.toLowerCase();

        if(DataBase.getUserByUserName(userName) != null)
         return SignUpMenuMessages.DUPLICATE_USERNAME_SIGNUP_ERROR;

        if(!isUsernameFormatValid(userName))
         return SignUpMenuMessages.INVALID_USERNAME_SIGNUP_ERROR;

        if(DataBase.getUserByEmail(email) != null)
         return SignUpMenuMessages.DUPLICATE_EMAIL_SIGNUP_ERROR;

        if(!isPasswordStrong(passWord)) 
         return SignUpMenuMessages.WEAK_PASSWORD_ERROR;
        
        if(!isPasswordRepeatedCorrectly(passWordConfirmation, passWord))
         return SignUpMenuMessages.WRONG_PASSWORD_REPEAT_SIGNUP_ERROR;

        if(!isEmailFormatValid(email))
         return SignUpMenuMessages.INVALID_EMAIL_SIGNUP_ERROR;
        

        passWord=UserInfoOperator.encodeStringToSha256(passWord);
        User newUser=new User(userName, passWord, nickName,email, slogan);
        newUser.setStayLoggedIn(false);

        SignUpMenu.chooseSecurityQuestionForUser(newUser);

        UserInfoOperator.storeUserDataInJson(newUser, "src/main/java/jsonData/Users.json");
        DataBase.addUser(newUser);
        return SignUpMenuMessages.SUCCESFUL_SIGNUP_STEP; 
    }

    public static SignUpMenuMessages userLoginController(String userName , String passWord , Boolean stayLoggedInoption) throws NoSuchAlgorithmException{
        if(userName==null || passWord==null)
         return SignUpMenuMessages.LOGIN_EMPTY_FIELDS_ERROR;
        
        passWord=UserInfoOperator.encodeStringToSha256(passWord);
        User targetUser=DataBase.getUserByUserName(userName);
        
        if(targetUser==null) 
         return SignUpMenuMessages.LOGIN_INVALID_USERNAME_ERROR;
        
        if(!passWord.equals(targetUser.getPassword()))
          return SignUpMenuMessages.LOGIN_INCORRECT_PASSWORD_ERROR;


        if(stayLoggedInoption!= null){
            targetUser.setStayLoggedIn(true);
            UserInfoOperator.storeUserDataInJson(targetUser,"src/main/java/jsonData/Users.json" );
        }

        DataBase.setCurrentUser(targetUser);
        return SignUpMenuMessages.SUCCESFUL_LOGIN;
    }

    public static SignUpMenuMessages forgotMyPassWordController(String email, String securityAnswer) throws NoSuchAlgorithmException{
        User targetUser=DataBase.getUserByEmail(email);
        if(targetUser==null)
          return SignUpMenuMessages.INVALID_EMAIL_FORGET_PASSWORD_ERROR;
        
        if(!targetUser.getSecurityQuestion().equals(securityAnswer))
            return SignUpMenuMessages.INCORRECT_SECURITY_FORGET_PASSWORD_ERROR;
        
        String newPassword=SignUpMenu.getNewPasswordFromUser();
        newPassword=UserInfoOperator.encodeStringToSha256(newPassword);
        
        targetUser.setPassword(newPassword);
        UserInfoOperator.storeUserDataInJson(targetUser, "src/main/java/jsonData/Users.json");
        return SignUpMenuMessages.SUCCESFUL_FORGET_PASSWORD;
    }

    public static SignUpMenuMessages handleNewPasswordEntry(String password, String passwordRepeat){

        if(password==null)
         return SignUpMenuMessages.EMPTY_FIELDS_FORGET_PASSWORD_ERROR;         

        if(password.equals("random")){
            password=UserInfoOperator.generateRandomPassword();
            passwordRepeat=SignUpMenu.confirmRandomPassword(password);
        }

        if( passwordRepeat ==null)
          return SignUpMenuMessages.EMPTY_FIELDS_FORGET_PASSWORD_ERROR;         

        if(!isPasswordRepeatedCorrectly(password, passwordRepeat))
          return SignUpMenuMessages.INCORRECT_REPEAT_FORGET_PASSWORD_ERROR;

        if(!isPasswordStrong(password))
          return SignUpMenuMessages.WEAK_PASSWORD_ERROR;
        

        return SignUpMenuMessages.SUCCESFUL_FORGET_PASSWORD;
    }

    private static boolean isEmailFormatValid(String inputEmail){
        String spaceCheckerRegex="\\s";
        Matcher spaceCheckerMatcher=Orders.createMatcher(spaceCheckerRegex, inputEmail);
        if(spaceCheckerMatcher.find()) return false;

        String correctFormatRegex="(?<emailBody>[^\\@]+)\\@(?<firstEmailHead>[^\\.]+)\\.(?<secondEmailHead>.+)";
        Matcher correctFormatMatcher=Orders.createMatcher(correctFormatRegex, inputEmail);
        if(!correctFormatMatcher.matches()) return false;

        return true;
    }

    private static boolean isPasswordStrong(String password){
        if(password.length()<6) return false;

        String bigAlphabetRegex="[A-Z]";
        Matcher bigAlphabetMatcher=Orders.createMatcher(bigAlphabetRegex, password);
        if(!bigAlphabetMatcher.find()) return false;

        String smallAlphabetRegex="[a-z]";
        Matcher smallAlphabetMatcher=Orders.createMatcher(smallAlphabetRegex, password);
        if(!smallAlphabetMatcher.find()) return false;

        String numberRegex="[0-9]";
        Matcher numberMatcher=Orders.createMatcher(numberRegex, password);
        if(!numberMatcher.find()) return false;

        String specialCharRegex="[^a-zA-Z0-9]";
        Matcher specialCharMatcher=Orders.createMatcher(specialCharRegex, password);
        if(!specialCharMatcher.find()) return false;

        return true;
    }

    private static boolean isUsernameFormatValid(String inputUsername){
        String invalidCharRegex="[^A-Za-z0-9\\_]";
        Matcher invalidCharMatcher=Orders.createMatcher(invalidCharRegex, inputUsername);
        if(invalidCharMatcher.find()) return false;

        return true;
    }

    private static boolean isPasswordRepeatedCorrectly(String password, String passwordRepeat){
        if(password.equals(passwordRepeat))
            return true;
        else return false;
    }

    public static void setNewPenalty(){
        failedAttempts++;
        failurePenalty=failedAttempts*5;
        adjustPenaltyByTime();
    }

    public static void adjustPenaltyByTime(){
        PenaltyTimer.setProcessDuration(getPenalty());
        PenaltyTimer.setProcessStartingTime();

        Timer timerObj = new Timer(true);
        timerObj.scheduleAtFixedRate(new PenaltyTimer(), 0, 1000);
    }

    public static SignUpMenuMessages runControllerSignupFunction(String signupComponentsInput) throws NoSuchAlgorithmException{
        
        //NOTE: if(!Orders.isOrderNotJunky(signupComponentsInput)) System.out.println("error");

        String username=Orders.findFlagOption("-u", signupComponentsInput);
        String password=Orders.findFlagOption("-p", signupComponentsInput);
        String passwordConfirmation=Orders.findWordAfterFlagSequence("-p", signupComponentsInput);
        String nickName=Orders.findFlagOption("-n", signupComponentsInput);
        String email=Orders.findFlagOption("--email", signupComponentsInput);
        String slogan=Orders.findFlagOption("-s", signupComponentsInput);
    
        if(username==null || password==null || email==null|| passwordConfirmation==null || nickName==null)
            return SignUpMenuMessages.EMPTY_FIELDS_SIGNUP_ERROR;
        

        if(password.equals("random")){
            password=UserInfoOperator.generateRandomPassword();
            passwordConfirmation=SignUpMenu.confirmRandomPassword(password);
        }
        if(slogan.equals("random")){
            slogan=UserInfoOperator.getRandomSlogan();
            SignUpMenu.displayRandomSlogan(slogan);
        }

        SignUpMenuMessages message= SignUpMenuController.createUserController(username,
                 password, nickName, passwordConfirmation, email, slogan);

            if(message.equals(SignUpMenuMessages.DUPLICATE_USERNAME_SIGNUP_ERROR)){
               username=SignUpMenu.suggestNewUsername(username);
                if(username != null)
                return createUserController(username, password, nickName,
                                passwordConfirmation, email, slogan);
            }
        return message;  
    }

    public static SignUpMenuMessages UserSecurityAnswerController(String securityAnswerComponents, User user){

        String pickedQuestion=Orders.findFlagOption("-q", securityAnswerComponents);
        String answer=Orders.findFlagOption("-a", securityAnswerComponents);
        String answerRepetition=Orders.findWordAfterFlagSequence("-a", securityAnswerComponents);
        
        if(answer==null || pickedQuestion==null || answerRepetition==null)
            return SignUpMenuMessages.EMPTY_FIELDS_SECURITY_ERROR;
        
        if(!answer.equals(answerRepetition))
            return SignUpMenuMessages.WRONG_SECURITY_REPEAT_ERROR;
        
        user.setSecurityQuestion(answer);

        return SignUpMenuMessages.SUCCESFUL_SECURITY_ANSWER;
    }

    public static int getPenalty(){
        return failurePenalty;
    }

    public static void decreasePenalty(){
        failurePenalty--;
    }
}