import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import setting.SoundEffect;

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
    	SoundEffect.play("soundEffect/click.mp3");
    	// try to load mode select scene
    	Parent root = FXMLLoader.load(getClass().getResource("modeSelect/ModeSelect.fxml"));
    	// get the current Stage
    	Stage primaryStage = (Stage) newGameButton.getScene().getWindow();
    	
    	primaryStage.setScene(new Scene(root));
    }

    @FXML
    private void levelSelectAction(ActionEvent actionEvent) throws IOException, ClassNotFoundException {  	
    	SoundEffect.play("soundEffect/click.mp3");
    	// get the current Stage
        Stage primaryStage = (Stage)  newGameButton.getScene().getWindow();


        // try to load level select scene
        Parent root = FXMLLoader.load(getClass().getResource("saveslotSelect/SaveSlotSelect.fxml"));
        System.out.println("User get to choose a savelot ");
        // checkout to level select scene
        primaryStage.setScene(new Scene(root));
    }

    @FXML
    private void settingAction(ActionEvent actionEvent) throws IOException {
    	SoundEffect.play("soundEffect/click.mp3");
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
    	SoundEffect.play("soundEffect/click.mp3");
    	// try to exit this.
        // get the current Stage
        Stage primaryStage = (Stage)  newGameButton.getScene().getWindow();
        // call the function set in main, close the programme gracefully
        primaryStage.close();
    }
}
