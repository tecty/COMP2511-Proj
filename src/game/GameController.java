package game;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.util.ArrayList;

public class GameController {
    // create game in this controller
    @FXML
    Pane rootPane;

    // reference to the board
    private Grid[][] board = new Grid[6][6];

    // store all the car in this game
    Group carGroup = new Group();
    Group gridGroup = new Group();

    // the size of each grid
    public final static int GRID_SIZE = 50;

    // when a click on a car, it would set
    private double mouseX, mouseY;


    @FXML
    private void initialize(){
        // set up all the grid
        for (int x = 0; x < 6; x++) {
            for (int y = 0; y < 6; y++) {
                Grid grid = new Grid(x,y);
                // store this gird into board.
                board[x][y] = grid;
                // add this grid to group
                gridGroup.getChildren().add(grid);
            }
        }

        // add the group to the pane and group of car
        // to show in the scene
        rootPane.getChildren().addAll(gridGroup,carGroup);

        // set the car 0
        makeCar(MoveDir.HORIZONTAL,
                0,0,2,2,Color.RED);

    }

    private void makeCar(MoveDir dir,
                         int carId, int gridX,
                         int gridY, int len, Paint color){
        // pass through the argument
        Car thisCar = new Car(dir, carId, gridX,gridY,len,color);
        // add to the group to show
        carGroup.getChildren().add(thisCar);

//        board[1][1].setCar(thisCar);
        
        // reference it from the board
        if(dir == MoveDir.VERTICAL){
            for (int y = 0; y < len; y++) {
                // only need to change y
                board[gridX][gridY+y].setCar(thisCar);
            }
        }else if (dir == MoveDir.HORIZONTAL){
            for (int x = 0; x < len; x++) {
                // only need to change x
                board[gridX +x ][gridY].setCar(thisCar);
            }
        }

        //set the function for mouse clicking on
        thisCar.setOnMousePressed(e -> {
            this.mouseX = e.getSceneX();
            this.mouseY = e.getSceneY();
        });

        //set the function for mouse dragging
        thisCar.setOnMouseDragged(e -> {
            int mouseGridX = (int)(e.getSceneX() - this.mouseX + thisCar.getGridX()*this.GRID_SIZE)/this.GRID_SIZE;
            int mouseGridY = 0;
            // calculate the suppose grid x and grid y
            // by mouse position
            if(thisCar.getDir() == MoveDir.HORIZONTAL){
                if (!isCollision(thisCar, mouseGridX, thisCar.getGridY())){
                    // no collision, relocate the block
                    thisCar.relocate(mouseGridX * this.GRID_SIZE,
                            thisCar.getGridY() * this.GRID_SIZE);
                }
            }
            else{
                if (!isCollision(thisCar, thisCar.getGridX()*this.GRID_SIZE,mouseGridY)){
                    // no collision, relocate the block

                    thisCar.relocate(thisCar.getGridX() * GameController.GRID_SIZE,
                            e.getSceneY() - this.mouseY+ thisCar.getGridY()*this.GRID_SIZE);
                }
            }


        });
        thisCar.setOnMouseReleased(mouseEvent -> {
            int newX = toBoard(thisCar.getLayoutX());
            int newY = toBoard(thisCar.getLayoutY());

            tryMove(thisCar, newX, newY);
        });
    }

    private int toBoard(double position){
        // return the number of the grid on the board
        return (int ) (position +0.5 * this.GRID_SIZE)/ this.GRID_SIZE;
    }

    public boolean isCollision(Car car, int gridX, int gridY){
        // bug? 
        if (gridX < 0  || gridX + car.getLen()>=6 ||
                gridY < 0  || gridY+ car.getLen()>=6) {
            return true;
        }
        return false;
    }


    private void tryMove(Car car, int gridX, int gridY){
        // call when try to move

        // TODO: this functionality
        // try assign this car to the position it would belong
        // identify whether it has collision



        // remove the old gird information
        for (int i = 0; i < car.getLen(); i++) {
            board[car.getGridX()][car.getGridY()+i].setCar(null);
        }


        // set the car's coordinate to board
        car.setPos(gridX*this.GRID_SIZE,gridY*this.GRID_SIZE);
        // refresh the board's information
        for (int i = 0; i < car.getLen(); i++) {
            board[car.getGridX()][car.getGridY()+i].setCar(car);
        }

    }

    private void  showGame(Game game){

    }
}
