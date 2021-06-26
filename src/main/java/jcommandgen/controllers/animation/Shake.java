package jcommandgen.controllers.animation;

import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class Shake {

    private final TranslateTransition tt;

    public Shake(double millis) {
        tt = new TranslateTransition(Duration.millis(millis));
        tt.setFromX(-10);
        tt.setByX(10);
        tt.setCycleCount(3);
        tt.setAutoReverse(true);
    }

    public void play(Node node) {
        tt.setNode(node);
        tt.play();
    }

}
