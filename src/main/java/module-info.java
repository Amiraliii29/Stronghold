module Project {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires com.google.gson;
    requires json.simple;
    requires java.transaction.xa;
    requires jdk.security.auth;
    requires com.fasterxml.jackson.databind;

    exports Model;
    opens Model to com.google.gson;

    exports Controller;
    opens Controller to com.google.gson;

    exports View;
    opens View to com.google.gson, javafx.fxml;
    exports View.Controller;
    opens View.Controller to com.google.gson, javafx.fxml;
    exports Main;
    opens Main to com.google.gson, javafx.fxml;
}