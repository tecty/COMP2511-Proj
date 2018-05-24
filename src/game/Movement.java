package game;

/**
 * @author aki
 * Simple class to record a movement
 */
public class Movement {
	private final Car car;
	private final int movement;

	/**
	 * Record the car's movement.
	 * @param car The car is moved.
	 * @param movement The difference of this movement.
	 */
	protected Movement(Car car, int movement) {
		// TODO Auto-generated constructor stub
		this.car = car;
		this.movement = movement;
	}

	/**
	 * What this movement's car.
	 * @return The car of this movement.
	 */
	public Car getCar() {
		return car;
	}

	/**
	 * The distance of this movement.
	 * @return Distance of this movement.
	 */
	protected int getMovement() {
		return movement;
	}
	
}
