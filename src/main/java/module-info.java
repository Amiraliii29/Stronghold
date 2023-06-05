module Project {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires com.google.gson;
    requires json.simple;
    requires java.transaction.xa;
    requires jdk.security.auth;

    exports Model;
    opens Model to com.google.gson;

    exports Model.Buildings;
    opens Model.Buildings to com.google.gson;

    exports Model.Units;
    opens Model.Units to com.google.gson;

    exports Controller;
    opens Controller to com.google.gson;

    exports View;
    opens View to javafx.fxml;
}