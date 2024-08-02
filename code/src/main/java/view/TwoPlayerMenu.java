package view;

import controller.GameController;
import javafx.animation.*;
import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.*;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class TwoPlayerMenu extends Application {
    public static Stage stage;
    private Scene scene;
    private final GameController gameController = new GameController();
    private final ObservableList<PathTransition> allPathTransitions = FXCollections.observableArrayList();
    private final ObservableList<FadeTransition> allFadeTransitions = FXCollections.observableArrayList();
    private final ObservableList<ScaleTransition> allScaleTransitions = FXCollections.observableArrayList();
    private final Level level = AA.getCurrentLevel();
    private final IntegerProperty ballDown = new SimpleIntegerProperty(AA.getBallNumber()/2);
    private final IntegerProperty ballUp = new SimpleIntegerProperty((int)Math.ceil((double) AA.getBallNumber()/2));
    private final IntegerProperty ballNumber = new SimpleIntegerProperty(ballUp.add(ballDown).intValue());
    private boolean isIced = false;
    private boolean phase2HasBeenCalled = false;
    private boolean phase3HasBeenCalled = false;
    private boolean phase4HasBeenCalled = false;
    public static MediaPlayer musicTrack = AA.getMusicTrack();

    @Override
    public void start(Stage stage) throws Exception {
        TwoPlayerMenu.stage = stage;
        musicTrack.play();

        Pane pane = FXMLLoader.load(GameMenu.class.getResource("/view/gameMenu.fxml"));
        Background background = new Background(setBackground("/images/beigeScreen.jpg"));
        pane.setBackground(background);

        createPauseButton(pane);
        createMainCircle(pane);
        Circle pathCircle = createPathCircle(pane);
        ProgressBar iceCounter = createIceCounter(pane);
        ProgressBar ballCounter = createBallCounter(pane);
        Label ballCount = countBallNumber(pane);
        Label countScore = countScore(pane);

        for(int i = 1; i <= ballDown.intValue(); i++){
            ShootCircle ball = createShootCircle(pane);
            ball.setFill(new ImagePattern(new Image(GameMenu.class.getResource("/images/image" + i + ".jpg").toExternalForm())));
        }

        for(int i = 1; i <= ballUp.intValue(); i++){
            ShootCircle ball = createShootCircleUp(pane);
            ball.setFill(new ImagePattern(new Image(GameMenu.class.getResource("/images/G" + i + ".png").toExternalForm())));
        }

        gameController.isGameEnded().addListener((observable, oldValue, newValue) -> {
            if(newValue) {
                try {
                    endGameLost(pane);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        ballNumber.addListener((observable, oldValue, newValue) -> {
            ballCount.setText("ball shoot : " + (AA.getBallNumber() - newValue.intValue()));
            countScore.setText("score : " + (AA.getBallNumber() - newValue.intValue()) * AA.getCurrentLevel().getLevelNumber());
            if(newValue.intValue() <= 5)
                ballCount.setTextFill(Color.DARKGREEN);
            else if((double) newValue.intValue()/AA.getBallNumber() < (double) 1/2)
                ballCount.setTextFill(Color.YELLOWGREEN);
        });

        if(AA.isBW()){
            ColorAdjust colorAdjust = new ColorAdjust();
            colorAdjust.setSaturation(-1);
            pane.setEffect(colorAdjust);
        }

        Scene scene = new Scene(pane, 450, 600);
        stage.setScene(scene);
        this.scene = scene;
        enterEvent(pane, pathCircle, iceCounter, ballCounter);
        stage.setTitle("two-player game menu");
        stage.show();
    }

    private void createMainCircle(Pane pane){
        MainCircle mainCircle = new MainCircle();
        pane.getChildren().add(mainCircle);

        for(int i = 0; i < 5; i++){
            ShootCircle shootCircle = new ShootCircle(mainCircle, gameController, "/images/purpleDot.jpeg");
            shootCircle.getPathTransition().setDelay(Duration.millis
                    ((double) i * AA.getMap().getDelayByLevel(AA.getCurrentLevel().getLevelNumber())));
            shootCircle.getPathTransition().play();
            allPathTransitions.add(shootCircle.getPathTransition());
            pane.getChildren().add(shootCircle);
        }
    }

    private Circle createPathCircle(Pane pane){
        Circle pathCircle = new Circle(225, 250, 150);
        pathCircle.setOpacity(0);
        pane.getChildren().add(pathCircle);
        return pathCircle;
    }

    private void createPauseButton(Pane pane){
        Circle button = new Circle(20, 20, 15);
        pane.getChildren().add(button);
        button.setFill(new ImagePattern(new Image(GameMenu.class.getResource("/images/pause.png").toExternalForm())));
        button.setOnMouseClicked(mouseEvent1 -> {
            try {
                if(musicTrack != null) musicTrack.stop();
                stopAllAnimations();
                VBox pause = new FXMLLoader(GameMenu.class.getResource("pauseMenu2.fxml")).load();
                if(musicTrack != null) ((Button) pause.getChildren().get(4)).setText("stop music");
                else ((Button) pause.getChildren().get(4)).setText("start music");
                pane.getChildren().add(pause);
                pause.getChildren().get(0).setOnMouseClicked(mouseEvent2 -> {
                    pane.getChildren().remove(pause);
                    if(musicTrack != null) musicTrack.play();
                    startAllTransitions();
                    pane.getChildren().get(ballNumber.get()+12).requestFocus();
                });

                pause.getChildren().get(3).setOnMouseClicked(mouseEvent3 -> {
                    pane.getChildren().remove(pause);
                    BorderPane music;
                    try {
                        music = new FXMLLoader(GameMenu.class.getResource("musicMenu.fxml")).load();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    pane.getChildren().add(music);
                    BorderPane finalMusic = music;
                    music.getChildren().get(0).setOnMouseClicked(mouseEvent4 -> {
                        pane.getChildren().add(pause);
                        pane.getChildren().remove(finalMusic);
                    });
                });

                pause.getChildren().get(4).setOnMouseClicked(mouseEvent5 -> {
                    if(musicTrack != null){
                        musicTrack = null;
                        ((Button) pause.getChildren().get(4)).setText("start music");
                    }

                    else {
                        musicTrack = AA.getMusicTrack();
                        ((Button) pause.getChildren().get(4)).setText("stop music");
                    }
                });

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    private ProgressBar createIceCounter(Pane pane){
        ProgressBar progressBar = new ProgressBar(0);
        progressBar.setTranslateX(330);
        progressBar.setTranslateY(10);
        pane.getChildren().add(progressBar);
        Label label = new Label("balls until ice");
        label.setTranslateX(346);
        label.setTranslateY(10);
        label.setTextFill(Color.ORANGERED);
        pane.getChildren().add(label);
        return progressBar;
    }

    private ProgressBar createBallCounter(Pane pane){
        ProgressBar progressBar = new ProgressBar(0);
        progressBar.setTranslateX(330);
        progressBar.setTranslateY(35);
        pane.getChildren().add(progressBar);
        return progressBar;
    }

    private Label countBallNumber(Pane pane){
        Label label = new Label("balls shoot : " + (AA.getBallNumber() - ballNumber.intValue()));
        label.setTranslateX(345);
        label.setTranslateY(36);
        label.setTextFill(Color.ORANGERED);
        pane.getChildren().add(label);
        return label;
    }

    private Label countScore(Pane pane){
        Label label = new Label("score : 0");
        label.setTranslateX(200);
        label.setTranslateY(570);
        label.setTextFill(Color.BROWN);
        pane.getChildren().add(label);
        return label;
    }

    private ShootCircle createShootCircle(Pane pane){
        ShootCircle shootCircle = new ShootCircle(gameController);
        pane.getChildren().add(shootCircle);
        return shootCircle;
    }

    private ShootCircle createShootCircleUp(Pane pane){
        ShootCircle shootCircle = new ShootCircle(gameController, 50);
        pane.getChildren().add(shootCircle);
        return shootCircle;
    }

    private void enterEvent(Pane pane, Circle pathCircle, ProgressBar iceCounter, ProgressBar ballCounter){
        scene.setOnKeyPressed(keyEvent -> {
            String keyName = keyEvent.getCode().getName();

            if(keyName.equals(AA.getOnePlayerKey())) {
                if(ballDown.get() >= 1) {
                    ShootCircle shootCircle = (ShootCircle) pane.getChildren().get(ballDown.get() + 12);
                    ShootingAnimation shootingAnimation = new ShootingAnimation(shootCircle, pathCircle, level, 1, phase4HasBeenCalled);

                    shootingAnimation.statusProperty().addListener((observable, oldValue, newValue) -> {
                        if (newValue == Animation.Status.STOPPED) {
                            if (!isIced) iceCounter.setProgress(iceCounter.getProgress() + 0.2);
                            ballCounter.setProgress(ballCounter.getProgress() + (double) 1 / AA.getBallNumber());
                            gameController.getBallsOnMain().add(shootCircle);
                            gameController.collision();
                            ballNumber.set(ballNumber.get()-1);

                            if (gameController.isGameEnded().get()) {
                                try {
                                    endGameLost(pane);
                                } catch (Exception e) {
                                    System.out.println(e);
                                }
                            } else if (ballNumber.intValue() < 1) {
                                try {
                                    endGameWon(pane);
                                } catch (Exception e) {
                                    System.out.println(e);
                                }
                            } else {
                                PathTransition pathTransition = shootCircle.createPathTransition();
                                allPathTransitions.add(pathTransition);
                                pathTransition.play();
                                if ((double) ballNumber.intValue() / AA.getBallNumber() <= (double) 3 / 4 && !phase2HasBeenCalled) {
                                    phase2();
                                    phase2HasBeenCalled = true;
                                }
                                if ((double) ballNumber.intValue() / AA.getBallNumber() <= (double) 1 / 2 && !phase3HasBeenCalled)
                                    fade();
                            }
                        }
                    });

                    shootingAnimation.play();
                    MediaPlayer mediaPlayer = new MediaPlayer(new Media(GameMenu.class.getResource("/musics/shoot.mp3").toExternalForm()));
                    mediaPlayer.setVolume(0.1);
                    mediaPlayer.play();
                    ballDown.set(ballDown.get() - 1);
                }
            }

            else if(keyName.equals(AA.getTwoPlayerKey())){
                if(ballUp.get() >= 1) {
                    ShootCircle shootCircle = (ShootCircle) pane.getChildren().get(12 + AA.getBallNumber() / 2 + ballUp.get());
                    ShootingAnimation shootingAnimation = new ShootingAnimation(shootCircle, pathCircle, level, -1, phase4HasBeenCalled);

                    shootingAnimation.statusProperty().addListener((observable, oldValue, newValue) -> {
                        if (newValue == Animation.Status.STOPPED) {
                            if (!isIced) iceCounter.setProgress(iceCounter.getProgress() + 0.2);
                            ballCounter.setProgress(ballCounter.getProgress() + (double) 1 / AA.getBallNumber());
                            gameController.getBallsOnMain().add(shootCircle);
                            gameController.collision();
                            ballNumber.set(ballNumber.get()-1);

                            if (gameController.isGameEnded().get()) {
                                try {
                                    endGameLost(pane);
                                } catch (Exception e) {
                                    System.out.println(e);
                                }
                            } else if (ballNumber.intValue() < 1) {
                                try {
                                    endGameWon(pane);
                                } catch (Exception e) {
                                    System.out.println(e);
                                }
                            } else {
                                PathTransition pathTransition = shootCircle.createPathTransition();
                                allPathTransitions.add(pathTransition);
                                pathTransition.play();
                                if ((double) ballNumber.intValue() / AA.getBallNumber() <= (double) 3 / 4 && !phase2HasBeenCalled) {
                                    phase2();
                                    phase2HasBeenCalled = true;
                                }
                                if ((double) ballNumber.intValue() / AA.getBallNumber() <= (double) 1 / 2 && !phase3HasBeenCalled)
                                    fade();
                                if((double) ballNumber.intValue() / AA.getBallNumber() <= (double) 1/4 && !phase4HasBeenCalled)
                                    phase4HasBeenCalled = true;
                            }
                        }
                    });

                    shootingAnimation.play();
                    MediaPlayer mediaPlayer = new MediaPlayer(new Media(GameMenu.class.getResource("/musics/shoot.mp3").toExternalForm()));
                    mediaPlayer.setVolume(0.1);
                    mediaPlayer.play();
                    ballUp.set(ballUp.get() - 1);
                }
            }

            else if(keyName.equals(AA.getIceKey())){
                if(iceCounter.getProgress() >= 1){
                    new MediaPlayer(new Media(Objects.requireNonNull(TwoPlayerMenu.class.getResource("/musics/ice.mp3")).toExternalForm())).play();
                    slowTransitions();
                    Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(AA.getCurrentLevel().getIceTimer()), actionEvent -> fastTransitions()));
                    timeline.play();
                    iceCounter.setProgress(0);
                }
            }
        });
    }

    private void endGameWon(Pane pane) {
        stopAllAnimations();
        TwoPlayerMenu.musicTrack.stop();
        Background background = new Background(setBackground("/images/lightGreen.jpeg"));
        pane.setBackground(background);
        pane.getChildren().get(0).requestFocus();
        pane.setOnMouseClicked(mouseEvent -> {
            try {
                countScoreWon();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void endGameLost(Pane pane) {
        stopAllAnimations();
        TwoPlayerMenu.musicTrack.stop();
        Background background = new Background(setBackground("/images/lightRed.png"));
        pane.setBackground(background);
        pane.getChildren().get(0).requestFocus();
        Timeline timeline1 = new Timeline(new KeyFrame(Duration.millis(20), actionEvent -> createZoom(pane)));
        timeline1.setCycleCount(30);
        timeline1.play();
        timeline1.setOnFinished(actionEvent -> {
            Timeline timeline2 = new Timeline(new KeyFrame(Duration.millis(20), actionEvent2 -> exitZoom(pane)));
            timeline2.setCycleCount(30);
            timeline2.play();
        });

        pane.setOnMouseClicked(mouseEvent -> {
            try {
                countScoreLost();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void createZoom(Pane pane){
        pane.setScaleX(pane.getScaleX() + 0.01);
        pane.setScaleY(pane.getScaleY() + 0.01);
    }

    private void exitZoom(Pane pane){
        pane.setScaleX(pane.getScaleX() - 0.01);
        pane.setScaleY(pane.getScaleY() - 0.01);
    }

    private BackgroundImage setBackground(String URL){
        Image image = new Image(GameMenu.class.getResource(URL).toExternalForm(), 800 ,600, false, false);
        BackgroundImage backgroundImage = new BackgroundImage(image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        return backgroundImage;
    }

    private void stopAllAnimations(){
        stopPathTransitions();
    }

    private void startAllTransitions(){
        for(Transition transition : allPathTransitions)
            transition.setRate(1);

        allPathTransitions.addListener((ListChangeListener<PathTransition>) change -> {
            while (change.next()){
                if(change.wasAdded()){
                    List<? extends PathTransition> addedTransitions = change.getAddedSubList();
                    for(PathTransition transition : addedTransitions)
                        transition.setRate(1);
                }
            }
        });
    }
    private void slowTransitions(){
        isIced = true;
        for(PathTransition transition : allPathTransitions){
            transition.setRate((double) 1/2);
        }
        allPathTransitions.addListener((ListChangeListener<PathTransition>) change -> {
            while (change.next()){
                if(change.wasAdded() && isIced){
                    List<? extends PathTransition> addedTransitions = change.getAddedSubList();
                    for(PathTransition transition : addedTransitions)
                        transition.setRate((double) 1/2);
                }
            }
        });
    }

    private void fastTransitions(){
        isIced = false;
        for(PathTransition transition : allPathTransitions) {
            transition.setRate(1);
        }
    }

    private void countScoreWon() throws Exception {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("You Won!");
        alert.setContentText("You scored " + AA.getBallNumber() + " x " + AA.getCurrentLevel().getLevelNumber() + " points!");
        AA.getCurrentUser().addScore((long) AA.getBallNumber() * AA.getCurrentLevel().getLevelNumber(), AA.getCurrentLevel());
        new MainMenu().start(stage);
        alert.show();
    }

    private void countScoreLost() throws Exception {
        int ballScored = AA.getBallNumber() - ballNumber.intValue() - 1;
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("You Lost:(");
        alert.setContentText("You scored " + ballScored + " x " + AA.getCurrentLevel().getLevelNumber() + " points!");
        AA.getCurrentUser().addScore((long) ballScored * AA.getCurrentLevel().getLevelNumber(), AA.getCurrentLevel());
        new MainMenu().start(stage);
        alert.show();
    }

    private void phase2(){
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), actionEvent -> changeDirection()));
        timeline.setCycleCount(-1);
        timeline.play();
        increaseRadius();
    }

    private void changeDirection(){
        for(PathTransition transition : allPathTransitions){
            transition.setRate(transition.getRate() * -1);
        }
        allPathTransitions.addListener((ListChangeListener<PathTransition>) change -> {
            while (change.next()){
                if(change.wasAdded()){
                    List<? extends PathTransition> addedTransitions = change.getAddedSubList();
                    for(PathTransition transition : addedTransitions)
                        transition.setRate(transition.getRate() * -1);
                }
            }
        });
    }

    private void increaseRadius(){
        for(ShootCircle shootCircle : gameController.getAllBalls()){
            ScaleTransition transition = new ScaleTransition(Duration.seconds(2), shootCircle);
            transition.setAutoReverse(true);
            transition.setToX(1.1);
            transition.setToY(1.1);
            transition.setCycleCount(-1);
            transition.play();
            allScaleTransitions.add(transition);
        }
    }

    private void fade(){
        for(ShootCircle shootCircle : gameController.getBallsOnMain()){
            FadeTransition transition = new FadeTransition(Duration.seconds(2), shootCircle);
            transition.setFromValue(1);
            transition.setToValue(0);
            transition.setAutoReverse(true);
            transition.setCycleCount(-1);
            transition.play();
            allFadeTransitions.add(transition);
        }
        phase3HasBeenCalled = true;

        gameController.getBallsOnMain().addListener((ListChangeListener<ShootCircle>) change -> {
            while (change.next()){
                if(change.wasAdded()){
                    List<? extends ShootCircle> addedBalls = change.getAddedSubList();
                    for(ShootCircle shootCircle : addedBalls){
                        FadeTransition transition = new FadeTransition(Duration.seconds(2), shootCircle);
                        transition.setFromValue(1);
                        transition.setToValue(0);
                        transition.setAutoReverse(true);
                        transition.setCycleCount(-1);
                        transition.play();
                    }
                }
            }
        });
    }

    private void stopPathTransitions(){
        for(Transition transition : allPathTransitions)
            transition.setRate(0);

        allPathTransitions.addListener((ListChangeListener<PathTransition>) change -> {
            while (change.next()){
                if(change.wasAdded()){
                    List<? extends PathTransition> addedTransitions = change.getAddedSubList();
                    for(PathTransition transition : addedTransitions)
                        transition.setRate(0);
                }
            }
        });
    }

    public void restartGame() throws Exception{
        new TwoPlayerMenu().start(stage);
    }

    public void exitGame() throws Exception{
        new MainMenu().start(stage);
    }

    public void stopMusic() {
        TwoPlayerMenu.musicTrack.stop();
    }

    public void showKeys() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Control Keys");
        alert.setContentText("Shoot Ball for the first player : " + AA.getOnePlayerKey() + "\nIce Mode : " + AA.getIceKey() +
                "\nShoot Ball for the second player : " + AA.getTwoPlayerKey());
        alert.show();
    }
}
