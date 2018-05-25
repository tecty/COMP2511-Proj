package game;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import puzzleModel.Algorithm;
import puzzleModel.Board;
import save.Level;
import setting.Setting;

import java.util.ArrayList;
import java.util.Stack;

/**
 * 
 */
public class BoardController {
    @FXML
    Pane root;
    
    //animation class
    private DummyCar dummy;
    
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
    private Stack<Movement> history = new Stack<>();


    // current level the board is responsible for
    private Level currentLevel;

    // a handle to main controller
    private GameController mainController;


    // car list for the
    private ArrayList<Car> currStat = new ArrayList<>();

    // boolean for the hint state
    private boolean onHint = false;

    /**
     * Initialise the board by adding two groups of elements.
     */
    @FXML private void initialize(){
        // load all the grid
        setBoard();
        root.getChildren().addAll(gridGroup);
        root.getChildren().addAll(carGroup);
        gridGroup.toBack();
    }


    /**
     * Reset by a given level object.
     * @param level The level object this board is going to reset to.
     */
    public void reset(Level level){
    	cleanHint();
        if(currentLevel == level){
            // not destroy the old car to prevent
            // the car from shifting colors
            // by undo all user's steps
            while (!history.isEmpty()){
                undo();
            }
        }
        else {

            // reset the current stat
            currStat.removeAll(currStat);

            // reset to a given level
            currentLevel = level;
            // remove all the cars in previous level in GUI
            carGroup.getChildren().removeAll(carGroup.getChildren());
            // remove all the car record in grid
            for (int x = 0; x < 6; x++) {
                for (int y = 0; y < 6; y++) {
                    gridBoard[x][y].setCar(null);
                }
            }
            // remove all the record moving history
            history.removeAllElements();

            // add back all the car that belong to this level
            for(Car each : currentLevel.getCars()) {
                makeCar(
                        each.getDir(),
                        each.getCarId(),
                        each.getGridX(),
                        each.getGridY(),
                        each.getLen()
                );
            }
        }
        // reset the step count
        mainController.resetSteps();
    }

    /**
     * Set up this board by creating all fresh grid object.
      */
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

    /**
     * Check whether that position is valid for adding a car.
     * @param gridX Position X need to be check.
     * @param gridY Position Y need to be check.
     * @param len Potential car length.
     * @param dir Potential car direction.
     * @return Whether is valid to add that car.
     */
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

    /**
     * Make a car into the system by providing it's information.
     * @param dir The direction of that car.
     * @param carId The carID.
     * @param gridX The car's position X.
     * @param gridY The car's position Y.
     * @param len The car's length.
     */
    private void makeCar(MoveDir dir,
                         int carId, int gridX,
                         int gridY, int len){
        //if the car cannot be made, ignore this car
        if(!validPosition(gridX, gridY, len, dir)) return;
        // pass through the argument
        Car thisCar = new Car(dir, carId, gridX,gridY,len);
        // add to the group to show
        carGroup.getChildren().add(thisCar);

        // add this for solving
        currStat.add(thisCar);

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

            // check whether the game is finished
            if(checkLevelClear(thisCar)) {
                mainController.checkoutFinishPrompt();
            }

            if (onHint && !Setting.save.isExpertMode()){
                // if an not expert, pay for a hint,

                // then hint till this session end  end.
                hintNextStep();
            }
        });
    }
    /**
     * Function use to move a car in the board. Handle user interaction
     * @param car The car need to move.
     * @param gridX Position X need to be move to.
     * @param gridY Position Y need to be move to.
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

        // refresh the coordinate in the board
        refreshCarInBoard(car, gridX, gridY);

        cleanHint();

        // change the car's stage would add up move counter
        mainController.addSteps();
    }


    /**
     * Refresh the grid's record for a given car.
     * @param car The car need to refresh the record.
     * @param gridX X position need to be locate to.
     * @param gridY Y position need to be locate to.
     */
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

    /**
     * these functions are for checking if the level is cleared
     * @param car The car is changed in this move.
     * @return Whether this level is cleared.
     */
    private boolean checkLevelClear(Car car){
        if(car.isTarget() && car.getGridX()==4) return true;
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

    /**
     * Actual function directly handle collision from mouse.
     * @param car The car mouse is dragging.
     * @return Whether there is a collision.
     */
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

    /**
     * Calculate the offset range of a car in GUI.
     * @param car The car's offset need to be calculated.
     */
    private void getOffsetRange(Car car){
        // Logic : [leftBoundary ,(minGridCar,) car , (maxGridCar,) rightBoundary]

        if (car.getDir() == MoveDir.HORIZONTAL){
            // set the range it initially gain
            offsetMin = -0; offsetMax=+0;
            // find the min by gaining range
            for (int x = car.getGridX()-1; x >= 0; x--) {
                if(gridBoard[x][car.getGridY()].getCar() ==null)
                    offsetMin -= GameController.GRID_SIZE;
                else break;
            }
            // find max by gaining range.
            for (int x = car.getGridX()+car.getLen(); x < 6; x++) {
                if(gridBoard[x][car.getGridY()].getCar() == null)
                    offsetMax += GameController.GRID_SIZE;
                else break;
            }
        }else{
            // Vertical: get the range of y
            // set the range it initially gain
            offsetMin = -0; offsetMax=+0;
            // find the min by gaining range
            for (int y = car.getGridY()-1; y >= 0; y--) {
                if(gridBoard[car.getGridX()][y].getCar() ==null)
                    offsetMin -= GameController.GRID_SIZE;
                else break;
            }
            // find max by gaining range.
            for (int y = car.getGridY()+car.getLen(); y < 6; y++) {
                if (gridBoard[car.getGridX()][y].getCar() == null)
                    offsetMax += GameController.GRID_SIZE;
                else break;
            }
        }
    }

    /**
     * Dump the sate of this board for the purpose of debugging.
     * @param car The Car is changing, which needs to be print too.
     */
    private void dumpState(Car car){
        // dump the current state of this board
        System.out.println("car"+ car.getCarId()+ " has grid "+ car.getGridX() + " ,"+ car.getGridY());
        System.out.println("OffsetRange "+ offsetMin/GameController.GRID_SIZE + " ," + offsetMax/GameController.GRID_SIZE);
        System.out.println(mainController.getSteps());
        printBoard();
        System.out.println();
    }

    /**
     * Print the current board for the purpose of debugging.
     */
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

    /**
     * Handle the undo instruction by user.
     */
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

    /**
     * Inject main controller so this board can call back some function.
     * @param mainController The main controller.
     */
    protected void injectMainController(GameController mainController){
        this.mainController = mainController;
    }

    /**
     * Handle user's instruction to show a hint in board.
     */
    public void hintNextStep() {
    	onHint = true;
        // initialise the dummy car object
        dummy = new DummyCar();
        // show the dummy car
        root.getChildren().add(dummy);
        //the dummy should not cover the real cars
        dummy.toFront();
        // car group should be most front
        carGroup.toFront();

        onHint = true;
        ArrayList<puzzleModel.Car> cars = new ArrayList<>();
        if(currStat.isEmpty()) System.out.println("empty");
        for(Car each : currStat) {
            cars.add(each.getAlgorithmCar());
        }

        //summarize the current board situation into a Board class
        Board currBoard = new Board(cars, new ArrayList<>());
        //now use a solving algorithm to solve the puzzle
        Algorithm alg = new Algorithm();

        if(alg.unlockCar(currBoard)) return;

        Board solveBoard = alg.solve(currBoard);

        puzzleModel.Car nextStep = solveBoard.popFirst();

        puzzleModel.Coordinate co = nextStep.popFirstCo();


        //flash the car suggest to move
        Car nextCar = currStat.get(nextStep.getCarID());
        
        dummy.project(nextCar, co);
        
        //flash the destination grids
        for(int i = co.y1; i <= co.y2; i++) {
            for(int j = co.x1; j <= co.x2; j++) {
                gridBoard[i][j].flash();
            }
        }
//        System.out.println("Move the car " +
//                nextStep.getCarID() + " to (" + co.x1 + ", "+co.y1+")," +
//                " ("+co.x2+", "+co.y2+")");
    }


    /**
     * clear all the GUI prompt create by giving a hint.
     */
    private void cleanHint() {
        // remove the dummy car.
        if (dummy!= null)
            root.getChildren().remove(dummy);
        // remove all the grid that is flashing.
        for(int i = 0; i < 6; i++) {
            for(int j = 0; j < 6; j++) {
                gridBoard[i][j].stopFlash();
            }
        }
    }
    
    public void turnOffHint() {
    	cleanHint();
    	onHint = false;
    }

}
