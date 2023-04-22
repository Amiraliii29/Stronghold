package Controller;



import java.util.List;


import java.io.*;

import org.*;
import org.json.simple.*;  
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


import Model.DataBase;
import Model.User;

public class JsonConverter {


    public static void putUserDataInFile(String username, String password,String email, String slogan,
         String securityQ, String nickname,String rank,String highscore,boolean stayLoggedIn ,String dirFromSrc) {

            JSONObject newUser= new JSONObject();
            String loginValue;
            if (stayLoggedIn) loginValue="true";
            else loginValue="false";

            newUser.put("username", username);
            newUser.put("password", password);
            newUser.put("email", email);
            newUser.put("slogan", slogan);
            newUser.put("securityQ", securityQ);
            newUser.put("nickname", nickname);
            newUser.put("rank", rank);
            newUser.put("highscore", highscore);
            newUser.put("stayLoggedIn", loginValue);


            

            JSONArray userData=getUsersDataInJson(dirFromSrc);
            int userIndexInData=getUserIndexInJsonArray(username, dirFromSrc);
            
            
            if(userIndexInData==-1)
                userData.add(newUser);
            else{
                userData.remove(userIndexInData);
                userData.add(newUser);
            }

            try{
                File file=new File(dirFromSrc);
                FileWriter fileWriter = new FileWriter(file);
                fileWriter.write(userData.toJSONString());  
                fileWriter.flush();  
                fileWriter.close();  
            } catch ( IOException e) {  
                e.printStackTrace();  
            }
    }

    public static void fillFormerUsersDatabase(String dirFromSrc){
        JSONArray usersJsonArray=getUsersDataInJson(dirFromSrc);

        for (int i = 0; i < usersJsonArray.size(); i++) {
            User userUnderRestoration=new User(null, null, null, null, null);
            fillUserInfo(userUnderRestoration, i, usersJsonArray);
            DataBase.addUser(userUnderRestoration);
            if(userUnderRestoration.getStayLoggedIn())
             DataBase.setCurrentUser(userUnderRestoration);
        }

    }
   
    private static void fillUserInfo(User user,int userIndex, JSONArray jsonData){
        JSONObject UserInJson=(JSONObject) jsonData.get(userIndex);
        user.setNickName(getJsonKeyValue("nickname", UserInJson));
        user.setUsername(getJsonKeyValue("username", UserInJson));
        user.setEmail(getJsonKeyValue("email", UserInJson));
        user.setSlogan(getJsonKeyValue("slogan", UserInJson));
        user.setSecurityQuestion(getJsonKeyValue("securityQ", UserInJson));
        String hashedPassword=getJsonKeyValue("password", UserInJson);
        user.setPassword(hashedPassword);

        String loginStatInString=getJsonKeyValue("stayLoggedIn", UserInJson);
        if(loginStatInString.equals("true"))
         user.setStayLoggedIn(true);
        else user.setStayLoggedIn(false);
    }

    private static String getJsonKeyValue(String key, JSONObject jsonObject){
        if(jsonObject.get(key)!=null)
            return jsonObject.get(key).toString();
        else return null;
    }

    private static JSONArray getUsersDataInJson(String dirFromSrc){
        JSONArray formerData=new JSONArray();
        try {  
            
            JSONParser jsonParser = new JSONParser();
            Object objjj = jsonParser.parse(new FileReader(dirFromSrc));
            formerData=(JSONArray) objjj;

        } catch ( ParseException | IOException e) {  
            e.printStackTrace();  
        }
        return formerData;
    }

    private static int getUserIndexInJsonArray(String username, String dirFromSrc){
        JSONArray jsonDataArray=getUsersDataInJson(dirFromSrc);
        JSONObject jsonUser;

        for (int i = 0; i < jsonDataArray.size(); i++) {
            jsonUser= (JSONObject)jsonDataArray.get(i);
            if(jsonUser.get("username").toString().equals(username))
                return i;
        }
        return -1;
    }

}

