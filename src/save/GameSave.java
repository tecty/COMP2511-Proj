package save;

import java.io.Serializable;
import java.util.ArrayList;

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
	boolean expertMode;
	
	//variables recording the progress of this save-slot
	//number of levels cleared
	private int levelCleared;
	//number of remaining hint chances
	private int hintNum;
	
	//this should be initialized when a New Game starts
	//so the initializations of all boards should be carried out here
	public GameSave(String name, boolean expertMode) {
		//record the name of the slot
		this.name = name;
		//record the gaming mode (only novice or expert)
		this.expertMode = expertMode;
		
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
		if(expertMode) return "Expert Mode";
		return "Novice Mode";
	}
	
	public boolean isExpertMode() {
		return expertMode;
	}

	public int getLevelCleared() {
		return levelCleared;
	}
	
	public int getHintRemain() {
		return hintNum;
	}

	/**
	 * User try to use a hint.
	 * @return True if they used a hint, false when
	 * they couldn't use that hint.
	 */
	public boolean useHint(){
		if (hintNum>0){
			this.hintNum--;
			return true;
		}
		return false;
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
		// add all the empty level
		for (int i = 0; i < NUM_OF_LEVEL; i++) {
			allLevels.add(new Level(this, i));
		}

		// load two puzzle in front ground
        allLevels.get(0).loadPuzzle();
        allLevels.get(1).loadPuzzle();
        // use background daemon to load the rest.
        for (int i = 2; i < NUM_OF_LEVEL; i++) {
			// create a prepare to run thread
            PuzzleCreatorThread thisThread =
                    new PuzzleCreatorThread(allLevels.get(i));

			// send the generate task to
            // background executor
            Setting.puzzleCreator.submit(thisThread);
		}

	}
	
	public Level getLevel(int num) {
		return allLevels.get(num);
	}
	
	//following functions change some information stored in the save slot
	public void setLevelCleared(int levelCleared) {
		this.levelCleared =  levelCleared+1;
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


}
