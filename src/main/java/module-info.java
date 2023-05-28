module Project {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires com.google.gson;

    exports Model;
    opens Model to com.google.gson;

    exports View;
    opens View to javafx.fxml;
}