import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class ExitGame implements EventHandler<ActionEvent>{

	@Override
	public void handle(ActionEvent event) {
		// TODO Auto-generated method stub
		Platform.exit();
	}

}
