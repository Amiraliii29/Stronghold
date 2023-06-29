package Main;

import Controller.JsonConverter;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) throws IOException {
        load();

        ServerSocket serverSocket = new ServerSocket(8000);
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
    }
}
