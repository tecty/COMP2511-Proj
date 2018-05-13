package game;

import java.io.File;
import java.net.MalformedURLException;

import javax.activation.MimetypesFileTypeMap;
import javax.management.loading.MLet;

import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
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
    
    //real location for the car
//    private double oldX, oldY;


    public Car( MoveDir dir,
               int carId, int gridX, int gridY,
               int len,Paint color) throws MalformedURLException{
        // set the information of this car
        this.dir = dir;
        this.carId = carId;
        this.len = len;
        this.gridX = gridX;
        this.gridY = gridY;


        // set this car's position as given.
        this.refresh();

        // set the size of this car in the gird
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
//        Rectangle carRectangle = new Rectangle();
        Image img = new Image(new File("/game/car.jpg").toURI().toString());
        ImageView tile = new ImageView();
        tile.setImage(img);	
        tile.setCache(true);
        Rectangle2D carRectangle;
        if(dir == MoveDir.HORIZONTAL) {
        	carRectangle = new Rectangle2D(
        			GameController.GRID_SIZE * gridX, 
					GameController.GRID_SIZE * gridY, 
					GameController.GRID_SIZE * getLen(), 
					GameController.GRID_SIZE
			);
        }
        else {
        	carRectangle = new Rectangle2D(
        			GameController.GRID_SIZE * gridX, 
					GameController.GRID_SIZE * gridY, 
					GameController.GRID_SIZE , 
					GameController.GRID_SIZE * getLen()
			);
        }
        							
        tile.setViewport(carRectangle);
        
        
//        carRectangle.setFill(new ImagePattern(img, 0, 0, 1, 1, true));
        getChildren().add(tile);

        // set the style of this car
//        if (dir == MoveDir.HORIZONTAL) {
//            // height ==1
//            carRectangle.setWidth(getLen() * GameController.GRID_SIZE);
//            carRectangle.setHeight(GameController.GRID_SIZE);
//        }
//        else if (dir == MoveDir.VERTICAL){
//            // width ==1
//            carRectangle.setWidth(GameController.GRID_SIZE);
//            carRectangle.setHeight(getLen() * GameController.GRID_SIZE);
//        }
        // TODO: Style and center the car
        // don't know how to center the color block


        // set the color by given.
//        carRectangle.setFill(color);
    }

    
    public void refresh() {
        // show in screen by it's grid coordinate
        relocate(
                gridX * GameController.GRID_SIZE,
                gridY * GameController.GRID_SIZE);
    }


    public boolean isTarget() {
    	// target car has root id (0)
        return getCarId() == 0;
    }
    
    public int getCarId() {
        return carId;
    }

    /**
     * Refresh the position of this car by provide two coordinate
     * but only one coordinate would be use by the direction it is
     * set.
     * @param screenX X coordinate the function want to set to.
     * @param screenY Y coordinate the function want to set to.
     */
    @Override
    public  void relocate(double screenX, double screenY){
        // one direction should not be able to move
        if (dir == MoveDir.HORIZONTAL){
            // y is not change able;
            screenY = getGridY()* GameController.GRID_SIZE;
        }
        else {
            // vertical == x is not changeable
            screenX = getGridX()* GameController.GRID_SIZE;
        }
        // set the screen location
        super.relocate(screenX, screenY);
    }

    public void relocateByOffset(double offsetX, double offsetY){
        // relocate this block by the mouse offset
        // assume the collision is protected by higher levels function
        relocate(getGridX()*GameController.GRID_SIZE + offsetX,
                 getGridY()*GameController.GRID_SIZE + offsetY);
    }



    public void setGrid(int gridX, int gridY){
        // set the grid and keep the Invariant:
        // in Horizontal car y = y_0
        // in Vertical car   x = x_0
        if (dir == MoveDir.HORIZONTAL){
            // only change x
            this.gridX = gridX;
        }
        else {
            // else: VERTICAL
            // only change y
            this.gridY = gridY;
        }
    }

    public int getGridX() {
        return gridX;
    }

    public int getGridY() {
        return gridY;
    }

    public MoveDir getDir() {
        return dir;
    }

    public int getLen() {
        return len;
    }

}
