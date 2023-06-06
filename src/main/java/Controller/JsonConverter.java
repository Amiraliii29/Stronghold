package Controller;

import java.io.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.gson.Gson;

import Model.User;

public class JsonConverter {
    public static void putUserDataInFile(User user,String dirFromSrc) {

            String userInJsonString= new Gson().toJson(user);
            
            try {
                removeUsernameJsonData(user, dirFromSrc);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            JSONArray userData=getUsersDataInJson(dirFromSrc);
                userData.add(userInJsonString);

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

    public static void removeUsernameJsonData(User user, String dirFromSrc) throws ParseException{
        JSONArray userData=getUsersDataInJson(dirFromSrc);
        int index=getUserIndexInJsonArray(user,dirFromSrc);
        userData.remove(index);
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
        User userUnderRestoration;
        Gson gson=new Gson();

        for (int i = 0; i < usersJsonArray.size(); i++) {
            userUnderRestoration= gson.fromJson(usersJsonArray.get(i).toString(), User.class);
            User.getUsers().add(userUnderRestoration);
            if(userUnderRestoration.getStayLoggedIn())
                User.setCurrentUser(userUnderRestoration);
        }
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

    private static int getUserIndexInJsonArray(User user, String dirFromSrc){
        JSONArray jsonDataArray=getUsersDataInJson(dirFromSrc);
        User userUnderRestoration;
        Gson gson=new Gson();

        for (int i = 0; i < jsonDataArray.size(); i++) {
            userUnderRestoration=gson.fromJson(jsonDataArray.get(i).toString(), User.class);
            if(userUnderRestoration.getUsername().equals(user.getUsername()) || userUnderRestoration.getEmail().equals(user.getEmail()) )
                return i;
        }
        return -1;
    }

}

