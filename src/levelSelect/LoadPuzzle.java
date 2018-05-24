package levelSelect;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * Controller for the waiting scene while generating new puzzle.
 */
public class LoadPuzzle{
	
	@FXML
	Label loading;

	/**
	 * load the level select scene because some of
	 * the puzzle can be play by user.
	 * @throws Exception Exception may be trigger by user.
	 */
	public void load() throws Exception{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("../levelSelect/LevelSelect.fxml"));
		Parent root = loader.load();
		Stage primaryStage = (Stage) loading.getScene().getWindow();
		primaryStage.setScene(new Scene(root));
	}
}
