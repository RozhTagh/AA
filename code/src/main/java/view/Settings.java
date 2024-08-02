package view;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.AA;
import model.Level;
import model.Map;
import model.Music;

public class Settings extends Application {
    public static Stage stage;
    @FXML
    public RadioButton level1;
    @FXML
    public RadioButton level2;
    @FXML
    public RadioButton level3;
    @FXML
    public RadioButton mapButton1;
    @FXML
    public RadioButton mapButton2;
    @FXML
    public RadioButton mapButton3;
    @FXML
    private Slider ballNumber;
    @FXML
    private Text sliderNumber;
    @FXML
    private Circle map1;
    @FXML
    private Circle map2;
    @FXML
    private Circle map3;
    @FXML
    private CheckBox BW;
    @FXML
    private CheckBox mute;
    @FXML
    private ComboBox<String> onePlayer;
    @FXML
    private ComboBox<String> twoPlayer;
    @FXML
    private ComboBox<String> ice;

    @Override
    public void start(Stage stage) throws Exception {
        Settings.stage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(Settings.class.getResource("settings.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 640);
        stage.setScene(scene);
        stage.setTitle("settings");
        stage.show();
    }

    public void level1(ActionEvent actionEvent) {
        AA.setCurrentLevel(Level.ONE);
    }

    public void level2(ActionEvent actionEvent) {
        AA.setCurrentLevel(Level.TWO);
    }

    public void level3(ActionEvent actionEvent) {
        AA.setCurrentLevel(Level.THREE);
    }

    @FXML
    public void initialize(){
        map1.setFill(new ImagePattern(new Image(Settings.class.getResource("/images/map1.jpeg").toExternalForm())));
        map2.setFill(new ImagePattern(new Image(Settings.class.getResource("/images/map2.jpeg").toExternalForm())));
        map3.setFill(new ImagePattern(new Image(Settings.class.getResource("/images/map3.jpeg").toExternalForm())));
        sliderNumber.setText("ball number : " + AA.getBallNumber());
        ballNumber.setValue(AA.getBallNumber());

        if(AA.getCurrentLevel().equals(Level.ONE)) level1.setSelected(true);
        else if(AA.getCurrentLevel().equals(Level.TWO)) level2.setSelected(true);
        else level3.setSelected(true);

        if(AA.getMap().equals(Map.ONE)) mapButton1.setSelected(true);
        else if (AA.getMap().equals(Map.TWO)) mapButton2.setSelected(true);
        else mapButton3.setSelected(true);

        ballNumber.valueProperty().addListener((observable, oldValue, newValue) -> {
            sliderNumber.setText("ball number : " + Integer.toString(newValue.intValue()));
            AA.setBallNumber((int) ballNumber.getValue());
        });
        if(AA.isBW()) BW.setSelected(true);
        if(AA.getMusicTrack() == null) mute.setSelected(true);
        onePlayer.setPromptText("first player's shoot");
        onePlayer.getItems().add("Space");
        onePlayer.getItems().add("Enter");
        onePlayer.getItems().add("Up");

        twoPlayer.setPromptText("second player's shoot");
        twoPlayer.getItems().add("Space");
        twoPlayer.getItems().add("Enter");
        twoPlayer.getItems().add("Down");

        ice.setPromptText("ice key");
        ice.getItems().add("Space");
        ice.getItems().add("Enter");
        ice.getItems().add("Shift");
    }

    public void map1(ActionEvent actionEvent) {
        AA.setMap(Map.ONE);
    }

    public void map2(ActionEvent actionEvent) {
        AA.setMap(Map.TWO);
    }

    public void map3(ActionEvent actionEvent) {
        AA.setMap(Map.THREE);
    }

    public void back(MouseEvent mouseEvent) throws Exception{
        new MainMenu().start(stage);
    }

    public void mute(ActionEvent actionEvent) {
        if(mute.isSelected()) AA.setMusicTrack(null);
        else AA.setMusicTrack(Music.TWO.getMediaPlayer());
    }

    public void BW(ActionEvent actionEvent) {
        AA.setBW(BW.isSelected());
    }

    public void setOnePlayer(ActionEvent actionEvent) {
        AA.setOnePlayerKey(onePlayer.getSelectionModel().getSelectedItem());
    }

    public void setTwoPlayer(ActionEvent actionEvent) {
        AA.setTwoPlayerKey(twoPlayer.getSelectionModel().getSelectedItem());
    }

    public void ice(ActionEvent actionEvent) {
        AA.setIceKey(ice.getSelectionModel().getSelectedItem());
    }
}
