import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import save.SaveManager;
import setting.Bgm;
import setting.Setting;

import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class Main extends Application {
	Stage window;

	public static void main (String[] args) {
		launch(args);
	}
	
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

		window.setOnCloseRequest(windowEvent -> {closeApp();});

		// show the window
		window.show();

        // setup the executor for generating puzzles
        Setting.puzzleCreator= Executors.newFixedThreadPool(2);
	}

	private void closeApp(){
	    // not a correct way to do this
	    // A graceful way to close the game
        // may prompt a sure to close
		if (Setting.save!= null){
			SaveManager.save(Setting.save);
		}

		// stop the executor
        Setting.puzzleCreator.shutdownNow();
    }

}