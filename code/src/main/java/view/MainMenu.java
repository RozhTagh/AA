package view;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import model.AA;
import model.Database;

import java.io.IOException;

public class MainMenu extends Application {
    public static Stage stage;
    @FXML
    private Circle avatarPhoto;

    @Override
    public void start(Stage stage) throws Exception {
        MainMenu.stage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(MainMenu.class.getResource("mainMenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 370, 440);
        stage.setScene(scene);
        stage.setTitle("Main Menu");
        stage.show();
    }

    @FXML
    public void initialize() {
        String path;
        if(MainMenu.class.getResource(AA.getCurrentUser().getAvatar()) != null)
            path = MainMenu.class.getResource(AA.getCurrentUser().getAvatar()).toExternalForm();
        else path = AA.getCurrentUser().getAvatar();
        avatarPhoto.setFill(new ImagePattern(new Image(path)));
    }

    public void profileMenu() throws Exception{
        new ProfileMenu().start(MainMenu.stage);
    }

    public void newGame() throws Exception {
        new GameMenu().start(MainMenu.stage);
    }

    public void continueGame() throws Exception{
        Database.getCurrentGame().start(stage);
    }

    public void scoreboard() throws Exception {
        new Scoreboard().start(MainMenu.stage);
    }

    public void settings() throws Exception{
        new Settings().start(MainMenu.stage);
    }


    public void twoPlayer(MouseEvent mouseEvent) throws Exception{
        new TwoPlayerMenu().start(MainMenu.stage);
    }

    public void jarFile(MouseEvent mouseEvent) throws IOException, InterruptedException {
        Process process = Runtime.getRuntime().exec("mvn package");
        process.waitFor();
    }

    public void exit(MouseEvent mouseEvent) {
        stage.close();
    }
}
