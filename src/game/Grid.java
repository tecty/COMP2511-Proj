package game;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Grid extends Rectangle {
    // the small grid that contain each car

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
                x* GameController.GRID_SIZE,
                y*GameController.GRID_SIZE
        );

        this.x = x; this.y = y;
    }

    public boolean hasCar(){
        return car != null;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        // only can set car with no car in there
        if (!hasCar()){
            this.car = car;
        }
    }
}
