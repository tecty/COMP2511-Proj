package save;

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
import setting.Setting;
import setting.SoundEffect;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Controller handle the save to load by user.
 */
public class SaveslotSelect {
	//components on FXML file
	//static panes
	@FXML
	ScrollPane slotPage;
	//buttons
	@FXML
	Button back;

	/**
	 * Initialize the UI component that contain save's information.
	 * @throws IOException Exception may happen when loading a scene.
	 */
	@FXML
	private void initialize() throws IOException {
		VBox list = new VBox();
		list.setPrefWidth(Control.USE_COMPUTED_SIZE);
		// try to load level select scene
		ArrayList<String> allSaveNames = SaveManager.loadAllSaves();
		for(String each : allSaveNames) {
			GameSave saveSlot = SaveManager.load(each);
			
			Button slot = new Button();
			slot.setPrefHeight(80);
			slot.setPrefWidth(482);

			// show the button's text
			String slotText = each.substring(0,each.length()-4) + " - " +	//save-slot name
							  saveSlot.printExpertMode() + "\n" +			//gaming mode
							  SaveManager.lastModifiedTime(each) + "\n"  +		//last time played
							  "Level: " + saveSlot.getLevelCleared() + "\t" +		//the highest level achieved
							  "Hint: " + saveSlot.getHintNum() + "\t" +			//number of remaining hint chances
							  "Total Star: " + saveSlot.getTotalStar();		//number of star collected
			slot.setText(slotText);
			slot.setTextAlignment(TextAlignment.CENTER);
			list.getChildren().add(slot);

			// try to repair that save
			SaveManager.tryRepairSave(saveSlot);


			slot.setOnMouseClicked(e -> {
				SoundEffect.play("soundEffect/click.mp3");
				FXMLLoader loader = new FXMLLoader();
				try {

					// record the save is using in setting class.
					Setting.save = saveSlot;

					// load the root node by fxml
					loader.setLocation(getClass().getResource("../levelSelect/LevelSelect.fxml"));

					// get the current Stage
					Stage primaryStage = (Stage)slot.getScene().getWindow();
					// checkout to level select scene
					primaryStage.setScene(new Scene(loader.load()));
				}catch (Exception exception){
					// do nothing
					exception.printStackTrace();
				}
			});
		}
		slotPage.setContent(list);
	}

	/**
	 * If user want to get to main scene.
	 * @param actionEvent Source trigger this action.
	 * @throws IOException Exception may occur by loading a scene.
	 */
	@FXML
    private void backAction(ActionEvent actionEvent) throws IOException {
		SoundEffect.play("soundEffect/click.mp3");
        // checkout to main menu
        Stage primaryStage = (Stage)back.getScene().getWindow();
        // try to load level select scene
        Parent root = FXMLLoader.load(getClass().getResource("../Main.fxml"));
        System.out.println("User get to main ");
        // checkout to level select scene
        primaryStage.setScene(new Scene(root));
    }
}
