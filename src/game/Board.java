package game;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import save.Level;
import setting.Setting;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Stack;

public class Board extends Pane{
    // store all the car in this game
    Group carGroup = new Group();
    Group gridGroup = new Group();

    // reference to the gridBoard
    private Grid[][] gridBoard = new Grid[6][6];

    // when a click on a car, it would set
    private double mouseOriginX, mouseOriginY;
    // cleaner code to handle collision
    // record the mouse offset of this drag
    private double mouseOffsetX, mouseOffsetY;

    // the range of the offset of mouse movement can achieve
    // for clicked particular car
    private double offsetMax, offsetMin;

    // store the moved car id of each step
    Stack<Movement> history = new Stack<>();

    //step counter
    //variables prepared for stepCounter
    IntegerProperty steps = new SimpleIntegerProperty();
    
    // current level the board is responsible for 
    int currentLevel;
    public Board(){
        // add group into the region on the screen
//        this.getChildren().add(gridGroup);
//        this.getChildren().add(carGroup);
    }

    public Group getCarGroup(){
        return carGroup;
    }
    public Group getGridGroup(){
        return gridGroup;
    }

    public void reset(int level){

    }
    public void reset(){

    }

    private void setBoard() {
        // set up all the grid
        for (int x = 0; x < 6; x++) {
            for (int y = 0; y < 6; y++) {
                Grid grid = new Grid(x, y);
                // store this gird into gridBoard.
                gridBoard[x][y] = grid;
                // add this grid to group
                gridGroup.getChildren().add(grid);
            }
        }
    }
    private void addCars() throws MalformedURLException {
        if(Setting.save ==null) System.out.println("slot empty");
        Level currLevel = Setting.save.getLevel(currentLevel);
        if(currLevel==null) {
            System.out.println("empty");
            System.exit(1);
        }
        for(Car each : currLevel.getCars()) {
            makeCar(
                    each.getDir(),
                    each.getCarId(),
                    each.getGridX(),
                    each.getGridY(),
                    each.getLen()
            );
        }
    }

    //check if the car making is valid
    private boolean validPosition(int gridX, int gridY, int len, MoveDir dir) {
        if(dir==MoveDir.HORIZONTAL) {
            for(int pos = gridX; pos < gridX+len; pos ++) {
                if(gridBoard[pos][gridY].getCar()!=null) return false;
            }
        }
        else {
            for(int pos = gridY; pos < gridY+len; pos ++) {
                if(gridBoard[gridX][pos].getCar()!=null) return false;
            }
        }
        return true;
    }

    private void makeCar(MoveDir dir,
                         int carId, int gridX,
                         int gridY, int len) throws MalformedURLException{
        //if the car cannot be made, ignore this car
        if(!validPosition(gridX, gridY, len, dir)) return;
        // pass through the argument
        Car thisCar = new Car(dir, carId, gridX,gridY,len);
        // add to the group to show
        carGroup.getChildren().add(thisCar);

        // reference it from the board
        refreshCarInBoard(thisCar,gridX,gridY);

        //set the function for mouse clicking on
        thisCar.setOnMousePressed(mouseEvent -> {
            // only record the original mouse position
            mouseOriginX = mouseEvent.getSceneX();
            mouseOriginY = mouseEvent.getSceneY();

            // calculate the offset range of current car
            getOffsetRange(thisCar);
        });

        //set the function for mouse dragging
        thisCar.setOnMouseDragged(mouseEvent -> {
            // global var for record mouse offset
            // by not fetch mouse position twice
            mouseOffsetX = mouseEvent.getSceneX()-mouseOriginX;
            mouseOffsetY = mouseEvent.getSceneY()-mouseOriginY;

            handleCollision(thisCar);

            // relocate this car's position in GUI
            thisCar.relocateByOffset(mouseOffsetX,mouseOffsetY);
        });
        thisCar.setOnMouseReleased(mouseEvent -> {
            mouseOffsetX = mouseEvent.getSceneX() - mouseOriginX;
            mouseOffsetY = mouseEvent.getSceneY() - mouseOriginY;

            // couldn't be correctly release if there is a collision
            if (handleCollision(thisCar))
                return;
            // calculate the gird offset
            int gridOffsetX = getGridOffset(mouseOffsetX);
            int gridOffsetY = getGridOffset(mouseOffsetY);

            /*
             * move to its gird
             */
            // refresh the board reference of the car in board.
            // add up move counter if the car position is changed
            moveCar(thisCar,
                    thisCar.getGridX()+ gridOffsetX,
                    thisCar.getGridY()+ gridOffsetY);
            // force to refresh the position of the car in GUI
            thisCar.refresh();

//            // check whether the game is finished
//            if(checkLevelClear(thisCar)) {
//                try {
//                    handleLevelClear();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
        });
    }
    /**
     * Function use to move a car in the board. Handle user interaction
     * @param car
     * @param gridX
     * @param gridY
     */
    private void moveCar(Car car, int gridX, int gridY) {
        /*
         * simply pass the argument, cause the low level function
         * has done the protect job.
         */

        /*
         * protect by not moving car then:
         * Record the movement in the stack by the car's moving restriction
         * Since the car could only move in one direction, we not need to record
         * the coordinate but simply record the relative movement.
         */
        if (car.getDir() == MoveDir.HORIZONTAL) {
            // only can change x
            gridY = car.getGridY();
            if (car.getGridX() == gridX) {
                return;
            }
            history.push(new Movement(car, gridX - car.getGridX()));
        }
        else if (car.getDir() == MoveDir.VERTICAL) {
            // only can change y
            gridX = car.getGridX();
            if (car.getGridY() == gridY) {
                return;
            }
            history.push(new Movement(car, gridY - car.getGridY()));
        }


        if(car.getDir() == MoveDir.HORIZONTAL) {

        }
        else if (car.getDir() == MoveDir.VERTICAL) {

        }
        // refresh the coordinate in the board
        refreshCarInBoard(car, gridX, gridY);


        // change the car's stage would add up move counter
        steps.set(steps.get()+1);

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
            for (int x = car.getGridX();
                 x < car.getGridX()+car.getLen(); x++) {
                // keep the invariant that y = y_0
                gridBoard[x][car.getGridY()].setCar(null);
            }
            // add new board record
            for (int x = gridX;
                 x < gridX + car.getLen() ; x++) {
                // keep the invariant that y = y_0
                gridBoard[x][car.getGridY()].setCar(car);
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
            for (int y = car.getGridY();
                 y < car.getGridY()+car.getLen(); y++) {
                // keep the invariant that y = y_0
                gridBoard[car.getGridX()][y].setCar(null);
            }
            // add new board record
            for (int y = gridY;
                 y < gridY + car.getLen(); y++) {
                // keep the invariant that y = y_0
                gridBoard[car.getGridX()][y].setCar(car);
            }
        }

        if (gridX != car.getGridX()|| gridY!= car.getGridY()){
            // set the car's new position
            car.setGrid(gridX,gridY);

        }
    }

    //these functions are for checking if the level is cleared
    private boolean checkLevelClear(Car car){
        if(car.isTarget() && car.getGridX()==0) return true;
        return false;
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

            return (int ) (offset +0.5 * GameController.GRID_SIZE)/ GameController.GRID_SIZE;
        }
        else if (offset<0 && offset< -0.5){
            return (int ) (offset -0.5 * GameController.GRID_SIZE)/ GameController.GRID_SIZE;
        }
        return 0;
    }

    public boolean handleCollision(Car car){


        if (car.getDir() == MoveDir.HORIZONTAL){
            // y = y_0

            // reset the offset base on the range
            if (mouseOffsetX> offsetMax)
                mouseOffsetX = offsetMax;
            if (mouseOffsetX< offsetMin)
                mouseOffsetX = offsetMin;

        }else{
            // Vertical move x = x_0

            // the offset y shouldn't be outside the range
            if (mouseOffsetY> offsetMax)
                mouseOffsetY = offsetMax;
            if (mouseOffsetY< offsetMin)
                mouseOffsetY = offsetMin;
        }

        return false;
    }


    private void getOffsetRange(Car car){
        // Logic : [leftBoundary ,(minGridCar,) car , (maxGridCar,) rightBoundary]

        if (car.getDir() == MoveDir.HORIZONTAL){
            // setup the leftBoundary and rightBoundary
            offsetMin = -car.getGridX()                   * GameController.GRID_SIZE;
            offsetMax = (6 - car.getGridX()- car.getLen())* GameController.GRID_SIZE;
            // get the range of x

            // find the min
            for (int x = 0; x < 6; x++) {
                if (gridBoard[x][car.getGridY()].getCar() == car)
                    break;
                if (gridBoard[x][car.getGridY()].getCar() != null ){
                    // set the new min offset
                    offsetMin = -(car.getGridX() -x-1)*GameController.GRID_SIZE;
                }
            }

            // find the max
            for (int x = car.getGridX() + car.getLen(); x < 6; x++) {
                // car length = 2 and star at 2, then search start from 4
                // but board[4] wouldn't contain this car
                if (gridBoard[x][car.getGridY()].getCar()!= null){
                    // set the offset max
                    offsetMax = (x - car.getGridX()- car.getLen() -1)*GameController.GRID_SIZE;
                    // not need to find the next car would be collision
                    break;
                }
            }

        }else{
            // Vertical: get the range of y
            // setup the topBoundary and bottomBoundary
            offsetMin = -car.getGridY()                   * GameController.GRID_SIZE;
            offsetMax = (6 - car.getGridY()- car.getLen())* GameController.GRID_SIZE;
            // get the range of x

            // find the min
            for (int y = 0; y < 6; y++) {
                if (gridBoard[car.getGridX()][y].getCar() == car)
                    break;
                if (gridBoard[car.getGridX()][y].getCar() != null){
                    // set the new min offset
                    offsetMin = -(car.getGridY() -y-1)*GameController.GRID_SIZE;
                }
            }

            // find the max
            for (int y = car.getGridY() + car.getLen(); y < 6; y++) {
                // car length = 2 and star at 2, then search start from 4
                // but board[4] wouldn't contain this car
                if (gridBoard[car.getGridX()][y].hasCar(car)){
                    // set the offset max
                    offsetMax = (y - car.getGridY()- car.getLen() -1)*GameController.GRID_SIZE;
                    // not need to find the next car would be collision
                    break;
                }
            }
        }
    }

    private void dumpState(Car car){
        // dump the current state of this board
        System.out.println("car"+ car.getCarId()+ " has grid "+ car.getGridX() + " ,"+ car.getGridY());
        System.out.println("OffsetRange "+ offsetMin/GameController.GRID_SIZE + " ," + offsetMax/GameController.GRID_SIZE);
        System.out.println(steps.get());
        printBoard();
        System.out.println();
    }

    private void  printBoard(){
        // test function to print this board
        for (int y = 0; y < 6; y++) {
            for (int x = 0; x < 6; x++) {
                if (gridBoard[x][y].getCar()== null)
                    System.out.print("#");
                else
                    System.out.print(gridBoard[x][y].getCar().getCarId());
            }
            System.out.print("\n");
        }
    }
    public void undo(){
        //cannot undo at the stat of game
        if(history.isEmpty()) return;

        // pop a movement in the stack
        Movement move = history.pop();
        if (move.getCar().getDir() == MoveDir.HORIZONTAL) {
            // only change x
            refreshCarInBoard(move.getCar(),
                    move.getCar().getGridX() - move.getMovement(),
                    move.getCar().getGridY());
        }
        else if (move.getCar().getDir() == MoveDir.VERTICAL) {
            // only change y
            refreshCarInBoard(move.getCar(),
                    move.getCar().getGridX(),
                    move.getCar().getGridY()-move.getMovement());
        }

        move.getCar().refresh();
    }
    public IntegerProperty getStepProperty(){
        return steps;
    }
}
