package game;

import javafx.scene.shape.Rectangle;

public class Car extends Rectangle {


    // the actual car move in each grid
    final private MoveDir dir;
    final private int id;
    private int gridX;
    private int gridY;


    public Car(MoveDir dir, int id, int gridX, int gridY){
        // set the information of this car
        this.dir = dir;
        this.id = id;
        setGridX(gridX);
        setGridY(gridY);

    }


    public int getCarId() {
        return id;
    }


    public int getGridX() {
        return gridX;
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

}
