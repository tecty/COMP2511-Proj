import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class Main extends Application{
	Stage window;
	Scene mainScene;
	
	public static void main (String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		//add the title for the window
		primaryStage.setTitle("Gridlock");
		//bind the window with primaryStage in default
		window = primaryStage;
		//Music file is not included in the file right now
		//But the function works
		// Bgm.play("music/Havana.mp3");
		
		//Generate components on the main page
		//Game Title
		Label title = new Label("GridLock");
		
		//go to create a new game interface
		Button newGame = new Button("New Game");
		//go to game settings
		Button setting = new Button("Settings");
		//quit the game
		Button exit = new Button("Exit Game");
		exit.setOnAction(new ExitGame());
		
		VBox layout = new VBox();
		layout.getChildren().addAll(title, newGame, setting, exit);
		
		mainScene = new Scene(layout, 200, 200);
		window.setScene(mainScene);
		window.show();
	}
}