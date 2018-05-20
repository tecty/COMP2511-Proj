package modeSelect;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import levelSelect.LevelSelect;

import java.io.IOException;

import save.GameSave;

import save.SaveManager;
import setting.SoundEffect;

public class ModeSelect {
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

    @FXML
    private void initialize() {
    	duplicateWarning.setVisible(false);
    	emptyWarning.setVisible(false);
    	overFillWarning.setVisible(false);
    }
    
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
    	//add format suffix
    	name = name + ".sav";
    	System.out.println("New saveslot "+name+" is created");
    	
    	GameSave newSave;
    	//first check which gaming mode the player chooses
    	if(actionEvent.getSource() == novice) {
    		newSave = new GameSave(name, false);
    	}
    	else newSave = new GameSave(name, true);	
    	
    	//try to save the new save slot
    	SaveManager.save(newSave, name);
    	    	
    	//now try to jump to the level select scene with the specified saving slot
    	FXMLLoader loader = new FXMLLoader();
    	loader.setLocation(getClass().getResource("../levelSelect/LevelSelect.fxml"));
    	Parent root = loader.load();
    	
    	LevelSelect levelSelect = loader.getController();
    	levelSelect.loadBoards(newSave);
    	
        // get the current Stage
        Stage primaryStage = (Stage)novice.getScene().getWindow();

        
        // checkout to level select scene
        primaryStage.setScene(new Scene(root));
    }
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
}
