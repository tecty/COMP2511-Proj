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
    // the move counter of this game
    private int moveCounter;


    @FXML
    private void initialize() {
        // move counter of each game should be set to 0
        moveCounter = 0;
        // set up all the grid
        for (int x = 0; x < 6; x++) {
            for (int y = 0; y < 6; y++) {
                Grid grid = new Grid(x, y);
                // store this gird into board.
                board[x][y] = grid;
                // add this grid to group
                gridGroup.getChildren().add(grid);
            }
        }

        // add the group to the pane and group of car
        // to show in the scene
        rootPane.getChildren().addAll(gridGroup, carGroup);

        // set the car 0
        makeCar(MoveDir.HORIZONTAL,
                0, 0, 2, 2, Color.RED);
        makeCar(MoveDir.VERTICAL,
                1, 2, 2, 2, Color.BLUE);
    }

    private void makeCar(MoveDir dir,
                         int carId, int gridX,
                         int gridY, int len, Paint color){
        // pass through the argument
        Car thisCar = new Car(dir, carId, gridX,gridY,len,color);
        // add to the group to show
        carGroup.getChildren().add(thisCar);

        // reference it from the board
        refreshCarInBoard(thisCar,gridX,gridY);

        //set the function for mouse clicking on
        thisCar.setOnMousePressed(mouseEvent -> {
            // only record the original mouse position
            mouseOriginX = mouseEvent.getSceneX();
            mouseOriginY = mouseEvent.getSceneY();
        });

        //set the function for mouse dragging
        thisCar.setOnMouseDragged(mouseEvent -> {
            // local var of mouse offset to remove strange situation
            // by not fetch mouse position twice
            double offsetX = mouseEvent.getSceneX()-mouseOriginX;
            double offsetY = mouseEvent.getSceneY()-mouseOriginY;

            if (isCollision(thisCar,offsetX,offsetY)){
                // couldn't move if there is an collision
                return;
            }
            // relocate this car's position in GUI
            thisCar.relocateByOffset(offsetX,offsetY);
        });
        thisCar.setOnMouseReleased(mouseEvent -> {
            double mouseOffsetX = mouseEvent.getSceneX() - mouseOriginX;
            double mouseOffsetY = mouseEvent.getSceneY() - mouseOriginY;

            // couldn't be correctly release if there is a collision
            if (isCollision(thisCar,mouseOffsetX,mouseOffsetY))
                return;
            // calculate the gird offset
            int gridOffsetX = getGridOffset(mouseOffsetX);
            int gridOffsetY = getGridOffset(mouseOffsetY);

            /*
             * move to its gird
             */
            // refresh the board reference of the car in board.
            // add up move counter if the car position is changed
            refreshCarInBoard(thisCar,
                    thisCar.getGridX()+ gridOffsetX,
                    thisCar.getGridY()+ gridOffsetY);
            // refresh the position of the car in GUI
            thisCar.refresh();
        });
    }

    private void refreshCarInBoard(Car car,
                                   int gridX,
                                   int gridY) {
        // remove the old board record
        if (car.getDir() == MoveDir.HORIZONTAL){
            // value in board change in x

            // protect the gridX over boundary with mouse over
            // boundary
            if (gridX<0)
                gridX = 0;
            if (gridX+car.getLen()>6)
                gridX = 6-car.getLen();

            // clean the old board record
            for (int i = car.getGridX();
                 i < car.getGridX()+car.getLen(); i++) {
                // keep the invariant that y = y_0
                board[i][car.getGridY()].setCar(null);
            }
            // add new board record
            for (int i = gridX;
                 i < gridX + car.getLen() ; i++) {
                // keep the invariant that y = y_0
                board[i][car.getGridY()].setCar(car);
            }
        }
        else{
            // else: move Vertical

            // protect the gridX over boundary with mouse over
            // boundary
            if (gridY<0)
                gridY = 0;
            if (gridY+car.getLen()>6)
                gridY = 6-car.getLen();

            // value in board change in y
            for (int i = car.getGridY();
                 i < car.getGridY()+car.getLen(); i++) {
                // keep the invariant that y = y_0
                board[car.getGridX()][i].setCar(null);
            }
            // add new board record
            for (int i = gridY;
                 i < gridY + car.getLen(); i++) {
                // keep the invariant that y = y_0
                board[car.getGridX()][i].setCar(car);
            }
        }

        if (gridX != car.getGridX()|| gridY!= car.getGridY()){
            // set the car's new position
            car.setGrid(gridX,gridY);
            // change the car's stage would add up move counter
            addMoveCounter();
        }
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
        if (offset>0 && offset>0.5){

            return (int ) (offset +0.5 * this.GRID_SIZE)/ this.GRID_SIZE;
        }
        else if (offset<0 && offset< -0.5){
            return (int ) (offset -0.5 * this.GRID_SIZE)/ this.GRID_SIZE;
        }
        return 0;
    }

    public boolean isCollision(Car car, double offsetX, double offsetY){
        // the range of the largest offset can move of this car
        // in current board state
        double offsetMax, offsetMin;

        if (car.getDir() == MoveDir.HORIZONTAL){
            // y = y_0
            // the minimum and maximum of this offset range
            offsetMin = (0-car.getGridX())             *GRID_SIZE;
            offsetMax = (6-car.getGridX()-car.getLen())*GRID_SIZE;

            for (int x = car.getGridX(); x >=0; x--) {
                // try to reach min
                if (board[x][car.getGridY()].hasCar(car)){
                    // some block lock the position before the boundary
                    // reset the min and break
                    offsetMin = (x - car.getGridX() + 1)* GRID_SIZE;
                    break;
                }
            }
            for (int x = car.getGridX()+car.getLen(); x < 6; x++) {
                // try to reach max
                if(board[x][car.getGridY()].hasCar(car)){
                    // recalculate the max and break
                    offsetMax = (x - car.getGridX() -1)*GRID_SIZE;
                }
            }
            // the offset x shouldn't be outside the range
            if (offsetMax<offsetX || offsetX< offsetMin)
                return  true;
        }else{
            // Vertical move x = x_0
            // the minimum and maximum of this offset range
            offsetMin = (0-car.getGridY())             *GRID_SIZE;
            offsetMax = (6-car.getGridY()-car.getLen())*GRID_SIZE;

            for (int y = car.getGridY(); y >=0; y--) {
                // try to reach min
                if (board[car.getGridX()][y].hasCar(car)){
                    // some block lock the position before the boundary
                    // reset the min and break
                    offsetMin = (y - car.getGridY() + 1)* GRID_SIZE;
                    break;
                }
            }
            for (int y = car.getGridY()+car.getLen(); y < 6; y++) {
                // try to reach max
                if(board[car.getGridX()][y].hasCar(car)){
                    // recalculate the max and break
                    offsetMax = (y - car.getGridY() -1)*GRID_SIZE;
                }
            }
            // the offset x shouldn't be outside the range
            if (offsetMax<offsetY || offsetY< offsetMin)
                return true;
        }

        return false;
    }


    public int getMoveCounter() {
        return moveCounter;
    }

    public void addMoveCounter() {
        this.moveCounter ++;
    }


//    TODO: Pass in a valid game
//    private void  showGame(Game game){
//
//    }
}
