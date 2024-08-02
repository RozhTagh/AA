package controller;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;
import model.ShootCircle;
import view.GameMenu;

import java.util.ArrayList;

public class GameController {
    private final ObservableList<ShootCircle> ballsOnMain = FXCollections.observableArrayList();
    private final ArrayList<ShootCircle> allBalls = new ArrayList<>();
    private BooleanProperty isGameEnded = new SimpleBooleanProperty(false);

    public ObservableList<ShootCircle> getBallsOnMain(){
        return ballsOnMain;
    }

    public ArrayList<ShootCircle> getAllBalls() {
        return allBalls;
    }
    public BooleanProperty isGameEnded() {
        return isGameEnded;
    }

    public void addBallOnMain(ShootCircle shootCircle){
        ballsOnMain.add(shootCircle);
        collision();
    }

    public void collision(){
        for(int i = 0; i < ballsOnMain.size()-1; i++){
            for(int j = i + 1; j < ballsOnMain.size(); j++){
                if(ballsOnMain.get(i).getBoundsInParent().intersects(ballsOnMain.get(j).getBoundsInParent())) {
                    isGameEnded.set(true);
                    ballsOnMain.get(i).setFill(Color.STEELBLUE);
                    ballsOnMain.get(j).setFill(Color.DARKBLUE);
                    return;
                }
            }
        }
    }
}
