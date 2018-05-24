package game;

import java.util.Random;

/**
 * Static class for select image of a car.
 */
public class RandomSelector {
	/**
	 * Randomly select a image of the car.
	 * @param len The length of the car.
	 * @param dir The direction of this car.
	 * @param target Whether this car is the target car.
	 * @return A image name is selected.
	 */
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

}
