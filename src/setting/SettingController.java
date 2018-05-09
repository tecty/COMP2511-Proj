package setting;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class SettingController {
    @FXML
    Button backButton;

    @FXML
    private void backAction(ActionEvent actionEvent) throws IOException {
        // get the current Stage
        Stage primaryStage = (Stage)  backButton.getScene().getWindow();


        // try to load level select scene
        Parent root = FXMLLoader.load(getClass().getResource("../Main.fxml"));
        System.out.println("User get to settings ");
        // checkout to level select scene
        primaryStage.setScene(new Scene(root));
    }


}
