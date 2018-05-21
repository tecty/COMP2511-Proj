package levelSelect;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

import game.GameController;
import save.GameSave;
import setting.Setting;
import setting.SoundEffect;

public class LevelSelect {
    @FXML
    Button one;
    @FXML
    Button two;
    @FXML
    Button three;
    @FXML
    Button four;
    @FXML
    Button five;
    @FXML
    Button six;
    @FXML
    Button seven;
    @FXML
    Button eight;
    @FXML
    Button nine;
    @FXML
    Button backButton;
    
    //this is the model used for generating the nine boards

    private void levelLock() {
        //check the playability of each level

        System.out.println("SAVE:" + saveslot.getLevelCleared());

        if(0 > saveslot.getLevelCleared()+1) one.setDisable(true);
        if(1 > saveslot.getLevelCleared()+1) two.setDisable(true);
        if(2 > saveslot.getLevelCleared()+1) three.setDisable(true);
        if(3 > saveslot.getLevelCleared()+1) four.setDisable(true);
        if(4 > saveslot.getLevelCleared()+1) five.setDisable(true);
        if(5 > saveslot.getLevelCleared()+1) six.setDisable(true);
        if(6 > saveslot.getLevelCleared()+1) seven.setDisable(true);
        if(7 > saveslot.getLevelCleared()+1) eight.setDisable(true);
        if(8 > saveslot.getLevelCleared()+1) nine.setDisable(true);
    }

    @FXML
    private void levelAction(ActionEvent actionEvent) throws IOException  {
    	SoundEffect.play("soundEffect/click.mp3");
        // get the current Stage
        Stage primaryStage = (Stage)  one.getScene().getWindow();

        // try to load level select scene
        FXMLLoader loader = new FXMLLoader();
        
        Parent root = null;
        try {
			loader.setLocation(getClass().getResource("../game/Game.fxml"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("load Game.fxml fail");
			e.printStackTrace();
		}
        try {
        	root = loader.load();
        } catch (Exception e) {
        	e.printStackTrace();
        	System.exit(0);
        }

        // get the controller of the game, and set the save slot it would use
        GameController gameController = loader.getController();

        System.out.println("go to play a game of level "+ ((Button)actionEvent.getSource()).getText());
        // checkout to level select scene
        primaryStage.setScene(new Scene(root));

        //now choose which level will be loaded
        if(actionEvent.getSource() == one)
            gameController.resetLevel(0);
        else if(actionEvent.getSource() == two)
            gameController.resetLevel(1);
        else if(actionEvent.getSource() == three)
            gameController.resetLevel(2);
        else if(actionEvent.getSource() == four)
            gameController.resetLevel(3);
        else if(actionEvent.getSource() == five)
            gameController.resetLevel(4);
        else if(actionEvent.getSource() == six)
            gameController.resetLevel(5);
        else if(actionEvent.getSource() == seven)
            gameController.resetLevel(6);
        else if(actionEvent.getSource() == eight)
            gameController.resetLevel(7);
        else if(actionEvent.getSource() == nine)
            gameController.resetLevel(8);
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
