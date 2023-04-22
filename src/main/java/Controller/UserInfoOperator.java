package Controller;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;

import Model.DataBase;
import Model.User;

public class UserInfoOperator {
    
    private static final ArrayList<String> defaultSlogans = new ArrayList<String>();
    private static final ArrayList<String> securityQuestions= new ArrayList<String>();
    private static SecureRandom randomGenerator=new SecureRandom();


    
    
    static{
        setDefaultSlogans();
        setSecurityQuestions();
    }


    
        
    public static String getRandomSlogan(){
        int randomNumBound=defaultSlogans.size();
        int randomSelectedIndex=randomGenerator.nextInt(randomNumBound);

        return defaultSlogans.get(randomSelectedIndex);
    }

    public static String getSecurityQuestionByIndex(int indexFromOne){
        if(indexFromOne>securityQuestions.size()) return null;
        return securityQuestions.get(indexFromOne-1);
    }

    public static void storeUserDataInJson(User user,String dirFromSrc) throws NoSuchAlgorithmException{
        String username=user.getUsername();
        String password=user.getPassword();
        String email=user.getEmail();
        String slogan=user.getSlogan();
        String securityQuestion=user.getSecurityQuestion();
        String nickname=user.getNickName();
        String rank=Integer.toString(user.getRank());
        String highscore=Integer.toString(user.getHighscore());
        boolean loginStatus=user.getStayLoggedIn();

        JsonConverter.putUserDataInFile(username, password, email, slogan, 
                        securityQuestion, nickname,rank,highscore,loginStatus ,dirFromSrc);
    }

    public static void updateAllUsersJsonData(String dirFromSrc) throws NoSuchAlgorithmException{
        for (User user : DataBase.getUsers()) {
            storeUserDataInJson(user, dirFromSrc);
        }
    }

    public static String generateRandomPassword(){
        int size=6+randomGenerator.nextInt(4);
        String output="";

        for (int i = 0; i < size; i++) {
         switch (i) {
            case 0:
                output=output.concat(""+generateBigAlphabet());
                break;
            case 1:
                output=output.concat(""+generateSmallAlphabet());
                break;
            case 2:
                output=output.concat(""+generateDecimalNumberInChar());
                break;
            case 3:
                output=output.concat(""+generateSpecialChar());
                break;
            default:
                output=output.concat(""+generateRandomChar());
                break;
         }   
        }
        return output;
    }

    public static String encodeStringToSha256(String textToEncode) throws NoSuchAlgorithmException{
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] byteOfTextToHash = textToEncode.getBytes(StandardCharsets.UTF_8);
        byte[] hashedByetArray = digest.digest(byteOfTextToHash);
        String encoded = Base64.getEncoder().encodeToString(hashedByetArray);
        return encoded;
    }

    public static String addRandomizationToString(String targetString){
        String targetConcat="";
        int randomizationSize=2+randomGenerator.nextInt(2);

        for (int i = 0; i < randomizationSize; i++) {
            if(i%2==1) targetConcat=targetConcat.concat(""+generateDecimalNumberInChar());
            else targetConcat=targetConcat.concat(""+generateSmallAlphabet());
        }
        targetString=targetString.concat(targetConcat);
        return targetString;
    }

    private static char generateSmallAlphabet(){
        //97 ta 122
        int randomValInInt=randomGenerator.nextInt(26);
        char randomChar=(char) (randomValInInt+97);

        return randomChar;
    }

    private static char generateBigAlphabet(){

        int randomValInInt=randomGenerator.nextInt(26);
        char randomChar=(char) (randomValInInt+65);

        return randomChar;
    }

    private static char generateDecimalNumberInChar(){

        int randomValInInt=randomGenerator.nextInt(10);
        char randomChar=(char) (randomValInInt+48);
        
        return randomChar;
    }

    private static char generateSpecialChar(){
        int randomValInInt=randomGenerator.nextInt(15);
        char randomChar=(char) (randomValInInt+33);
        
        return randomChar;
    }

    private static char generateRandomChar(){
        int randomFormat=randomGenerator.nextInt(4);
        char randomChar=(char)0;

        switch (randomFormat) {
            case 0:
                randomChar=generateBigAlphabet();
                break;
        
            case 1:
                randomChar=generateSmallAlphabet();
                break;
            case 2:
                randomChar=generateSpecialChar();
                break;
            case 3:
                randomChar=generateDecimalNumberInChar();
                break;
            default:
                break;
        }

        return randomChar;
    }

    private static void setDefaultSlogans(){
        defaultSlogans.add("Ours is the fury");
        defaultSlogans.add("We do not sow");
        defaultSlogans.add("Hear me roar");
        defaultSlogans.add("We live to die another day");
        defaultSlogans.add("How many you got? just bring em all, I miss the 'get em gone'");
        defaultSlogans.add("These times murder pays like a part time job");
        defaultSlogans.add("yor woife is in me dms talking hey baby");
    }

    private static void setSecurityQuestions(){
        securityQuestions.add("what is your favorite book name?");
        securityQuestions.add("what is your favorite restaurant name?");
        securityQuestions.add("who is your favorite character from tv shows?");
        securityQuestions.add("what is your favorite video game?");
    }


}