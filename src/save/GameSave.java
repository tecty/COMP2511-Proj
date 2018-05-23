package save;

import java.io.Serializable;
import java.util.ArrayList;

import puzzleModel.Algorithm;
import puzzleModel.Board;
import puzzleModel.Generator;
import setting.Setting;

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
	public String getFileName(){
		// try to remove dependent code
		return name +".sav";
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

	public void useHint(){
		this.hintNum--;
	}
	protected void addHint(int num){
		this.hintNum ++;
	}

	public int getTotalStar() {
		int sum = 0;
		for(Level each : allLevels) {
			sum += each.userStar();
		}
		return sum;
	}
	
	public void gameGenerate() {
		int stepRequire ;

		// add all the empty level
		for (int i = 0; i < NUM_OF_LEVEL; i++) {
			allLevels.add(new Level(this));
		}

		// load first two puzzle
        loadPuzzle(allLevels.get(0),3);
        loadPuzzle(allLevels.get(1),5);

        // use background daemon to load the rest.
        for (int i = 2; i < NUM_OF_LEVEL; i++) {
			stepRequire = 2*i +3;
			// step require must be max out at 10
			// or this save is in expert mode
			// then it would be set to 10 manually
			if (stepRequire>10 || isExpertMode)
				stepRequire = 10;

			// create a prepare to run thread
            PuzzleCreatorThread thisThread =
                    new PuzzleCreatorThread(allLevels.get(i),stepRequire);



			// send the generate task to
            // background executor
            Setting.puzzleCreator.submit(thisThread);
//            thisThread.run();

			// load this puzzle
//			loadPuzzle(
//					allLevels.get(i),
//					stepRequire
//			);
		}




//		for(int i = 0; i < NUM_OF_LEVEL; i++) {
//			System.out.println("looping");
//		    stepRequire = 2*i+3;
//		    if(stepRequire> 10){
//		        // max the step require to 10
//		        stepRequire = 10;
//            }
//			// add a level
//		    if(isExpertMode) allLevels.add(gameGenerate(10));
//		    else allLevels.add(gameGenerate(stepRequire));
//		}

		// save this file when generated 9 puzzles.
//		SaveManager.save(this);
	}
	
	public Level getLevel(int num) {
//		if(allLevels.get(num)==null) System.out.println("empty");
		return allLevels.get(num);
	}
	
	//following functions change some information stored in the save slot
	public void setLevelCleared(int levelCleared) {
		this.levelCleared =  levelCleared+1;
	}

	/**
	 * Load a new puzzle to an empty level.
	 * @param level Level need to be load.
	 * @param steps Require step for that puzzle.
	 * @return
	 */
	public void loadPuzzle(Level level, int steps) {
		//the generated set of arranged cars and the corresponding recommend steps are imported in
        Generator generator = new Generator();

        // print which level it has generated
		System.out.println("Generated level: "+ allLevels.indexOf(level));

		// use algorithm to generate the board
        puzzleModel.Board board =  generator.generateRandomBoard(steps);
		// solve the puzzle to get a correct recommended step
        Algorithm alg = new Algorithm();
        Board solved = alg.solve(board);
		// load the new puzzle and correct
		// recommend step into this save
		level.loadPuzzle(
				board.toCarList(),
				solved.carID.size() + 1);
	}

	/**
	 * Flush this save to disk, this must happen
	 * atomically.
	 */
	public void flush(){
		synchronized (this){
			// because save would destroy the file,
			// so, only can save one time.
			SaveManager.save(this);
		}
	}

//	private void addPuzzle(int stepsRequire){
//	    // add a brand new puzzle by game generator;
//        this.allLevels.add(gameGenerate(stepsRequire));
//        synchronized (this){

//        }
//    }
}
