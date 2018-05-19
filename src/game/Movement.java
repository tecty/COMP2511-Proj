/**
 * 
 */
package game;

/**
 * @author aki
 * Simple class to record a movement
 */
public class Movement {
	private final Car car;
	private final int movement;
	
	public Movement(Car car, int movement) {
		// TODO Auto-generated constructor stub
		this.car = car;
		this.movement = movement;
	}
	
	public Car getCar() {
		return car;
	}
	public int getMovement() {
		return movement;
	}
	
}
