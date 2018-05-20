package setting;

import java.nio.file.Paths;
import javafx.scene.media.*;
import javafx.util.Duration;

public class Bgm {
	//mediaPlayer-object will not we cleaned away since someone holds a reference to it!	
	private static MediaPlayer mediaPlayer;

	public static void play()
	{
		Media hit = new Media(Paths.get("music/bgm.mp3").toUri().toString());
	    mediaPlayer = new MediaPlayer(hit);
	    mediaPlayer.setOnEndOfMedia(() -> mediaPlayer.seek(Duration.ZERO));
	    mediaPlayer.setAutoPlay(true);
	}
}