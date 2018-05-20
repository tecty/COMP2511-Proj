package setting;

import java.nio.file.Paths;

import javafx.scene.media.*;

public class SoundEffect{
	private static MediaPlayer mediaPlayer;
	
	public static void play(String fileName) {
		// TODO Auto-generated method stub
		Media hit = new Media(Paths.get(fileName).toUri().toString());
		mediaPlayer = new MediaPlayer(hit);
		mediaPlayer.play();
	}

}
