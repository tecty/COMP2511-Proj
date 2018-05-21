package setting;

import java.nio.file.Paths;
import javafx.scene.media.*;
import javafx.util.Duration;

public class Bgm {
	//mediaPlayer-object will not we cleaned away since someone holds a reference to it!	
	private static MediaPlayer mediaPlayer;
	private static boolean playable = true;

	public static void changeStat(boolean result) {
		playable = result;
		if(!result) {
			mediaPlayer.stop();
		}
		else {
			Media hit = new Media(Paths.get("music/bgm.mp3").toUri().toString());
		    mediaPlayer = new MediaPlayer(hit);
			mediaPlayer.setOnEndOfMedia(() -> mediaPlayer.seek(Duration.ZERO));
		    mediaPlayer.setAutoPlay(true);
		}
	}
	
	public static boolean getStat() {
		return playable;
	}
	
	public static void play()
	{
		if(playable) {
			Media hit = new Media(Paths.get("music/bgm.mp3").toUri().toString());
		    mediaPlayer = new MediaPlayer(hit);
		    mediaPlayer.setOnEndOfMedia(() -> mediaPlayer.seek(Duration.ZERO));
		    mediaPlayer.setAutoPlay(true);
		}
		else return;
	}
}