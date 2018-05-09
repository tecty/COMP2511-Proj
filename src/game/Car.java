package game;

import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class Car extends StackPane {


    // the actual car move in each grid
    final private MoveDir dir;
    final private int carId;
    private int gridX;
    private int gridY;
    // the length of this car
    final private int len;


    public Car(MoveDir dir, int carId, int gridX, int gridY, int len,Paint color){
        // set the information of this car
        this.dir = dir;
        this.carId = carId;
        this.len = len;
        setGridX(gridX);
        setGridY(gridY);



        // set this car's position as given.
        this.setPos();
        // set the size of this Pane
        if (dir == MoveDir.HORIZONTAL) {
            // height ==1
            setWidth(getLen() * GameController.GRID_SIZE);
            setHeight(GameController.GRID_SIZE);
        }
        else if (dir == MoveDir.VERTICAL){
            // width ==1
            setWidth(GameController.GRID_SIZE);
            setHeight(getLen() * GameController.GRID_SIZE);
        }

        // set the appearance of this car
        Rectangle carRectangle = new Rectangle();
        getChildren().add(carRectangle);


        // set the style of this car
        if (dir == MoveDir.HORIZONTAL) {
            // height ==1
            carRectangle.setWidth(0.9*getLen() * GameController.GRID_SIZE);
            carRectangle.setHeight(0.9*GameController.GRID_SIZE);
        }
        else if (dir == MoveDir.VERTICAL){
            // width ==1
            carRectangle.setWidth(0.9*GameController.GRID_SIZE);
            carRectangle.setHeight(0.9*getLen() * GameController.GRID_SIZE);
        }
        // don't know how to center the color block


        // set the color by given.
        carRectangle.setFill(color);

    }


    public int getCarId() {
        return carId;
    }

    public int getGridX() {
        return gridX;
    }

    private  void setPos(){
        // refresh the location by the given position.
        relocate(getGridX()*GameController.GRID_SIZE,
                getGridY()*GameController.GRID_SIZE);

    }

    public void setGridX(int gridX) {
        this.gridX = gridX;
    }

    public int getGridY() {
        return gridY;
    }

    public void setGridY(int gridY) {
        this.gridY = gridY;
    }
    public MoveDir getDir() {
        return dir;
    }

    public int getLen() {
        return len;
    }
}
