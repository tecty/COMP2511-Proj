package game;

import javafx.fxml.FXML;
import javafx.scene.Group;
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

    }

    private void  showGame(Game game){

    }
}
