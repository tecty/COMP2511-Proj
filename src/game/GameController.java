package game;

import java.io.IOException;

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
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import save.Level;
import setting.Setting;
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
    @FXML
    private ImageView star0;
    @FXML
    private ImageView star1;
    @FXML
    private ImageView star2;

    // text for recommend step and time
    @FXML
    private Label recommendSteps;
    @FXML
    private Label recommendSec;

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

        // refresh the hint's text by hint count
        refreshHint();
    }

    private void refreshHint(){
        // setting the hint by save
        hint.setText("Hint :" + Setting.save.getHintNum());
    }

    public void checkoutFinishPrompt()  {
    	//first stop the timer
    	timer.stop();
        //initial the start with no thing show
        star0.setVisible(false);
        star1.setVisible(false);
        star2.setVisible(false);

        //local variable for this level
        Level level = Setting.save.getLevel(currentLevel);

        // try to update the game info
        level.update(steps.get(), time.doubleValue());
        // show the star's judgement by this game's performance
        switch (level.calStar(steps.get(), time.doubleValue())){
            case 1:
                star0.setVisible(true);
                break;
            case 2:
                star0.setVisible(true);
                star1.setVisible(true);
                break;
            case 3:
                star0.setVisible(true);
                star1.setVisible(true);
                star2.setVisible(true);
                break;
        }


        //update the up-till-now cleared level number
        Setting.save.addLevelCleared(currentLevel);
        System.out.println("get Starts "+ Setting.save.getLevel(currentLevel).calStar());

        //take care the availability of playing the next level
    	if(Setting.save.getLevelCleared()>8) next.setDisable(true);

    	//result interface now visible
    	levelClear.setVisible(true);
    	// play the game successful sound effect.
        SoundEffect.play("soundEffect/levelPass.mp3");

        // star may be gain while game is succeed
        refreshHint();

    }

    public void resetLevel(int level){
        // set up new current level
        currentLevel = level;

        //hide the result interface
        levelClear.setVisible(false);

        // the level entity of this game
        Level thisLevel = Setting.save.getLevel(currentLevel);

        // reset the board by loading a save
        // step counter would reset there
        boardController.reset(thisLevel);

        // reset the title
        title.setText("Puzzle "+(level+1));

        // reset the rec step and time
        recommendSteps.setText("/"+thisLevel.getRecommendStep());
        recommendSec.setText("/"+String.format("%.0f",thisLevel.recommendTime()));

        // reset the time

        timer.start();
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

    @FXML
    private void hintAction(){
        // reduce the hint count and refresh the text
        if (Setting.save.getLevel(currentLevel).useHint()){
            // hint the next step in the board
            boardController.hintNextStep();
            // reduce the hint count in the save
            refreshHint();
        }
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

