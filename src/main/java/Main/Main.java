package Main;

import Controller.JsonConverter;
import Model.Buildings.Building;
import Model.Resource;
import Model.Units.Unit;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) throws IOException {
        
//          String database="student.mdb";//Here database exists in the current directory  
  
//    String url="jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};  
//                     DBQ=" + database + ";DriverID=22;READONLY=true";  
  
//    Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");  
//    Connection c=DriverManager.getConnection(url);  
//    Statement st=c.createStatement();  
//    ResultSet rs=st.executeQuery("select * from login");  
    
//    while(rs.next()){  
//     System.out.println(rs.getString(1));  

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
