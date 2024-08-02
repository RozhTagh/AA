package view;

import javafx.animation.Transition;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import model.*;

import java.util.Random;

public class ShootingAnimation extends Transition {
    private final ShootCircle shootCircle;
    private final Circle pathCircle;
    private final Level level;
    private final int isDown;
    private final boolean phase4;

    public ShootingAnimation(ShootCircle shootCircle, Circle pathCircle, Level level, int isDown, boolean phase4){
        this.shootCircle = shootCircle;
        this.pathCircle = pathCircle;
        this.level = level;
        this.isDown = isDown;
        this.setCycleCount(-1);
        this.setCycleDuration(Duration.millis(15));
        this.phase4 = phase4;
    }

    @Override
    protected void interpolate(double v) {
        double y = shootCircle.getCenterY() - (5 * isDown);
        double x = shootCircle.getCenterX();
        if(phase4){
            Random random = new Random();
            x = shootCircle.getCenterX() - (level.getWindSpeed() * isDown * random.nextDouble(-2, 2));
        }

        if(shootCircle.getBoundsInParent().intersects(pathCircle.getBoundsInParent())) {
            this.stop();
        }

        shootCircle.setCenterY(y);
        shootCircle.setCenterX(x);
    }
}