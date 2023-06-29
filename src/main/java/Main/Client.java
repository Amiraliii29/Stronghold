package Main;

import Model.User;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import com.google.gson.Gson;

import Controller.ProfileMenuController;
import Controller.SignUpMenuController;

public class Client extends Thread{
    private final String token;
    private final Socket socket;
    private final DataInputStream dataInputStream;
    private final DataOutputStream dataOutputStream;
    private User user;
    private UserDataBase userDataBase;


    public Client(Socket socket) throws IOException {
        this.socket = socket;
        dataInputStream = new DataInputStream(socket.getInputStream());
        dataOutputStream = new DataOutputStream(socket.getOutputStream());

        token = encodeToken(generateToken());
    }


    @Override
    public void run() {
        try {
            dataOutputStream.writeUTF(token);

            String json = dataInputStream.readUTF();
            Request request = Request.fromJson(json);

            while (!request.verify(token)) {
                json = dataInputStream.readUTF();
                request = Request.fromJson(json);
            }

            while (!request.normalRequest.equals(NormalRequest.CLOSE)) {

                requestHandler(request);

                json = dataInputStream.readUTF();
                request = Request.fromJson(json);

                while (!request.verify(token)) {
                    json = dataInputStream.readUTF();
                    request = Request.fromJson(json);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private void requestHandler(Request request) {

        String result="";

        if(request.normalRequest.equals(NormalRequest.SIGNUP))
            result=SignUpMenuController.handleSignupRequest(request.argument,this);

        else if(request.normalRequest.equals(NormalRequest.CHANGE_PROFILE_FIELDS))
            result=ProfileMenuController.handleProfileFieldsChange(request.argument,user);
        
        else if(request.normalRequest.equals(NormalRequest.REMOVE_SLOGAN))
            result=ProfileMenuController.removeSlogan(user).toString();
        
        else if(request.normalRequest.equals(NormalRequest.CHANGE_PASSWORD))
            result=ProfileMenuController.handleChangePassword(request.argument, user);
        
        else if(request.normalRequest.equals(NormalRequest.GET_USER_BY_USERNAME))
            result=new Gson().toJson(User.getUserByUserName(request.argument.get("Username")));
        //TODO: FILL THE REST;




        try {
            dataOutputStream.writeUTF(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public Socket getSocket() {
        return socket;
    }

    public DataInputStream getDataInputStream() {
        return dataInputStream;
    }

    public DataOutputStream getDataOutputStream() {
        return dataOutputStream;
    }

    public User getUser() {
        return user;
    }

    public UserDataBase getUserDataBase() {
        return userDataBase;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setUserDataBase(UserDataBase userDataBase) {
        this.userDataBase = userDataBase;
    }


    private static String generateToken() {
        String alphanumeric = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        String specialChars = "!@#$%^&*()_+";
        String symbols = "~`-={}[]\\|;:'\",.<>?/";

        String combinedChars = alphanumeric + specialChars + symbols;

        SecureRandom random = new SecureRandom();

        StringBuilder password = new StringBuilder();
        for (int i = 0; i < 12; i++) {
            int index = random.nextInt(combinedChars.length());
            password.append(combinedChars.charAt(index));
        }

        return password.toString();
    }

    private static String encodeToken(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = digest.digest(password.getBytes());

            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }

            return sb.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
