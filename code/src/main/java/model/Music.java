package model;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public enum Music {
    ONE(1, new MediaPlayer(new Media(Music.class.getResource("/musics/music1.mp3").toExternalForm()))),
    TWO(2, new MediaPlayer(new Media(Music.class.getResource("/musics/music2.mp3").toExternalForm()))),
    THREE(3, new MediaPlayer(new Media(Music.class.getResource("/musics/music3.mp3").toExternalForm())));
    private int musicNumber;
    private MediaPlayer mediaPlayer;

    Music(int musicNumber, MediaPlayer mediaPlayer) {
        this.musicNumber = musicNumber;
        this.mediaPlayer = mediaPlayer;
    }

    public int getMusicNumber() {
        return musicNumber;
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public Music getMusicByNumber(int number){
        for(Music music : Music.values()){
            if (music.getMusicNumber() == number)
                return music;
        }
        return null;
    }
}
