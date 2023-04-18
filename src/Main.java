import Controller.Orders;
import Controller.SignUpMenuController;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        String lol="lololol";
        String input="user create -u <username> -p <password> \"<password confirmation>\" -email <email> -s <slogan>";
        System.out.println(Orders.getRandomSlogan());
    }
}