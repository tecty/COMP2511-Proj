package saveslotSelect;

import java.io.IOException;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import levelSelect.LevelSelect;
import save.GameSave;
import save.SaveManager;
import setting.Setting;

public class SaveslotSelect {
	//components on FXML file
	//static panes
	@FXML
	ScrollPane slotPage;
	//buttons
	@FXML
	Button back;
	
	@FXML
	private void initialize() throws ClassNotFoundException, IOException {
		VBox list = new VBox();
		list.setPrefWidth(Control.USE_COMPUTED_SIZE);
		// try to load level select scene
		ArrayList<String> allSaveNames = SaveManager.loadAllSaves();
		for(String each : allSaveNames) {
			GameSave saveSlot = SaveManager.load(each);
			
			Button slot = new Button();
			slot.setPrefHeight(80);
			slot.setPrefWidth(482);

			String slotText = each.substring(0,each.length()-4) + " - " +	//save-slot name
							  saveSlot.printExpertMode() + "\n" +			//gaming mode
							  SaveManager.lastModifiedTime(each) + "\n"  +		//last time played
							  "Level: " + saveSlot.getLevelCleared() + "\t" +		//the highest level achieved
							  "Hint: " + saveSlot.getHintRemain() + "\t" +			//number of remaining hint chances
							  "Total Star: " + saveSlot.getTotalStar();		//number of star collected
			slot.setText(slotText);
			slot.setTextAlignment(TextAlignment.CENTER);
			list.getChildren().add(slot);
			
			slot.setOnMouseClicked(e -> {
				FXMLLoader loader = new FXMLLoader();
		    	loader.setLocation(getClass().getResource("../levelSelect/LevelSelect.fxml"));
		    	Parent root = null;
		    	try {
					root = loader.load();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		    	
		    	LevelSelect levelSelect = loader.getController();
				// record the save is using in setting class.
				Setting.save = saveSlot;
		    	
		        // get the current Stage
		        Stage primaryStage = (Stage)slot.getScene().getWindow();
		        // checkout to level select scene
		        primaryStage.setScene(new Scene(root));
			});
		}
		slotPage.setContent(list);
	}
	
	
	@FXML
    private void backAction(ActionEvent actionEvent) throws IOException {
        // checkout to main menu
        Stage primaryStage = (Stage)back.getScene().getWindow();
        // try to load level select scene
        Parent root = FXMLLoader.load(getClass().getResource("../Main.fxml"));
        System.out.println("User get to main ");
        // checkout to level select scene
        primaryStage.setScene(new Scene(root));
    }
}
