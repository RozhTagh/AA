package view;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Database;
import model.Level;
import model.User;

public class Scoreboard extends Application {
    public static Stage stage;
    @Override
    public void start(Stage stage) throws Exception {
        Scoreboard.stage = stage;
        Pane pane = new Pane();

        Label inTotal = new Label("in total");
        inTotal.setLayoutX(180);
        inTotal.setLayoutY(40);
        Label level1 = new Label("level 1");
        level1.setLayoutX(340);
        level1.setLayoutY(40);
        Label level2 = new Label("level 2");
        level2.setLayoutX(500);
        level2.setLayoutY(40);
        Label level3 = new Label("level 3");
        level3.setLayoutX(660);
        level3.setLayoutY(40);
        pane.getChildren().addAll(inTotal, level1, level2, level3);

        HBox main = new HBox();
        main.setAlignment(Pos.CENTER);
        main.setSpacing(80);
        main.setLayoutX(40);
        main.setLayoutY(80);
        pane.getChildren().add(main);

        createBackButton(main);

        VBox vBox1 = new VBox();
        vBox1.setAlignment(Pos.CENTER);
        vBox1.setSpacing(20);
        main.getChildren().add(vBox1);
        User.setRank();
        for(int i = 0; i < 10; i++){
            HBox hBox = new HBox();
            hBox.setSpacing(20);
            hBox.setAlignment(Pos.CENTER);
            if(i >= Database.getUsers().size()) continue;
            Text username = new Text(Database.getUsers().get(i).getUsername());
            Text score = new Text(String.valueOf(Database.getUsers().get(i).getScore()));
            if(i < 3){
                username.setFill(Color.RED);
                score.setFill(Color.RED);
            }
            hBox.getChildren().add(username);
            hBox.getChildren().add(score);
            vBox1.getChildren().add(hBox);
        }

        VBox vBox2 = new VBox();
        vBox2.setAlignment(Pos.CENTER);
        vBox2.setSpacing(20);
        main.getChildren().add(vBox2);
        User.setRankByLevel(Level.ONE);
        for(int i = 0; i < 10; i++){
            HBox hBox = new HBox();
            hBox.setSpacing(20);
            hBox.setAlignment(Pos.CENTER);
            if(i >= Database.getUsers().size()) continue;
            Text username = new Text(Database.getUsers().get(i).getUsername());
            Text score = new Text(String.valueOf(Database.getUsers().get(i).getScoreByLevel(Level.ONE)));
            if(i < 3){
                username.setFill(Color.RED);
                score.setFill(Color.RED);
            }
            hBox.getChildren().add(username);
            hBox.getChildren().add(score);
            vBox2.getChildren().add(hBox);
        }

        VBox vBox3 = new VBox();
        vBox3.setAlignment(Pos.CENTER);
        vBox3.setSpacing(20);
        main.getChildren().add(vBox3);
        User.setRankByLevel(Level.TWO);
        for(int i = 0; i < 10; i++){
            HBox hBox = new HBox();
            hBox.setSpacing(20);
            hBox.setAlignment(Pos.CENTER);
            if(i >= Database.getUsers().size()) continue;
            Text username = new Text(Database.getUsers().get(i).getUsername());
            Text score = new Text(String.valueOf(Database.getUsers().get(i).getScoreByLevel(Level.TWO)));
            if(i < 3){
                username.setFill(Color.RED);
                score.setFill(Color.RED);
            }
            hBox.getChildren().add(username);
            hBox.getChildren().add(score);
            vBox3.getChildren().add(hBox);
        }

        VBox vBox4 = new VBox();
        vBox4.setAlignment(Pos.CENTER);
        vBox4.setSpacing(20);
        main.getChildren().add(vBox4);
        User.setRankByLevel(Level.THREE);
        for(int i = 0; i < 10; i++){
            HBox hBox = new HBox();
            hBox.setSpacing(20);
            hBox.setAlignment(Pos.CENTER);
            if(i >= Database.getUsers().size()) continue;
            Text username = new Text(Database.getUsers().get(i).getUsername());
            Text score = new Text(String.valueOf(Database.getUsers().get(i).getScoreByLevel(Level.THREE)));
            if(i < 3){
                username.setFill(Color.RED);
                score.setFill(Color.RED);
            }
            hBox.getChildren().add(username);
            hBox.getChildren().add(score);
            vBox4.getChildren().add(hBox);
        }

        Scene scene = new Scene(pane, 780, 470);
        stage.setScene(scene);
        stage.setTitle("Scoreboard");
        stage.show();
    }

    private void createBackButton(HBox main){
        Button button = new Button("back");
        button.setLayoutY(20);
        button.setLayoutX(20);
        button.setOnMouseClicked(mouseEvent -> {
            try {
                new MainMenu().start(stage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        main.getChildren().add(button);
    }
}