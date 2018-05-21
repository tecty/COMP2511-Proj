package puzzleAlgorithm;

import java.net.MalformedURLException;
import java.util.ArrayList;

import game.Car;
import game.MoveDir;

//this just simply copy the testing case we have always used
public class NullAlgorithm implements PuzzleAlgorithm{
	
	//ignore the difference of isExpert or not
	@Override
	public ArrayList<Car> generatePuzzle(boolean isExpert) {
		// TODO Auto-generated method stub
		ArrayList<Car> cars = new ArrayList<>();

		cars.add(new Car(MoveDir.HORIZONTAL,0,4,2,2));
		cars.add(new Car(MoveDir.VERTICAL,1,2,2,2));
		cars.add(new Car(MoveDir.VERTICAL,2,0,0,3));
		cars.add(new Car(MoveDir.VERTICAL,3,2,2,2));

		System.out.println("car fine");
		return cars;
	}

	//just the simplest three-step operation
	@Override
	public int getRecommendSteps() {
		// TODO Auto-generated method stub
		return 3;
	}

}
