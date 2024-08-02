module com.example.aa {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires com.google.gson;

    opens view to javafx.fxml;
    exports view;
    exports model;
    exports controller;
    opens model to com.google.gson;
}