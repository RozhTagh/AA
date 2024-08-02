package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LoginMenu extends Application {

    public static Stage stage;

    @Override
    public void start(Stage stage) throws Exception {
        LoginMenu.stage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(LoginMenu.class.getResource("loginMenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 320);
        stage.setTitle("Login Menu");
        stage.setScene(scene);
        stage.show();
    }
}
