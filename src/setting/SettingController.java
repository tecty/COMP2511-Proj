package setting;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Handle the page about toggle the settings.
 */
public class SettingController {
	private static final long serialVersionUID = 1L;
	
	@FXML
	Button musicControll;
	@FXML
	Button soundEffectControll;
	@FXML
	Button creditButton;
    @FXML
    Button backButton;
    
    //credit stuff
    @FXML
    Pane credit;
    @FXML
    Label version;
    @FXML
    Button creditBackButton;

	/**
	 * initialize the environment of this controller.
	 */
	@FXML
    private void initialize() {
    	version.setText("This is the game Traffic Jam in Version "+serialVersionUID+" developed by:");
    	credit.setVisible(false);
    	credit.setDisable(true);
    	if(Bgm.getStat()) {
    		System.out.println("true");
    		musicControll.setText("Music: On");
    	}
    	else musicControll.setText("Music: Off");
    	if(SoundEffect.getStat()) soundEffectControll.setText("Sound Effect: On");
    	else soundEffectControll.setText("Sound Effect: Off");
    }

	/**
	 * Change whether user want to play bgm.
	 * @param actionEvent The source trigger this event.
	 */
	@FXML
    private void changeBgm(ActionEvent actionEvent) {
    	SoundEffect.play("soundEffect/click.mp3");
    	if(musicControll.getText().equals("Music: On")) {
    		Bgm.changeStat(false);
    		musicControll.setText("Music: Off");
    	}
    	else{
    		Bgm.changeStat(true);
    		musicControll.setText("Music: On");
    	}
    }

	/**
	 * Change whether user want a sound effect.
	 * @param actionEvent The source trigger this action.
	 */
	@FXML
    private void changeSoundEffect(ActionEvent actionEvent) {
    	SoundEffect.play("soundEffect/click.mp3");
    	if(soundEffectControll.getText().equals("Sound Effect: On")) {
    		SoundEffect.changeStat(false);
    		soundEffectControll.setText("Sound Effect: Off");
    	}
    	else {
    		SoundEffect.changeStat(true);
    		soundEffectControll.setText("Sound Effect: On");
    	}
    }

	/**
	 * Get back to main scene.
	 * @param actionEvent Source trigger this action.
	 * @throws IOException Exception while load the scene.
	 */
	@FXML
    private void backAction(ActionEvent actionEvent) throws IOException {
    	SoundEffect.play("soundEffect/click.mp3");
        // get the current Stage
        Stage primaryStage = (Stage)  backButton.getScene().getWindow();

        // try to load level select scene
        Parent root = FXMLLoader.load(getClass().getResource("../Main.fxml"));
        System.out.println("User get to settings ");
        // checkout to level select scene
        primaryStage.setScene(new Scene(root));
    }

	/**
	 * Show the author of this application.
	 * @param actionEvent Source trigger this action.
	 */
	@FXML
    private void creditOpen(ActionEvent actionEvent) {
    	SoundEffect.play("soundEffect/click.mp3");
    	credit.setVisible(true);
    	credit.setDisable(false);
    }

	/**
	 * Close the author box.
	 * @param actionEvent Source trigger this event.
	 */
	@FXML
    private void creditClose(ActionEvent actionEvent) {
    	SoundEffect.play("soundEffect/click.mp3");
    	credit.setVisible(false);
    	credit.setDisable(true);
    }

}
