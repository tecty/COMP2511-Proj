package game;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Stack;

import javafx.animation.AnimationTimer;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import levelSelect.Level;
import levelSelect.LevelSelect;
import save.GameSave;
import save.SaveManager;

public class GameController {
    // create game in this controller
	@FXML //finish
	Pane levelClear;
    @FXML //finish
    Pane rootPane;
    //labels
    @FXML
    private Label title = new Label();
    @FXML //finish
    private Label timeCount = new Label();
    @FXML //finish
    private Label stepCount = new Label();
    //buttons on the board
    @FXML
    private Button hint = new Button();
    @FXML //finish
    private Button reset = new Button();
    @FXML
    private Button undo = new Button();
    @FXML //finish
    private Button back = new Button();
    //buttons on the game clear interface
    @FXML //finish
    private Button returnTo = new Button();
    @FXML
    private Button replay = new Button();
    @FXML
    private Button next = new Button();
    
    
    // reference to the board
    private Grid[][] board = new Grid[6][6];

    // the loaded saveslot
    GameSave saveslot;
    int chosenLevel;
    
    // store all the car in this game
    Group carGroup = new Group();
    Group gridGroup = new Group();
    
    // store the moved car id of each step 
    Stack<Movement> history = new Stack<>(); 
    
    //step counter
    //variables prepared for stepCounter
    IntegerProperty steps = new SimpleIntegerProperty();
    
    //timer
	//variables prepared for timer
	DoubleProperty time = new SimpleDoubleProperty();    	
	
	BooleanProperty running = new SimpleBooleanProperty();
	
	AnimationTimer timer = new AnimationTimer() {
	    private long startTime ;
	
	    @Override
	    public void start() {
	        startTime = System.currentTimeMillis();
	        running.set(true);
	        super.start();
	    }
	
	    @Override
	    public void stop() {
	        running.set(false);
	        super.stop();
	    }
	    
	    @Override
	    public void handle(long timestamp) {
	        long now = System.currentTimeMillis();
	        time.set((now - startTime) / 1000.0);
	    }
	};
    
    // the size of each grid
    public final static int GRID_SIZE = 50;

    // when a click on a car, it would set
    private double mouseOriginX, mouseOriginY;
    // cleaner code to handle collision
    // record the mouse offset of this drag
    private double mouseOffsetX, mouseOffsetY;

    // the range of the offset of mouse movement can achieve
    // for clicked particular car
    private double offsetMax, offsetMin;

    
    //load car settings from the save slot 
    public void loadSaveSlot(GameSave saveslot, int chosenLevel) throws MalformedURLException {
    	System.out.println("load saveslot");
    	this.saveslot = saveslot;
    	this.chosenLevel = chosenLevel;
    	
    	//modify the title
    	title.setText("Gridlock Level "+(chosenLevel+1));
    	
    	//check if it is expert mode
    	if(saveslot.expertMode()) {
    		undo.setDisable(true);
    		undo.setVisible(false);
    	}
    	
    	//check if there is next level
    	if(chosenLevel >= 8) next.setDisable(true);
    	
    	addCars();
    	rootPane.getChildren().addAll(carGroup);
    }
    
    @FXML
    private void initialize(){
    	System.out.println("initialize");
    	
    	//hide the result interface
    	levelClear.setVisible(false);
    	
    	//set a clean board
    	setBoard();
        // add the group to the pane
        // to show in the scene
        rootPane.getChildren().addAll(gridGroup);
    	
    	//bind the value of moveCounter to the stepCounter showing in fxml
    	stepCount.textProperty().bind(steps.asString("%d"));
        //try to use IntegerProperty to count steps
        steps.set(0);
        
		//bind the timeCount label with the running timer
	    timeCount.textProperty().bind(time.asString("%.1f"));     
        timer.start();
    }
    
    private void setBoard() {
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
    }
    
    private void addCars() throws MalformedURLException {
        if(saveslot==null) System.out.println("slot empty");
        Level currLevel = saveslot.getLevel(chosenLevel);
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
    			if(board[pos][gridY].getCar()!=null) return false;
    		}
    	}
    	else {
    		for(int pos = gridY; pos < gridY+len; pos ++) {
    			if(board[gridX][pos].getCar()!=null) return false;
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

            // TODO: need to delete
            System.out.println("Dump the System while " +
                    "click, a demo of this function");
            dumpState(thisCar);

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
            	 try {
            		 handleLevelClear();
            	 } catch (IOException e) {
                   e.printStackTrace();
               }
            }
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
                board[x][car.getGridY()].setCar(null);
            }
            // add new board record
            for (int x = gridX;
                 x < gridX + car.getLen() ; x++) {
                // keep the invariant that y = y_0
                board[x][car.getGridY()].setCar(car);
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
                board[car.getGridX()][y].setCar(null);
            }
            // add new board record
            for (int y = gridY;
                 y < gridY + car.getLen(); y++) {
                // keep the invariant that y = y_0
                board[car.getGridX()][y].setCar(car);
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
    
    @FXML
    private void handleLevelClear()throws IOException  {
    	//first stop the timer
    	timer.stop();
    	System.out.println("here");
    	saveslot.getLevel(chosenLevel).update(steps.get(), time.doubleValue());
    	System.out.println("here");
    	//update the up-till-now cleared level number
    	if(chosenLevel > saveslot.getLevelCleared()) saveslot.setLevelCleared(chosenLevel);
    	//save the new record
    	System.out.println("here");
    	SaveManager.save(saveslot, saveslot.getName());
    	//result interface now visible
    	System.out.println("here");
    	levelClear.setVisible(true);
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
            offsetMin = -car.getGridX()                   * GRID_SIZE;
            offsetMax = (6 - car.getGridX()- car.getLen())* GRID_SIZE;
            // get the range of x

            // find the min
            for (int x = 0; x < 6; x++) {
                if (board[x][car.getGridY()].getCar() == car)
                    break;
                if (board[x][car.getGridY()].getCar() != null ){
                    // set the new min offset
                    offsetMin = -(car.getGridX() -x-1)*GRID_SIZE;
                }
            }

            // find the max
            for (int x = car.getGridX() + car.getLen(); x < 6; x++) {
                // car length = 2 and star at 2, then search start from 4
                // but board[4] wouldn't contain this car
                if (board[x][car.getGridY()].getCar()!= null){
                    // set the offset max
                    offsetMax = (x - car.getGridX()- car.getLen() -1)*GRID_SIZE;
                    // not need to find the next car would be collision
                    break;
                }
            }

        }else{
            // Vertical: get the range of y
            // setup the topBoundary and bottomBoundary
            offsetMin = -car.getGridY()                   * GRID_SIZE;
            offsetMax = (6 - car.getGridY()- car.getLen())* GRID_SIZE;
            // get the range of x

            // find the min
            for (int y = 0; y < 6; y++) {
                if (board[car.getGridX()][y].getCar() == car)
                    break;
                if (board[car.getGridX()][y].getCar() != null){
                    // set the new min offset
                    offsetMin = -(car.getGridY() -y-1)*GRID_SIZE;
                }
            }

            // find the max
            for (int y = car.getGridY() + car.getLen(); y < 6; y++) {
                // car length = 2 and star at 2, then search start from 4
                // but board[4] wouldn't contain this car
                if (board[car.getGridX()][y].hasCar(car)){
                    // set the offset max
                    offsetMax = (y - car.getGridY()- car.getLen() -1)*GRID_SIZE;
                    // not need to find the next car would be collision
                    break;
                }
            }
        }
//        dumpState(car);
    }
    
    private void dumpState(Car car){
        // dump the current state of this board
        System.out.println("car"+ car.getCarId()+ " has grid "+ car.getGridX() + " ,"+ car.getGridY());
        System.out.println("OffsetRange "+ offsetMin/this.GRID_SIZE + " ," + offsetMax/this.GRID_SIZE);
        System.out.println(steps.get());
        printBoard();
        System.out.println();
    }

    private void  printBoard(){
        // test function to print this board
        for (int y = 0; y < 6; y++) {
            for (int x = 0; x < 6; x++) {
                if (board[x][y].getCar()== null)
                    System.out.print("#");
                else
                    System.out.print(board[x][y].getCar().getCarId());
            }
            System.out.print("\n");
        }
    }

    //The followings are button-linked functions
    //reset all cars
    @FXML
    private void reset() {
    	//hide the result interface
    	levelClear.setVisible(false);
    	
    	//reset counter
    	timer.stop();
    	steps.set(0);
    	//reset board and cars
    	gridGroup.getChildren().clear();
    	carGroup.getChildren().clear();
    	setBoard();
    	try {
    		addCars();
    	}
    	catch(Exception e){
    		System.out.println("load Game.fxml fail");
			e.printStackTrace();
    	}
    	//restart timer
    	timer.start();
    }
    
    @FXML
    private void backAction() throws IOException {
        // checkout to main menu
        Stage primaryStage = (Stage)back.getScene().getWindow();
        
        //go to the level select menu under the same save slot
		FXMLLoader loader = new FXMLLoader();
    	loader.setLocation(getClass().getResource("../levelSelect/levelSelect.fxml"));
    	Parent root = loader.load();
        LevelSelect levelSelect = loader.getController();
        levelSelect.loadBoards(saveslot);
        
        System.out.println("User get to level select ");
        // checkout to level select scene
        primaryStage.setScene(new Scene(root));
    }    
    
    @FXML
    private void nextLevel() throws IOException {
    	// checkout to main menu
        Stage primaryStage = (Stage)back.getScene().getWindow();
        
        //go to the level select menu under the same save slot
		FXMLLoader loader = new FXMLLoader();
    	loader.setLocation(getClass().getResource("Game.fxml"));
    	Parent root = loader.load();
        GameController newGame = loader.getController();
        newGame.loadSaveSlot(saveslot, chosenLevel+1);
        
        System.out.println("User get to level select ");
        // checkout to level select scene
        primaryStage.setScene(new Scene(root));
    }
    
    //the undo function
    @FXML
    private void undo() {
    	//cannot undo at the stat of game
    	if(history.isEmpty()) return;
    	
    	// pop a movement in the stack 
    	Movement move = history.pop();
    	System.out.println("the car is "+move.getCar().getCarId());
    	System.out.println("the movement is "+ move.getMovement());
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
}

