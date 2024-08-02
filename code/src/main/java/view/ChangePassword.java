package view;

import controller.ProfileController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.AA;
import view.messages.ProfileMenuMessages;

public class ChangePassword extends Application {
    private static Stage stage;
    @FXML
    private PasswordField password;
    @FXML
    private Text passwordDetail;
    @Override
    public void start(Stage stage) throws Exception {
        ChangePassword.stage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(ChangePassword.class.getResource("changePassword.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 320);
        stage.setScene(scene);
        stage.setTitle("change password");
        stage.show();
    }

    public void back(MouseEvent mouseEvent) throws Exception{
        new ProfileMenu().start(stage);
    }

    public void changePassword(MouseEvent mouseEvent) throws Exception{
        ProfileMenuMessages message = ProfileController.changePassword(password.getText());
        if(message.equals(ProfileMenuMessages.INVALID_PASSWORD))
            passwordDetail.setText("invalid password");
        else if (message.equals(ProfileMenuMessages.WEAK_PASSWORD))
            passwordDetail.setText("weak password");
        else{
            AA.getCurrentUser().setPassword(password.getText());
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("password confirmed");
            alert.setHeaderText("CONGRATULATIONS");
            alert.setContentText("Your password has been changed successfully");
            alert.showAndWait();
            new ProfileMenu().start(stage);
        }
    }

    public void reset(MouseEvent mouseEvent) {
        password.setText("");
        passwordDetail.setText("");
    }
}
