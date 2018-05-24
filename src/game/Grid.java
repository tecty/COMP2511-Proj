package game;

import javafx.animation.Animation;
import javafx.animation.FillTransition;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Grid extends Rectangle {
    // the small grid that contain each car

	//animation manager
	FillTransition transition;
	
    // the car in this grid
    private Car car;
    // the coordinate of this gird
    final private int x;
    final private int y;

    public Grid(int x, int y) {
        // set this rectangle's size by the settings
        // in game controller
        setWidth(GameController.GRID_SIZE);
        setHeight(GameController.GRID_SIZE);
        // set this rectangle has a border
        setStroke(Color.BLACK);
        setStrokeWidth(1);

        // set the background color
        setFill(Color.GREY);


        // set up this's grid's location
        relocate(
                x*GameController.GRID_SIZE,
                y*GameController.GRID_SIZE
        );

        this.x = x; this.y = y;
        
        transition = new FillTransition();
        transition.setDuration(Duration.millis(700));
		transition.setShape(this);
		transition.setFromValue(Color.GREY);
		transition.setToValue(Color.rgb(239, 239, 239));
		transition.setAutoReverse(true);
		transition.setCycleCount(Animation.INDEFINITE);
    }

    public boolean hasCar(Car car){
        return !(car == this.car ||this.car ==null);
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        // only can set car with no car in there
        // or clear this gird when moving out
        if (!hasCar(car) || car == null){
            this.car = car;
        }
    }
    
    public void flash() {
		transition.playFromStart();
    }
    
    public void stopFlash() {
    	transition.stop();
    	this.setFill(Color.GREY);
    }
}
