package game;

import javafx.scene.layout.StackPane;
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
    private double mouseX, mouseY;
    private double oldX, oldY;


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
            carRectangle.setWidth(getLen() * GameController.GRID_SIZE);
            carRectangle.setHeight(GameController.GRID_SIZE);
        }
        else if (dir == MoveDir.VERTICAL){
            // width ==1
            carRectangle.setWidth(GameController.GRID_SIZE);
            carRectangle.setHeight(getLen() * GameController.GRID_SIZE);
        }
        // don't know how to center the color block


        // set the color by given.
        carRectangle.setFill(color);
        
        //set the function for mouse clicking on
        setOnMousePressed(e -> {
        	this.mouseX = e.getSceneX()-gridX*GameController.GRID_SIZE;
        	this.mouseY = e.getSceneY()-gridY*GameController.GRID_SIZE;
        });
        
        //set the function for mouse dragging
        setOnMouseDragged(e -> {
            int mouseGridX, mouseGridY;
            // calculate the suppose grid x and grid y
            // by mouse position
            mouseGridX = (int )(e.getSceneX() / GameController.GRID_SIZE);
            mouseGridY = (int )(e.getSceneY() / GameController.GRID_SIZE);

            System.out.println("Axis: "+ mouseGridX +
                    " isCollision " + isCollision(mouseGridX,this.gridY));

            if(this.dir == MoveDir.HORIZONTAL){
                if (!isCollision(mouseGridX,this.gridY)){
                    // no collision, relocate the block
                    relocate(e.getSceneX() - this.mouseX,
                            this.gridY * GameController.GRID_SIZE);
                }
            }
            else{
                if (!isCollision(this.gridX,mouseGridY)){
                    // no collision, relocate the block
                    relocate(this.gridX * GameController.GRID_SIZE,
                            e.getSceneY() - this.mouseY);
                }
            }


        });

    }

    public boolean isCollision(int gridX, int gridY){
        if (gridX <= 0  || gridX + this.getLen()>6 ||
            gridY <= 0  || gridY+ this.getLen()>6) {
            return true;
        }
        return false;
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
    
    public void move (int gridX, int gridY) {
        //limit


    	oldX = gridX * GameController.GRID_SIZE;
    	oldY = gridY * GameController.GRID_SIZE;
    	relocate(oldX, oldY);    	
    }
}
