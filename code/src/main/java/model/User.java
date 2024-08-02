package model;

import java.util.*;
import java.util.Map;

public class User {
    private String username;
    private String password;
    private String avatar;
    private long score;
    private final HashMap<Level, Long> scoreByLevel;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.score = 0;
        this.avatar = "/images/avatar" + randomNumber() + ".jpeg";
        scoreByLevel = new HashMap<>();
        scoreByLevel.put(Level.ONE, 0L);
        scoreByLevel.put(Level.TWO, 0L);
        scoreByLevel.put(Level.THREE, 0L);
        Database.addUser(this);
    }

    public void setUsername(String username){
        this.username = username;
        Database.saveUsers();
    }

    public void setPassword(String password){
        this.password = password;
        Database.saveUsers();
    }

    public String getAvatar() {
        return avatar;
    }

    public String getUsername(){
        return this.username;
    }

    public boolean checkPassword(String password){
        return this.password.equals(password);
    }

    public void setAvatar(String path) {
        this.avatar = path;
        Database.saveUsers();
    }

    public static User getUserByUsername(String username){
        for(User user : Database.getUsers()){
            if(user.getUsername().equals(username))
                return user;
        }
        return null;
    }

    public void removeUser(){
        Database.getUsers().remove(this);
        Database.saveUsers();
    }

    public static boolean isUsernameValid(String username){
        return username.matches("[a-zA-Z0-9_]+");
    }

    public static boolean isPasswordValid(String password){
        return password.matches("[a-zA-Z0-9!@#$%^&*()_]+");
    }

    public static boolean isPasswordStrong(String password){
        return password.matches(".*[a-z].*") && password.matches(".*[A-Z].*") &&
                password.matches(".*[0-9].*") && password.matches(".*[!@#$%^&*()_].*");
    }

    private int randomNumber(){
        Random random = new Random();
        return random.nextInt(8)+1;
    }

    public void addScore(long addition, Level level){
        this.score += addition;
        for(Map.Entry<Level, Long> set : scoreByLevel.entrySet()){
            if (set.getKey().equals(level)) {
                set.setValue(set.getValue() + addition);
            }
        }
        Database.saveUsers();
    }

    public long getScore(){
        return this.score;
    }

    public static void setRank(){
        for(int i = 0; i < Database.getUsers().size(); i++){
            for(int j = i; j < Database.getUsers().size()-i-1; j++){
                if(Database.getUsers().get(j).getScore() < Database.getUsers().get(j+1).getScore())
                    Collections.swap(Database.getUsers(), j, j+1);
            }
        }
    }

    public static void setRankByLevel (Level level){
        for(int i = 0; i < Database.getUsers().size(); i++){
            for(int j = i; j < Database.getUsers().size()-i-1; j++){
                if(Database.getUsers().get(j).getScoreByLevel(level) < Database.getUsers().get(j+1).getScoreByLevel(level))
                    Collections.swap(Database.getUsers(), j, j+1);
            }
        }
    }

    public Long getScoreByLevel(Level level){
        for(Map.Entry<Level, Long> set : scoreByLevel.entrySet()){
            if (set.getKey().equals(level))
                return set.getValue();
        }
        return 0L;
    }
}