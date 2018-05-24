package game;

import java.io.Serializable;
import java.util.ArrayList;

import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Car extends StackPane implements Serializable{
	private static final long serialVersionUID = 1L;
	
	// the actual car move in each grid
    final private MoveDir dir;
    final private int carId;
    private int gridX;
    private int gridY;
    // the length of this car
    final private int len;
    
    private String sound;

    //animation stuff
//    Pane img = null;
//    Timeline timelineOn;

    public Car( MoveDir dir,
               int carId, int gridX, int gridY,
               int len) {
        // set the information of this car
        this.dir = dir;
        this.carId = carId;
        this.len = len;
        this.gridX = gridX;
        this.gridY = gridY;
        
        this.sound = RandomSelector.selectSound(len);

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
        Pane img = new Pane();
        Rectangle carRectangle = new Rectangle();
        
        getChildren().addAll(carRectangle, img);
        
    	String chooseImg = "-fx-background-image: url(\"/img/" + RandomSelector.selectImg(getLen(), getDir(), isTarget())+"\");-fx-background-repeat: no-repeat;-fx-background-size: contain;";
    	
    	img.setStyle(chooseImg);
        
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
        // TODO: Style and center the car
        // don't know how to center the color block

        // set the color by given.
        carRectangle.setFill(Color.TRANSPARENT);
//
//        //set up animation property
//        KeyFrame startFadeOut = new KeyFrame(Duration.seconds(0.0), new KeyValue(img.opacityProperty(), 1.0));
//        KeyFrame endFadeOut = new KeyFrame(Duration.seconds(0.5), new KeyValue(img.opacityProperty(), 0.0));
//        timelineOn = new Timeline(startFadeOut, endFadeOut);
//        timelineOn.setAutoReverse(true);
//        timelineOn.setCycleCount(Animation.INDEFINITE);
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

    public void dumpCar(){
        System.out.println("Car "+ getCarId()+
                " ("+getGridX()+","+getGridY()+")" + " Length: "+ getLen());
    }
    public String getSound() {
    	return sound;
    }

    public puzzleModel.Car getAlgorithmCar(){
        ArrayList<puzzleModel.Coordinate> paths = new ArrayList<puzzleModel.Coordinate>();
        if(this.getDir()==MoveDir.HORIZONTAL) paths.add(new puzzleModel.Coordinate( this.getGridY(), this.getGridX(), this.getGridY(), this.getGridX()+this.getLen()-1));
        else paths.add(new puzzleModel.Coordinate(gridY, gridX, gridY+this.getLen()-1, gridX));
        puzzleModel.Car algCar = new puzzleModel.Car(this.getCarId(), paths);
//        System.out.println("alg car generated");
        return algCar;
    }
//
//    public void flash() {
//        timelineOn.play();
//    }
//
//    public void stopFlash() {
//        timelineOn.stop();
//        img.setOpacity(1.0);
//    }
}
