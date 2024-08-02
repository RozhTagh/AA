package model;

import controller.GameController;
import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.geometry.NodeOrientation;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class ShootCircle extends Circle {
    private static MainCircle mainCircle;
    private PathTransition pathTransition;

    public ShootCircle(MainCircle mainCircle, GameController gameController, String path){
        this.setCenterX(225);
        this.setCenterY(405);
        this.setRadius(7);
        this.setFill(new ImagePattern(new Image(ShootCircle.class.getResource(path).toExternalForm())));
        ShootCircle.mainCircle = mainCircle;
        this.pathTransition = createPathTransition();
        gameController.getBallsOnMain().add(this);
        gameController.getAllBalls().add(this);
    }

    public ShootCircle(GameController gameController){
        this.setCenterX(225);
        this.setCenterY(500);
        this.setRadius(7);
        this.setFill(Color.BLACK);
        gameController.getAllBalls().add(this);
    }

    public ShootCircle(GameController gameController, double y){
        this.setCenterX(225);
        this.setCenterY(y);
        this.setRadius(7);
        this.setFill(Color.BLACK);
        gameController.getAllBalls().add(this);
    }

    public PathTransition getPathTransition() {
        return pathTransition;
    }

    public PathTransition createPathTransition(){
        double y = mainCircle.getCenterY();
        double x = mainCircle.getCenterX();
        double angle = Math.toDegrees(Math.atan2(this.getCenterY() - y, this.getCenterX() - x)) + 180;
        double radius = Math.pow(Math.pow((this.getCenterY() -y), 2) + Math.pow((this.getCenterX() - x), 2), 0.5);

        Arc path = new Arc(225, 250, radius, radius, angle, 360);
        path.setType(ArcType.CHORD);
        path.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);

        PathTransition pathTransition = new PathTransition();
        pathTransition.setNode(this);
        pathTransition.setCycleCount(-1);
        pathTransition.setDuration(Duration.millis(AA.getCurrentLevel().getRotationSpeed()));
        pathTransition.setPath(path);
        pathTransition.setInterpolator(Interpolator.LINEAR);
        this.pathTransition = pathTransition;

        return pathTransition;
    }
}