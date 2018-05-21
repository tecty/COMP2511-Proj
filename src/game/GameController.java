package game;

import java.io.IOException;
import java.net.MalformedURLException;

import javafx.animation.AnimationTimer;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import levelSelect.LevelSelect;
import save.SaveManager;
import setting.Setting;
import selector.RandomSelector;
import setting.SoundEffect;

public class GameController {
    // create game in this controller
	@FXML //finish
	Pane levelClear;
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

    @FXML private BoardController boardController;

    // the loaded saveSlot
    int currentLevel;

    // the integerProperty is from board now
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

    @FXML
    private void initialize(){
    	//bind the value of moveCounter to the stepCounter showing in fxml
    	stepCount.textProperty().bind(steps.asString("%d"));

		//bind the timeCount label with the running timer
	    timeCount.textProperty().bind(time.asString("%.1f"));

        // inject this controller to the board
        // so it can increment the steps
        boardController.injectMainController(this);
    }

    public void checkoutFinishPrompt()  {
    	//first stop the timer
    	timer.stop();
    	Setting.save.getLevel(currentLevel).update(steps.get(), time.doubleValue());
    	//update the up-till-now cleared level number
    	if(currentLevel > Setting.save.getLevelCleared()) Setting.save.setLevelCleared(currentLevel);
    	//save the new record
    	SaveManager.save(Setting.save, Setting.save.getName());
    	//result interface now visible
    	levelClear.setVisible(true);
    	// play the game successful sound effect.
        SoundEffect.play("soundEffect/levelPass.mp3");
    }

    public void resetLevel(int level){
        // set up new current level
        currentLevel = level;

        //hide the result interface
        levelClear.setVisible(false);

        // reset the board by loading a save
        // step counter would reset there
        boardController.reset(Setting.save.getLevel(currentLevel));

        // reset the time
        timer.start();

        // reset the title
    	title.setText("Puzzle "+(level+1));
    }

    //The followings are button-linked functions
    //reset all cars
    @FXML
    private void reset() {
        SoundEffect.play("soundEffect/click.mp3");
        resetLevel(currentLevel);
    }
    
    @FXML
    private void backAction(){
        // checkout to main menu
        Stage primaryStage = (Stage)back.getScene().getWindow();
        
        //go to the level select menu under the same save slot
		FXMLLoader loader = new FXMLLoader();
    	loader.setLocation(getClass().getResource("../levelSelect/LevelSelect.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        LevelSelect levelSelect = loader.getController();

        System.out.println("User get to level select ");
        // checkout to level select scene
        primaryStage.setScene(new Scene(root));
    }    
    
    @FXML
    private void nextLevel() {
        SoundEffect.play("soundEffect/click.mp3");
        resetLevel(currentLevel+1);
    }
    
    //the undo function
    @FXML
    private void undo() {
        SoundEffect.play("soundEffect/click.mp3");
    	boardController.undo();
    }

    protected void addSteps(){
        steps.set(steps.get()+1);
    }

    protected int getSteps(){
        return steps.get();
    }

    protected void resetSteps(){
        steps.set(0);
    }
}

