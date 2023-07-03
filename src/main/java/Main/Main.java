package Main;

import Controller.JsonConverter;
import Controller.UserInfoOperator;
import Model.Buildings.Building;
import Model.Resource;
import Model.Units.Unit;
import Model.User;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;

public class Main {
    public static void main(String[] args) throws IOException {
        Resource.load();
        Unit.load();
        Building.load();
        load();

        ServerSocket serverSocket = new ServerSocket(8080);
        System.out.println("Server Started!");
        while (true) {
            Socket socket = serverSocket.accept();
            System.out.println("Connected!");

            Client client = new Client(socket);
            client.start();
        }
    }

    private static void load() {
        JsonConverter.fillFormerUsersDatabase("src/main/resources/jsonData/Users.json");
        // try {
        //     UserInfoOperator.updateAllUsersJsonData("src/main/resources/jsonData/Users.json");
        // } catch (NoSuchAlgorithmException e) {
        //     e.printStackTrace();
        // }
        // System.out.println(User.getUsers().get(0).getFriends().get(0).getUsername());

    }
}
