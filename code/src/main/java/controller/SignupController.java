package controller;

import model.AA;
import model.User;
import view.messages.SignupMenuMessage;

public class SignupController {

    public static SignupMenuMessage checkUsername(String username){
        if(!User.isUsernameValid(username))
            return SignupMenuMessage.INVALID_USERNAME;
        if(User.getUserByUsername(username) != null)
            return SignupMenuMessage.USERNAME_EXISTS;
        else return SignupMenuMessage.VALID_USERNAME;
    }

    public static SignupMenuMessage checkPassword(String password){
        if(!User.isPasswordValid(password))
            return SignupMenuMessage.INVALID_PASSWORD;
        if(!User.isPasswordStrong(password))
            return SignupMenuMessage.WEAK_PASSWORD;
        else return SignupMenuMessage.VALID_PASSWORD;
    }

    public static void signup(String username, String password){
        User user = new User(username, password);
        AA.setCurrentUser(user);
    }

    public static SignupMenuMessage login(String username, String password){
        if(User.getUserByUsername(username) == null)
            return SignupMenuMessage.USERNAME_NOT_EXIST;
        if(!User.getUserByUsername(username).checkPassword(password))
            return SignupMenuMessage.INCORRECT_PASSWORD;
        else{
            AA.setCurrentUser(User.getUserByUsername(username));
            return SignupMenuMessage.LOGIN_SUCCESSFUL;
        }
    }
}
