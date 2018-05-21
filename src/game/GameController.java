package game;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Set;
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
import save.Level;
import levelSelect.LevelSelect;
import save.GameSave;
import save.SaveManager;
import setting.Setting;

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
    
    


    // new board structure for the game
    private Board boardNew = new Board();



    // the loaded saveslot
    int chosenLevel;

    // the integerProperty is from board now
    IntegerProperty steps = boardNew.getStepProperty();
    
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





    //load car settings from the save slot 
    public void loadSaveSlot(int level) throws MalformedURLException {
//    	System.out.println("load saveslot");
//
//    	this.chosenLevel = level;
//
//    	//modify the title
//    	title.setText("Gridlock Level "+(level+1));
//
//    	//check if it is expert mode
//    	if(Setting.save.expertMode()) {
//    		undo.setDisable(true);
//    		undo.setVisible(false);
//    	}
//
//    	//check if there is next level
//    	if(level >= 8) next.setDisable(true);
//
//    	addCars();
//    	rootPane.getChildren().addAll(carGroup);
    }
    
    @FXML
    private void initialize(){
        rootPane.getChildren().add(boardNew.getCarGroup());
        rootPane.getChildren().add(boardNew.getGridGroup());

//    	System.out.println("initialize");
//
//    	//hide the result interface
//    	levelClear.setVisible(false);
//
//    	//set a clean board
//    	setBoard();
//        // add the group to the pane
//        // to show in the scene
//        rootPane.getChildren().addAll(gridGroup);
//
//    	//bind the value of moveCounter to the stepCounter showing in fxml
//    	stepCount.textProperty().bind(steps.asString("%d"));
//        //try to use IntegerProperty to count steps
//        steps.set(0);
//
//		//bind the timeCount label with the running timer
//	    timeCount.textProperty().bind(time.asString("%.1f"));
//        timer.start();

        // create a new board
    }

    

    
    @FXML
    private void handleLevelClear()throws IOException  {
    	//first stop the timer
    	timer.stop();
    	Setting.save.getLevel(chosenLevel).update(steps.get(), time.doubleValue());
    	//update the up-till-now cleared level number
    	if(chosenLevel > Setting.save.getLevelCleared()) Setting.save.setLevelCleared(chosenLevel);
    	//save the new record
    	SaveManager.save(Setting.save, Setting.save.getName());
    	//result interface now visible
    	levelClear.setVisible(true);
    }



    //The followings are button-linked functions
    //reset all cars
    @FXML
    private void reset() {
    	//hide the result interface
    	levelClear.setVisible(false);
//
//    	//reset counter
//    	timer.stop();
//    	steps.set(0);
//    	//reset board and cars
//    	gridGroup.getChildren().clear();
//    	carGroup.getChildren().clear();
//    	setBoard();
//    	try {
//    		addCars();
//    	}
//    	catch(Exception e){
//    		System.out.println("load Game.fxml fail");
//			e.printStackTrace();
//    	}
        boardNew.reset();

    	//restart timer
    	timer.start();
    }
    
    @FXML
    private void backAction() throws IOException {
        // checkout to main menu
        Stage primaryStage = (Stage)back.getScene().getWindow();
        
        //go to the level select menu under the same save slot
		FXMLLoader loader = new FXMLLoader();
    	loader.setLocation(getClass().getResource("../levelSelect/LevelSelect.fxml"));
    	Parent root = loader.load();
        LevelSelect levelSelect = loader.getController();

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
        newGame.loadSaveSlot( chosenLevel+1);
        
        System.out.println("User get to level select ");
        // checkout to level select scene
        primaryStage.setScene(new Scene(root));
    }
    
    //the undo function
    @FXML
    private void undo() {
    	boardNew.undo();
    }
}

