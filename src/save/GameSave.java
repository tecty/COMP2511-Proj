package save;

import java.io.Serializable;
import java.util.ArrayList;

import puzzleAlgorithm.NullAlgorithm;
import puzzleAlgorithm.PuzzleAlgorithm;

import levelSelect.Level;

public class GameSave implements Serializable{
	private static final long serialVersionUID = 1L;

	//the total number of puzzles in one save
	private final static int NUM_OF_LEVEL = 9;
	
	//game saving file no
	private static int saveSlot = 0;
	
	//currently totally generate 9 boards in the save
	ArrayList<Level> allLevels;
	boolean isExpertMode;
	
	//this should be initialized when a New Game starts
	//so the initializations of all boards should be carried out here
	public GameSave(int isExpertMode) {
		//first record the gaming mode (only novice or expert)
		allLevels = new ArrayList<>();
		this.isExpertMode = (isExpertMode==1); 
		//generate and record enough level boards
		for(int i = 0; i < NUM_OF_LEVEL; i++) {
			allLevels.add(gameGenerate());
			System.out.println("now the "+i+" loop");
		}
		System.out.println("loop fine");
		System.out.println("final fine");
		saveSlot++;
	}
	
	public boolean expertMode() {
		return isExpertMode;
	}
	
	public int getSlotNum() {
		return saveSlot;
	}
	
	public Level getLevel(int num) {
		if(allLevels.get(num)==null) System.out.println("empty");
		return allLevels.get(num);
	}
	
	public Level gameGenerate() {
		//the generated set of arranged cars and the corresponding recommend steps are imported in
		PuzzleAlgorithm algorithm = new NullAlgorithm();
		Level newLevel = new Level(algorithm.generatePuzzle(isExpertMode), algorithm.getRecommendSteps());
		System.out.println("generate fine");
		return newLevel;
	}
}
