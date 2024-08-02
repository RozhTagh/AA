package view;

import controller.ProfileController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import model.AA;

public class ProfileMenu extends Application {
    public static Stage stage;
    @FXML
    private Circle avatarPhoto;

    @Override
    public void start(Stage stage) throws Exception {
        ProfileMenu.stage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(ProfileMenu.class.getResource("profileMenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 320);
        stage.setScene(scene);
        stage.setTitle("Profile Menu");
        stage.show();
    }

    public void deleteAccount() throws Exception{
        ProfileController.removeUser();
        new SignupMenu().start(ProfileMenu.stage);
    }

    public void changeUsername() throws Exception {
        new ChangeUsername().start(ProfileMenu.stage);
    }

    public void logout() throws Exception {
        AA.setCurrentUser(null);
        new LoginMenu().start(ProfileMenu.stage);
    }

    public void changePassword(MouseEvent mouseEvent) throws Exception{
        new ChangePassword().start(ProfileMenu.stage);
    }

    public void changeAvatar(MouseEvent mouseEvent) throws Exception{
        new ChangeAvatar().start(ProfileMenu.stage);
    }

    @FXML
    public void initialize() {
        String path;
        if(MainMenu.class.getResource(AA.getCurrentUser().getAvatar()) != null)
            path = MainMenu.class.getResource(AA.getCurrentUser().getAvatar()).toExternalForm();
        else path = AA.getCurrentUser().getAvatar();
        avatarPhoto.setFill(new ImagePattern(new Image(path)));
    }

    public void back(MouseEvent mouseEvent) throws Exception{
        new MainMenu().start(stage);
    }
}
