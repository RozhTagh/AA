package view;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.AA;
import model.Music;

public class MusicMenuController {
    private static Stage stage;
    @FXML
    public RadioButton music1selector;
    @FXML
    public RadioButton music2selector;
    @FXML
    public RadioButton music3selector;
    @FXML
    private Button music1;
    @FXML
    private Button music2;
    @FXML
    private Button music3;

    @FXML
    public void initialize(){
        ImageView imageView1 = new ImageView(new Image(MusicMenuController.class.getResource("/images/play.png").toExternalForm()));
        imageView1.setFitWidth(30);
        imageView1.setFitHeight(30);
        music1.setGraphic(imageView1);
        if(AA.getMusicTrack().equals(Music.ONE)) music1selector.setSelected(true);

        ImageView imageView2 = new ImageView(new Image(MusicMenuController.class.getResource("/images/play.png").toExternalForm()));
        imageView2.setFitWidth(30);
        imageView2.setFitHeight(30);
        music2.setGraphic(imageView2);
        if(AA.getMusicTrack().equals(Music.TWO)) music2selector.setSelected(true);

        ImageView imageView3 = new ImageView(new Image(MusicMenuController.class.getResource("/images/play.png").toExternalForm()));
        imageView3.setFitWidth(30);
        imageView3.setFitHeight(30);
        music3.setGraphic(imageView3);
        if(AA.getMusicTrack().equals(Music.THREE)) music3selector.setSelected(true);
    }

    public void playMusic1() {
        Music.ONE.getMediaPlayer().play();
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(7), actionEvent -> Music.ONE.getMediaPlayer().stop()));
        timeline.play();
    }

    public void setMusic1() {
        GameMenu.musicTrack = Music.ONE.getMediaPlayer();
        TwoPlayerMenu.musicTrack = Music.ONE.getMediaPlayer();
    }

    public void playMusic2() {
        Music.TWO.getMediaPlayer().play();
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(7), actionEvent -> Music.TWO.getMediaPlayer().stop()));
        timeline.play();
    }

    public void setMusic2() {
        GameMenu.musicTrack = Music.TWO.getMediaPlayer();
        TwoPlayerMenu.musicTrack = Music.TWO.getMediaPlayer();
    }

    public void playMusic3() {
        Music.THREE.getMediaPlayer().play();
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(7), actionEvent -> Music.THREE.getMediaPlayer().stop()));
        timeline.play();
    }

    public void setMusic3() {
        GameMenu.musicTrack = Music.THREE.getMediaPlayer();
        TwoPlayerMenu.musicTrack = Music.THREE.getMediaPlayer();
    }
}
