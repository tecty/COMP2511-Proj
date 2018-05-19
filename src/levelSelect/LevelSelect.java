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

    public void loadBoards(GameSave saveslot) {
    	Setting.save = saveslot;
    }

    @FXML
    private void levelAction(ActionEvent actionEvent) throws IOException  {
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

        //now choose which level will be loaded
        if(actionEvent.getSource() == one)
            gameController.loadSaveSlot(0);
        else if(actionEvent.getSource() == two)
            gameController.loadSaveSlot( 1);
        else if(actionEvent.getSource() == three)
            gameController.loadSaveSlot( 2);
        else if(actionEvent.getSource() == four)
            gameController.loadSaveSlot( 3);
        else if(actionEvent.getSource() == five)
            gameController.loadSaveSlot( 4);
        else if(actionEvent.getSource() == six)
            gameController.loadSaveSlot(5);
        else if(actionEvent.getSource() == seven)
            gameController.loadSaveSlot( 6);
        else if(actionEvent.getSource() == eight)
            gameController.loadSaveSlot( 7);
        else if(actionEvent.getSource() == nine)
            gameController.loadSaveSlot( 8);
        
        System.out.println("go to play a game of level "+ ((Button)actionEvent.getSource()).getText());
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