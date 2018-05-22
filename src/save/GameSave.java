package save;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

import game.Car;
import puzzleAlgorithm.NullAlgorithm;
import puzzleModel.Generator;

public class GameSave implements Serializable{
	//version id
	private static final long serialVersionUID = 1L;

	//save-slot name
	String name;
	
	//the total number of puzzles in one save
	private final static int NUM_OF_LEVEL = 9;

	//currently totally generate 9 boards in the save
	ArrayList<Level> allLevels;
	boolean isExpertMode;
	
	//variables recording the progress of this save-slot
	//number of levels cleared
	private int levelCleared;
	//number of remaining hint chances
	private int hintNum;	
	
	//this should be initialized when a New Game starts
	//so the initializations of all boards should be carried out here
	public GameSave(String name, boolean isExpertMode) {
		//record the name of the slot
		this.name = name;
		//record the gaming mode (only novice or expert)
		this.isExpertMode = isExpertMode; 
		
		//initialize the variables recording game progress
		levelCleared = 0; //no level is cleared initially
		hintNum = 3; //give this save-slot three stars  
		
		//generate and record enough level boards
		allLevels = new ArrayList<>();
		
	}
	
	//following functions return useful information saved in the save slot
	public String getName() {
		return name;
	}
	
	public String printExpertMode() {
		if(isExpertMode) return "Expert Mode";
		return "Novice Mode";
	}
	
	public boolean expertMode() {
		return isExpertMode;
	}
	
	public int getLevelCleared() {
		return levelCleared;
	}
	
	public int getHintRemain() {
		return hintNum;
	}
	
	public int getTotalStar() {
		int sum = 0;
		for(Level each : allLevels) {
			sum += each.userStar();
		}
		return sum;
	}
	
	public void loadPuzzle() {
		int stepRequire ;

		// this must be long , so i try to time it
        long startTime = System.currentTimeMillis();
		System.out.println("here1");

		for(int i = 0; i < NUM_OF_LEVEL; i++) {
			System.out.println("looping");
		    stepRequire = 2* NUM_OF_LEVEL + 1;
		    if(stepRequire> 12){
		        // max the step require to 12
		        stepRequire = 12;
            }
//			allLevels.add(gameGenerate(stepRequire));
			allLevels.add(new Level(new NullAlgorithm().generatePuzzle(true), 3));
			System.out.println("now the "+i+" loop");
		}
        System.out.println("Spend for 9 puzzles "+
                (System.currentTimeMillis()-startTime)
                + " ms"
        );
	}
	
	public Level getLevel(int num) {
		if(allLevels.get(num)==null) System.out.println("empty");
		return allLevels.get(num);
	}
	
	//following functions change some information stored in the save slot
	public void setLevelCleared(int levelCleared) {
		this.levelCleared =  levelCleared+1;
	}
	
	public Level gameGenerate(int steps) {
		//the generated set of arranged cars and the corresponding recommend steps are imported in
//		PuzzleAlgorithm algorithm = new NullAlgorithm();
//		System.out.println("generate fine");
        Generator generator = new Generator();

        puzzleModel.Board board =  generator.generateRandomBoard(steps);
        for (Iterator<Car> it = board.toCarList().iterator(); it.hasNext(); ) {
            Car eachCar = it.next();
            eachCar.dumpCar();
        }
//        System.exit(0);
        Level newLevel = new Level(board.toCarList(),steps);
        return newLevel;
	}
}
