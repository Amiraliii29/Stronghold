package Main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import View.SignUpMenu;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8000);
        System.out.println("Server Started!");
        while (true) {
            Socket socket = serverSocket.accept();
            System.out.println("Connected!");

            HandleConnection handleConnection = new HandleConnection(socket);
            handleConnection.start();
        }
    }
}

//public class Main extends Application {
//    public static void main(String[] args) throws FileNotFoundException, IOException, NoSuchAlgorithmException {
//        launch(args);
//    }
//
//    @Override
//    public void start(Stage stage) throws Exception {
//        new SignUpMenu().start(stage);
//    }
//}
