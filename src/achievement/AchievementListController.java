package achievement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import setting.SoundEffect;

import javax.swing.text.html.ListView;
import java.io.IOException;

public class AchievementListController {
    @FXML
    ListView listRoot;
    @FXML
    Button backButton;
    @FXML
    private void backAction(ActionEvent actionEvent) throws IOException {
        SoundEffect.play("soundEffect/click.mp3");
        // checkout to main menu
        Stage primaryStage = (Stage)  backButton.getScene().getWindow();

        // try to load level select scene
        Parent root = FXMLLoader.load(getClass().
                getResource("../levelSelect/LevelSelect.fxml"));
        System.out.println("User get to settings ");
        // checkout to level select scene
        primaryStage.setScene(new Scene(root));
    }

}
