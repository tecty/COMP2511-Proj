 package game;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class GameTimer {
	Label timeCount;
    DoubleProperty time;
    BooleanProperty running;

    AnimationTimer timer;

    
    //initialization
    public GameTimer(Label timeCount) {
    	this.timeCount = timeCount;
    	time = new SimpleDoubleProperty();
    	running = new SimpleBooleanProperty();
    	
    	timer = new AnimationTimer() {
            private long startTime ;
            @Override
            public void start() {
                startTime = System.currentTimeMillis();
                super.start();
            }
            @Override
            public void handle(long timestamp) {
                long now = System.currentTimeMillis();
                time.set((now - startTime) / 1000.0);
            }
        };
    }
    
    @FXML
    public void run() {
        timeCount.textProperty().bind(time.asString("%.3f seconds"));

    }

}
