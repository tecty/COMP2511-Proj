package puzzleAlgorithm;

import java.util.ArrayList;

import game.Car;

public interface PuzzleAlgorithm {
	public ArrayList<Car> generatePuzzle(boolean isExpert);
	public int getRecommendSteps();
}
