import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import levelSelect.LevelSelect;
import save.GameSave;
import save.SaveManager;

import java.io.IOException;


public class Controller {
    @FXML
    private Button newGameButton;
    @FXML
    private Button continueButton;
    @FXML
    private Button settingButton;
    @FXML
    private Button quitButton;

    @FXML
    private void modeSelectAction(ActionEvent actionEvent) throws IOException {
    	// try to load mode select scene
    	Parent root = FXMLLoader.load(getClass().getResource("modeSelect/ModeSelect.fxml"));
    	// get the current Stage
    	Stage primaryStage = (Stage) newGameButton.getScene().getWindow();
    	
    	primaryStage.setScene(new Scene(root));
    }

    @FXML
    private void levelSelectAction(ActionEvent actionEvent) throws IOException, ClassNotFoundException {  	
    	// get the current Stage
        Stage primaryStage = (Stage)  newGameButton.getScene().getWindow();


        // try to load level select scene
        Parent root = FXMLLoader.load(getClass().getResource("saveslotSelect/SaveSlotSelect.fxml"));
        System.out.println("User get to choose a savelot ");
        // checkout to level select scene
        primaryStage.setScene(new Scene(root));
        
//    	FXMLLoader loader = new FXMLLoader();
//    	loader.setLocation(getClass().getResource("levelSelect/LevelSelect.fxml"));
//        Parent root = loader.load();
//        //load a save-slot from default save file location
//        GameSave saveslot = SaveManager.load("saving/test.sav");
//        LevelSelect levelSelect = loader.getController();
//        levelSelect.loadBoards(saveslot);
//        
//        // get the current Stage
//        Stage primaryStage = (Stage)  newGameButton.getScene().getWindow();
//        System.out.println("User get to select level");
//        // checkout to level select scene
//        primaryStage.setScene(new Scene(root));
    }

    @FXML
    private void settingAction(ActionEvent actionEvent) throws IOException {
        // get the current Stage
        Stage primaryStage = (Stage)  newGameButton.getScene().getWindow();


        // try to load level select scene
        Parent root = FXMLLoader.load(getClass().getResource("setting/Setting.fxml"));
        System.out.println("User get to settings ");
        // checkout to level select scene
        primaryStage.setScene(new Scene(root));
    }

    @FXML
    private void quitAction(ActionEvent actionEvent) {
        // try to exit this.
        // get the current Stage
        Stage primaryStage = (Stage)  newGameButton.getScene().getWindow();
        // call the function set in main, close the programme gracefully
        primaryStage.close();
    }
}
