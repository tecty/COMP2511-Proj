package levelSelect;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class LoadPuzzle{
	
	@FXML
	Label loading;
 
//	@FXML
//	private void initialize() {
//		System.out.println("here5");
//		this.load();
//	}
//	
	public void load() throws Exception{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("../levelSelect/LevelSelect.fxml"));
		Parent root = loader.load();
		Stage primaryStage = (Stage) loading.getScene().getWindow();
		primaryStage.setScene(new Scene(root));
	}
}
