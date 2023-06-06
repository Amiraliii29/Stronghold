package Controller;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Comparator;

import Model.User;

public class UserComparator implements Comparator {

    public int compare(Object o1,Object o2){  
        User u1=(User)o1;  
        User u2=(User)o2;  
        return compareUsers(u2, u1);  
    } 
    
    private int compareUsers(User user1, User user2){
        if(user1.getHighScore()>user2.getHighScore()) return 1;
        else return -1;
    }

    public static void updateUsersRank(ArrayList<User> sortedUsers){
        int rank=1;
        for (User user : sortedUsers) {
            user.setRank(rank);
            try {
                UserInfoOperator.storeUserDataInJson(user, "src/main/resources/jsonData/Users.json");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            rank++;
        }
    }
}
