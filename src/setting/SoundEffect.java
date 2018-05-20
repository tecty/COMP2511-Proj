package setting;

import java.nio.file.Paths;

import javafx.scene.media.*;

public class SoundEffect{
	private static MediaPlayer mediaPlayer;
	private static boolean playable = true;
	
	public static void changeStat(boolean result) {
		playable = result;
	}
	
	public static boolean getStat() {
		return playable;
	}
	
	public static void play(String fileName) {
		if(playable) {
			// TODO Auto-generated method stub
			Media hit = new Media(Paths.get(fileName).toUri().toString());
			mediaPlayer = new MediaPlayer(hit);
			mediaPlayer.play();
		}
		else return;
	}

}
