package game;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Timer extends Application {

    @Override
    public void start(Stage primaryStage) {

        Label label = new Label();
        DoubleProperty time = new SimpleDoubleProperty();
        label.textProperty().bind(time.asString("%.3f seconds"));

        BooleanProperty running = new SimpleBooleanProperty();

        AnimationTimer timer = new AnimationTimer() {

            private long startTime ;

            @Override
            public void start() {
                startTime = System.currentTimeMillis();
                running.set(true);
                super.start();
            }

            @Override
            public void stop() {
                running.set(false);
                super.stop();
            }

            @Override
            public void handle(long timestamp) {
                long now = System.currentTimeMillis();
                time.set((now - startTime) / 1000.0);
            }
        };

        Button startStop = new Button();

        startStop.textProperty().bind(
            Bindings.when(running)
                .then("Stop")
                .otherwise("Start"));

        startStop.setOnAction(e -> {
            if (running.get()) {
                timer.stop();
            } else {
                timer.start();
            }
        });

        VBox root = new VBox(10, label, startStop);
        root.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root, 320, 120);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}