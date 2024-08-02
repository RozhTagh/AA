package model;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

public class MainCircle extends Circle {

    public MainCircle(){
        this.setCenterX(220);
        this.setCenterY(250);
        this.setRadius(80);
        this.setFill(new ImagePattern(new Image(MainCircle.class.getResource("/images/aa.jpg").toExternalForm())));
    }
}