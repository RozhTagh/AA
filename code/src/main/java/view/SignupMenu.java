package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.Database;

import java.io.IOException;

public class SignupMenu extends Application {

    public static Stage stage;

    @Override
    public void start(Stage stage) throws IOException {
        Database.loadUsers();
        SignupMenu.stage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(SignupMenu.class.getResource("signupMenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 320);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.getIcons().add(new Image(SignupMenu.class.getResource("/images/aaIcon.png").toExternalForm()));
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(SignupMenu.class, args);
    }
}