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
import setting.Bgm;


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
//		Bgm.play();

		// load the layout by fxml
		Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));

		// set the scene by the fxml file
		primaryStage.setScene(new Scene(root));

		window.setOnCloseRequest(windowEvent -> {closeApp();});

		// show the window
		window.show();
	}

	private void closeApp(){
	    // not a correct way to do this
	    // A graceful way to close the game
        // may prompt a sure to close
        // TODO: save the current game stage
        System.out.println("user will save it progress");
    }

}