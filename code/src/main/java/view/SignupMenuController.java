package view;

import controller.SignupController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import view.messages.SignupMenuMessage;

public class SignupMenuController {
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Text usernameDetails;
    @FXML
    private Text passwordDetails;

    public void submit() throws Exception {
        String usernameResult = checkUsername();
        String passwordResult = checkPassword();
        if(usernameResult.equals("valid") && passwordResult.equals("valid")){
            SignupController.signup(username.getText(), password.getText());
            new MainMenu().start(SignupMenu.stage);
        }
    }

    public void loginMenu() throws Exception {
        new LoginMenu().start(SignupMenu.stage);
    }

    public void reset(){
        username.setText("");
        password.setText("");
    }

    private String checkUsername(){
        SignupMenuMessage usernameMessage = SignupController.checkUsername(username.getText());

        if(usernameMessage.equals(SignupMenuMessage.INVALID_USERNAME)) {
            usernameDetails.setText("Invalid Username!");
            return "invalid";
        }

        else if (usernameMessage.equals(SignupMenuMessage.USERNAME_EXISTS)){
            usernameDetails.setText("Username already exists!");
            return "invalid";
        }

        usernameDetails.setText("");
        return "valid";
    }

    private String checkPassword(){
        SignupMenuMessage passwordMessage = SignupController.checkPassword(password.getText());

        if(passwordMessage.equals(SignupMenuMessage.INVALID_PASSWORD)){
            passwordDetails.setText("Invalid Password!");
            return "invalid";
        }

        else if(passwordMessage.equals(SignupMenuMessage.WEAK_PASSWORD)) {
            passwordDetails.setText("Weak Password!");
            return "invalid";
        }

        passwordDetails.setText("");
        return "valid";
    }
}