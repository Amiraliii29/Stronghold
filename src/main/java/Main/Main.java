package Main;

import Controller.JsonConverter;
import Model.Buildings.Building;
import Model.Resource;
import Model.Units.Unit;
import Model.User;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

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
        User.handleGetUsersRequest();
    }
}
