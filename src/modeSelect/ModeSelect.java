package modeSelect;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import levelSelect.LevelSelect;

import java.io.IOException;

import save.GameSave;

import save.SaveManager;

public class ModeSelect {
	@FXML
    Button novice;
    @FXML
    Button expert;
    @FXML
    Button backButton;    

    @FXML
    private void modeAction(ActionEvent actionEvent) throws IOException  {
    	//generate a new Save file
    	GameSave newSave;
    	//first check which gaming mode the player chooses
    	if(actionEvent.getSource() == novice) {
    		newSave = new GameSave(0);
    	}
    	else newSave = new GameSave(1);	
    	//try to save the new save slot
    	SaveManager.save(newSave, "saving/"+newSave.getSlotNum()+".sav");
    	    	
    	
    	//now try to jump to the level select scene with the specified saving slot
    	FXMLLoader loader = new FXMLLoader();
    	loader.setLocation(getClass().getResource("../levelSelect/levelSelect.fxml"));
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
        // checkout to main menu
        Stage primaryStage = (Stage)  backButton.getScene().getWindow();

        // try to load level select scene
        Parent root = FXMLLoader.load(getClass().getResource("../Main.fxml"));
        System.out.println("User get to settings ");
        // checkout to level select scene
        primaryStage.setScene(new Scene(root));
    }
}
