package setting;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.nio.file.Paths;

/**
 * Handling play a sound effect.
 */
public class SoundEffect{
	private static MediaPlayer mediaPlayer;
	private static boolean playable = true;

	/**
	 * Change whether playing a sound effect.
	 * @param result Whether user want a sound effect.
	 */
	protected static void changeStat(boolean result) {
		playable = result;
	}

	/**
	 * Get whether a sound effect is needed to play.
	 * @return Whether sound effect is turn on.
	 */
	protected static boolean getStat() {
		return playable;
	}

	/**
	 * Play a sound effect.
	 * @param fileName File name of that sound effect.
	 */
	public static void play(String fileName) {
		if(playable) {
			try {

				Media hit = new Media(Paths.get(fileName).toUri().toString());
				mediaPlayer = new MediaPlayer(hit);
				mediaPlayer.play();
			} catch (Exception e){
				// do nothing if system is not support
			}
		}
	}

}
