package modeSelect;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import save.GameSave;
import save.SaveManager;
import setting.Setting;
import setting.SoundEffect;

import java.io.IOException;

/**
 * Controller responsible for create a new game by given name and difficulty.
 */
public class ModeSelect {
	@FXML
	Pane loading = new Pane();
	
	//components on the interface
	//input textField
	@FXML
	TextField saveslotName = new TextField();
	
	//warning labels
	@FXML
	Label duplicateWarning;
	@FXML
	Label emptyWarning;
	@FXML
	Label overFillWarning;
	
	//buttons
	@FXML
    Button novice;
    @FXML
    Button expert;
    @FXML
    Button backButton;    

    private Load load = new Load();

	/**
	 * Initialize all the error message to not show.
	 */
	@FXML
    private void initialize() {
    	// clear all error message
    	duplicateWarning.setVisible(false);
    	emptyWarning.setVisible(false);
    	overFillWarning.setVisible(false);
    	loading.setVisible(false);
    }

	/**
	 * Create a new game.
	 * @param actionEvent The source trigger this action.
	 * @throws IOException Exception may trigger by loading scene.
	 */
    @FXML
    private void modeAction(ActionEvent actionEvent) throws IOException  {
    	SoundEffect.play("soundEffect/click.mp3");

    	initialize();
    	//check the name of the new save-slot
    	String name = saveslotName.getText();
    	//validity checks
    	if(name==null || name.isEmpty() || name.trim().isEmpty()) {
    		emptyWarning.setVisible(true);
    		saveslotName.setText(null);
    		return;
    	}
    	if(SaveManager.checkDuplicate(name)) {
    		duplicateWarning.setVisible(true);
    		saveslotName.setText(null);
    		return;
    	}
    	if(name.length()>255) {
    		overFillWarning.setVisible(true);
    		saveslotName.setText(null);
    	}
    	//generate a new Save file

    	System.out.println("New saveslot "+name+" is created");
    	
		loading.setVisible(true);

    	
    	GameSave newSave;
    	//first check which gaming mode the player chooses
    	if(actionEvent.getSource() == novice)
    		newSave = new GameSave(name, false);
    	else
    	    newSave = new GameSave(name, true);

    	//try to save the new save slot
    	SaveManager.save(newSave);

    	// checkout this save to the this GUI
    	Setting.save = newSave;

    	//now try to jump to the level select scene with the specified saving slot
    	FXMLLoader loader = new FXMLLoader();
    	loader.setLocation(getClass().getResource("LoadPuzzle.fxml"));

    	Parent root = loader.load();
        // get the current Stage
        Stage primaryStage = (Stage)novice.getScene().getWindow();
        // checkout to level select scene
        primaryStage.setScene(new Scene(root));

        // create a thread to generate a puzzle
		Thread loadPuzzle = new Thread(load);
		Thread t = new Thread(loadPuzzle);
		load.setOnSucceeded(e->{

			LoadPuzzle puzzle = loader.getController();
			try {
				puzzle.load();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		loadPuzzle.start();
    }

	/**
	 * Handle user want to go to main scene.
	 * @param actionEvent Source trigger this action.
	 * @throws IOException Exception may trigger by loading scene.
	 */
	@FXML
    private void backAction(ActionEvent actionEvent) throws IOException {
    	SoundEffect.play("soundEffect/click.mp3");
        // checkout to main menu
        Stage primaryStage = (Stage)  backButton.getScene().getWindow();

        // try to load level select scene
        Parent root = FXMLLoader.load(getClass().getResource("../Main.fxml"));
        System.out.println("User get to settings ");
        // checkout to level select scene
        primaryStage.setScene(new Scene(root));
        
    }

	/**
	 * class for generate all the puzzle of this new game.
	 */
	private class Load extends Task<Void>{
		@Override
		protected Void call() throws Exception{
			Setting.save.gameGenerate();
			return null;
		}
	}
}
