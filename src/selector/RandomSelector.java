package selector;

import java.util.Random;

import game.MoveDir;

public class RandomSelector {
	public static String selectImg(int len, MoveDir dir, boolean target) {
		//this is a car
		int choice = new Random().nextInt(5)+1;
		if(target) return "carTarget.png";
		if(len==2) {
			if(dir==MoveDir.HORIZONTAL)
				return "car"+ choice+".png";
			else
				return "car"+ choice+"V.png";
		}
		//this is a truck
		else {
			if(dir==MoveDir.HORIZONTAL)
				return "truck"+ choice + ".png";
			else
				return "truck"+ choice + "V.png";
		}	
	}
	
	public static String selectSound(int len) {
		//this is a car
		int choice = new Random().nextInt(2)+1;
		if(len==2) 
			return "soundEffect/carhorn"+choice + ".mp3";
		else
			return "soundEffect/truckhorn"+ choice + ".mp3";
	}
	
	public static String selectFlashImg(int len, MoveDir dir, boolean target) {
		if(len==2) {
			if(dir==MoveDir.HORIZONTAL) {
				if(target) return "carTargetFlash.png";
				else return "carFlash.png";
			}
			else {
				return "carVFlash.png";
			}
		}
		else {
			if(dir==MoveDir.HORIZONTAL) {
				return "truckFlash.png";
			}
			else {
				return "truckVFlash.png";
			}
		}
	}
}
