import java.io.File;

import javafx.scene.media.*;
import javafx.util.Duration;

public class Bgm {
	public void play(String musicFile) {
		Media sound = new Media(new File(musicFile).toURI().toString());
		MediaPlayer mediaPlayer = new MediaPlayer(sound);
		mediaPlayer.setOnEndOfMedia(new Runnable() {
		       public void run() {
		         mediaPlayer.seek(Duration.ZERO);
		       }
		   });
		mediaPlayer.play();
	}
}
