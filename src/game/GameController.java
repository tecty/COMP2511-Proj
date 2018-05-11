package game;

import java.awt.event.MouseEvent;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

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
    private double mouseOriginX, mouseOriginY;


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
        thisCar.setOnMousePressed(mouseEvent -> {
            // only record the original mouse position
            mouseOriginX = mouseEvent.getSceneX();
            mouseOriginY = mouseEvent.getSceneY();
            System.out.println("mouse offset x"+ mouseOriginX +" , "+ mouseOriginY);
        });

        //set the function for mouse dragging
        thisCar.setOnMouseDragged(mouseEvent -> {
            // TODO: Handle the collision
            thisCar.relocateByOffset(
                    mouseEvent.getSceneX()-mouseOriginX,
                    mouseEvent.getSceneY()-mouseOriginY
            );

        });
        thisCar.setOnMouseReleased(mouseEvent -> {
            // calculate the gird offset,
            int gridOffsetX = getGridOffset(
                    mouseEvent.getSceneX() - mouseOriginX
            );
            int gridOffsetY = getGridOffset(
                    mouseEvent.getSceneY() - mouseOriginY
            );



        });
    }

    
    //this function will crash currently
    private void levelClear(Car car)  {
        // get the current Stage
        Stage primaryStage = (Stage) car.getScene().getWindow();
        // try to load level select scene
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("../levelClear/levelClear.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("level cleared");
        // checkout to level select scene
        primaryStage.setScene(new Scene(root));
    }

    /**
     * Return the actual grid offset of the car by that move and
     * that direction.
     * @param offset The offset has dragged by mouse;
     * @return Integer offset of the grid.
     */
    private int getGridOffset(double offset){
        // return the number of the grid on the board
        return (int ) (offset +0.5 * this.GRID_SIZE)/ this.GRID_SIZE;
    }

    public boolean isCollision(Car car, double screenX, double screenY){
        return false;
    }


    private void tryMove(Car car, MouseEvent moueEvent){
        // call when dragging the car.

        // handle collision

        // relocate the block

    }

    private void move(Car car, MouseEvent mouseEvent){
        // calling when the mouse is released and car is going to settle
        // move to its gird

        // add up move counter
    }


//    TODO: Pass in a valid game
//    private void  showGame(Game game){
//
//    }
}
