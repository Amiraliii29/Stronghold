package View.Controller;

import Main.Client;
import Main.NormalRequest;
import Main.Request;
import Model.User;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class ChatController {

    public TextField messageText;

    public void enterPublicChat(MouseEvent mouseEvent) {
    }

    public void enterPrivateChat(MouseEvent mouseEvent) {
    }

    public void enterRoomChat(MouseEvent mouseEvent) {
    }

    public void sendGlobalMessage(MouseEvent mouseEvent) throws IOException {
        String message = messageText.getText();
        Request request = new Request(NormalRequest.SEND_GLOBAL_MESSAGE);
        request.argument.put("message" , message);
        request.argument.put("userName" , User.getCurrentUser().getUsername());
        request.argument.put("avatar" , User.getCurrentUser().getAvatarFileName());
        Client.client.getDataOutputStream().writeUTF(request.toJson());
    }
}
