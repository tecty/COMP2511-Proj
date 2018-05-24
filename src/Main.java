import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import save.SaveManager;
import setting.Bgm;
import setting.Setting;

import java.util.concurrent.Executors;

/**
 * JavaFx application.
 */
public class Main extends Application {
	private Stage window;

    /**
     * Call javafx to lunch the UI
     * @param args Command Line argument.
     */
	public static void main (String[] args) {
		launch(args);
	}

    /**
     * JavaFx require method. Presenting the window.
     * @param primaryStage The stage in the main window
     * @throws Exception The exception may have.
     */
	@Override
	public void start(Stage primaryStage) throws  Exception {
		//add the title for the window
		primaryStage.setTitle("Gridlock");
		//bind the window with primaryStage in default
		window = primaryStage;
		//Music file is not included in the file right now
		//But the function works
		Bgm.play();


		// load the layout by fxml
		Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));

		// set the scene by the fxml file
		primaryStage.setScene(new Scene(root));

		window.setOnCloseRequest(windowEvent -> closeApp());

		// show the window
		window.show();

        // setup the executor for generating puzzles
        Setting.puzzleCreator= Executors.newFixedThreadPool(2);
	}

    /**
     * The app closing routine, save the game user is playing,
     * then exit the programme.
     */
	private void closeApp(){
	    // not a correct way to do this
	    // A graceful way to close the game
        // may prompt a sure to close
		if (Setting.save!= null){
			SaveManager.save(Setting.save);
		}

		// force to stop the system
		System.exit(0);

    }

}