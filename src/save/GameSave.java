package save;

import setting.Setting;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Class hold the saved information.
 */
public class GameSave implements Serializable{
	//version id
	private static final long serialVersionUID = 1L;

	//save-slot name
	private String name;
	
	//the total number of puzzles in one save
	protected final static int NUM_OF_LEVEL = 9;

	//currently totally generate 9 boards in the save
	private ArrayList<Level> allLevels;
	private boolean expertMode;
	
	//variables recording the progress of this save-slot
	//number of levels cleared
	private int levelCleared;

	// how many stars user gets
	private int totalStars;
	// how many user has use his stars
	private int usedStars;

    /**
     * Initialize a save file with a name and whether
     * it is a expert save.
     * @param name Name of the save file.
     * @param expertMode Whether it's a expert save.
     */
	public GameSave(String name, boolean expertMode) {
		//record the name of the slot
		this.name = name;
		//record the gaming mode (only novice or expert)
		this.expertMode = expertMode;
		
		//initialize the variables recording game progress
		levelCleared = 0; //no level is cleared initially

		//generate and record enough level boards
		allLevels = new ArrayList<>();

		// total stars initial is 0
		totalStars = 0;
		// every 3 stars can get one hint
		// initially user have hint
		usedStars = -9;
		
	}

    /**
     * Return name of this save.
      * @return This save's name.
     */
	private String getName() {
		return name;
	}

    /**
     * Return the actual name save in file system.
     * @return File name is saved in file system.
     */
	protected String getFileName(){
		// try to remove dependent code
		return getName() +".sav";
	}

    /**
     * Print the prompt of expert mode.
     * @return The prompt text whether it is expert mode.
     */
	protected String printExpertMode() {
		if(expertMode) return "Expert Mode";
		return "Novice Mode";
	}

    /**
     * Return whether this save is expert mode.
     * @return Whether this save is expert save.
     */
	public boolean isExpertMode() {
		return expertMode;
	}

    /**
     * How many level user has finished.
     * @return The count of level user is finished currently.
     */
	public int getLevelCleared() {
		return levelCleared;
	}

    /**
     * Total stars user gets.
     * @return Total stars user got in this save.
     */
	protected int getTotalStar(){
		return totalStars;
	}

    /**
     * User has gain a star, needed to be recorded in this save.
     * @param starsGain How many stars user is gain this time.
     */
	protected void addStars(int starsGain){
		totalStars += starsGain;
	}

	/**
	 * User try to use a hint.
	 * @return True if they used a hint, false when
	 * they couldn't use that hint.
	 */
	protected boolean useHint(){
		if (getHintNum()>0){
			usedStars+=3;
			return true;
		}
		return false;
	}

    /**
     * Get how many hint remains in this save.
     * @return How many hint can spend in this save.
     */
	public int getHintNum() {
		return (totalStars-usedStars)/3;
	}

    /**
     * Generate the puzzles for each puzzle. Use background thread
     * pool to accelerate the procedure.
     */
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

    /**
     * Get the level by level number.
     * @param num Level number.
     * @return Level object of that level number.
     */
	public Level getLevel(int num) {
		return allLevels.get(num);
	}

    /**
     * Add up the level cleared, if user finished
     * an unfinished level.
     * @param levelCleared User currently finished level.
     */
	public void addLevelCleared(int levelCleared) {
	    // only can have more level clear when this
        // level is not finished before.
	    if (levelCleared == this.levelCleared)
	    	this.levelCleared =  levelCleared+1;
	}


	/**
	 * Flush this save to disk, this action must happen
	 * atomically.
	 */
	protected void flush(){
		synchronized (this){
			// because save would destroy the file,
			// so, only can save one time.
			SaveManager.save(this);
		}
	}


}
