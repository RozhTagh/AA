package view;

import controller.SignupController;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import view.messages.SignupMenuMessage;

public class LoginMenuController {
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Text usernameDetails;
    @FXML
    private Text passwordDetails;

    public void submit() throws Exception {
        SignupMenuMessage message = SignupController.login(username.getText(), password.getText());
        if(message.equals(SignupMenuMessage.USERNAME_NOT_EXIST))
            usernameDetails.setText("Username does not exist!");
        else if(message.equals(SignupMenuMessage.INCORRECT_PASSWORD))
            passwordDetails.setText("Incorrect password!");
        else new MainMenu().start(LoginMenu.stage);
    }

    public void signupMenu() throws Exception {
        new SignupMenu().start(LoginMenu.stage);
    }

    public void reset(){
        username.setText("");
        password.setText("");
    }
}
