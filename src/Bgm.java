import java.nio.file.Paths;
import javafx.scene.media.*;
import javafx.util.Duration;

public class Bgm {
	//mediaPlayer-object will not we cleaned away since someone holds a reference to it!
	private static MediaPlayer mediaPlayer;

	public static void play(String musicFile)
	{
	    Media hit = new Media(Paths.get(musicFile).toUri().toString());
	    mediaPlayer = new MediaPlayer(hit);
	    mediaPlayer.setOnEndOfMedia(new Runnable() {
	        public void run() {
	          mediaPlayer.seek(Duration.ZERO);
	        }
	    });
	    mediaPlayer.setAutoPlay(true);
	}
}