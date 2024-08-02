package view;

import controller.ProfileController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.AA;
import view.messages.ProfileMenuMessages;

public class ChangeUsername extends Application {
    public static Stage stage;
    @FXML
    private TextField username;
    @FXML
    private Text usernameDetail;

    @Override
    public void start(Stage stage) throws Exception {
        ChangeUsername.stage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(ChangeUsername.class.getResource("changeUsername.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 320);
        stage.setScene(scene);
        stage.setTitle("change username");
        stage.show();
    }

    public void changeUsername() throws Exception {
        ProfileMenuMessages message = ProfileController.changeUsername(username.getText());
        if(message.equals(ProfileMenuMessages.INVALID_USERNAME))
            usernameDetail.setText("invalid username");
        else if(message.equals(ProfileMenuMessages.USERNAME_EXISTS))
            usernameDetail.setText("username a;ready exists");
        else{
            AA.getCurrentUser().setUsername(username.getText());
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("username confirmed");
            alert.setHeaderText("CONGRATULATIONS");
            alert.setContentText("Your username has been changed successfully");
            alert.showAndWait();
            new ProfileMenu().start(stage);
        }
    }

    public void reset(MouseEvent mouseEvent) {
        username.setText("");
        usernameDetail.setText("");
    }

    public void back(MouseEvent mouseEvent) throws Exception{
        new ProfileMenu().start(stage);
    }
}
