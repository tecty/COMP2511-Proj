import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

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
    private void levelSelectAction(ActionEvent actionEvent) throws IOException {
        // try to load level select scene
        Parent root = FXMLLoader.load(getClass().getResource("levelSelect/LevelSelect.fxml"));
        // get the current Stage
        Stage primaryStage = (Stage)  newGameButton.getScene().getWindow();

        if (actionEvent.getSource() == newGameButton){
            System.out.println("User try to start a new game");
        }
        else if(actionEvent.getSource() == continueButton){
            System.out.println("User try to read his save file");
        }

        System.out.println("User get to select level");
        // checkout to level select scene
        primaryStage.setScene(new Scene(root));
    }

}
