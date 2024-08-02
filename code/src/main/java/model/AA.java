package model;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class AA {
    private static User currentUser;
    private static Level currentLevel = Level.TWO;
    private static int ballNumber = 15;
    private static Map map = Map.ONE;
    private static MediaPlayer musicTrack = Music.TWO.getMediaPlayer();
    private static boolean isBW = false;
    private static String onePlayerKey = "Space";
    private static String twoPlayerKey = "Enter";
    private static String IceKey = "Tab";

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User user) {
        AA.currentUser = user;
    }

    public static Level getCurrentLevel(){
        return currentLevel;
    }

    public static void setCurrentLevel(Level level){
        AA.currentLevel = level;
    }

    public static int getBallNumber() {
        return ballNumber;
    }

    public static void setBallNumber(int ballNumber) {
        AA.ballNumber = ballNumber;
    }

    public static Map getMap() {
        return map;
    }

    public static void setMap(Map map) {
        AA.map = map;
    }

    public static MediaPlayer getMusicTrack() {
        return musicTrack;
    }

    public static void setMusicTrack(MediaPlayer musicTrack) {
        AA.musicTrack = musicTrack;
    }

    public static boolean isBW() {
        return isBW;
    }

    public static void setBW(boolean BW) {
        isBW = BW;
    }

    public static String getOnePlayerKey() {
        return onePlayerKey;
    }

    public static String getTwoPlayerKey() {
        return twoPlayerKey;
    }

    public static String getIceKey() {
        return IceKey;
    }

    public static void setOnePlayerKey(String onePlayerKey) {
        AA.onePlayerKey = onePlayerKey;
    }

    public static void setTwoPlayerKey(String twoPlayerKey) {
        AA.twoPlayerKey = twoPlayerKey;
    }

    public static void setIceKey(String iceKey) {
        IceKey = iceKey;
    }
}
