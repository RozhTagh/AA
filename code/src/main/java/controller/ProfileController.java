package controller;

import model.AA;
import model.User;
import view.messages.ProfileMenuMessages;

public class ProfileController {
    public static void removeUser(){
        AA.getCurrentUser().removeUser();
        AA.setCurrentUser(null);
    }

    public static ProfileMenuMessages changeUsername(String username){
        if(!User.isUsernameValid(username))
            return ProfileMenuMessages.INVALID_USERNAME;
        if(User.getUserByUsername(username) != null)
            return ProfileMenuMessages.USERNAME_EXISTS;
        AA.getCurrentUser().setUsername(username);
        return ProfileMenuMessages.USERNAME_CHANGE_SUCCESSFUL;
    }

    public static ProfileMenuMessages changePassword (String password){
        if(!User.isPasswordValid(password))
            return ProfileMenuMessages.INVALID_PASSWORD;
        if(!User.isPasswordStrong(password))
            return ProfileMenuMessages.WEAK_PASSWORD;
        AA.getCurrentUser().setPassword(password);
        return ProfileMenuMessages.PASSWORD_CHANGE_SUCCESSFUL;
    }
}
