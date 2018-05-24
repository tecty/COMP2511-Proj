package save;

import java.io.Serializable;
import java.util.ArrayList;

import game.Car;
import puzzleModel.Algorithm;
import puzzleModel.Board;
import puzzleModel.Generator;

/**
 * Class store the information of each level.
 */
public class Level implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private ArrayList<Car> carList = new ArrayList<>();

	// level id for the id in the array in that save slot
    private int levelId;

	// not puzzle for this level
	private int recommendStep =-1;
	
	private int userStep=-1;
	private double userTime = Double.POSITIVE_INFINITY;
	// which save own this level
	private GameSave save;

	// whether this level have hinted by computer
    private boolean hinted ;

    /**
     * Create a blank level by inject the save it
     * belong and the level count of this level.
     * @param save The save object this level belong to.
     * @param levelId The level count of this level.
     */
	public Level(GameSave save, int levelId) {
	    // inject basic information about this level
		this.save = save;
		this.levelId = levelId;
		// whether this level is hinted
        hinted = false;
	}

    /**
     * Load a puzzle for this level (this level object have no puzzle).
     */
	protected void loadPuzzle(){
        //the generated set of arranged cars and the corresponding recommend steps are imported in
        Generator generator = new Generator();

        int levelId = save.allLevels.indexOf(this);
		// record the recommend step
        int steps;

        // steps calculate formula
		steps = levelId*2 + 3;
		// special case for steps calculation
		if(this.save.isExpertMode() || steps > 10)
			steps = 10;

        // use algorithm to generate the board
        puzzleModel.Board board =  generator.generateRandomBoard(steps);
        // solve the puzzle to get a correct recommended step
        Algorithm alg = new Algorithm();
        Board solved = alg.solve(board);

        // load the puzzle
		carList = board.toCarList();
		// load the steps computer could solve this puzzle
		recommendStep = solved.carID.size()+1;

		// print the
        // print which level it has generated
        System.out.println("Level: "+ levelId +" Need "+ recommendStep + " to solve");
		// flush the save to disk
		this.save.flush();
	}

    /**
     * Update the user's record of this level.
     * @param userStep Step is finished by user for this level.
     * @param userTime Time is finished by user for this level.
     */
	public void update(int userStep, double userTime) {
	    //flag to need file save
        boolean needUpdate = false;

        // the previous star
        int old_star = calStar();
        //should keep the best record in the saveslot
        if (this.userStep == -1 || this.userStep > userStep) {
            System.out.println("new " + userStep);
            this.userStep = userStep;
            needUpdate = true;
        }
        if (this.userTime == Double.POSITIVE_INFINITY || this.userTime > userTime) {
            System.out.println("new " + userTime);
            this.userTime = userTime;
            needUpdate = true;
        }

        // user may gain more star if they replay
        int new_star = calStar();
        if (needUpdate){
            if (new_star > old_star) {
                // now this level have gained more star
                // gain more hint
                save.addStars(new_star - old_star);
            }
            // flush the user record to disk
            save.flush();
        }
    }

    /**
     * Calculate how much stars is gain for given user performance.
     * @param userStep Step is finished by user for this level.
     * @param userTime Time is finished by user for this level.
     * @return Star is gained for this performance.
     */
    public int calStar(int userStep, double userTime){

        int total = 0;
        // this puzzle is not generated
        if (recommendStep == -1)
            return 0;
        if (hinted)
            // if a user use a hint,
            // he only could get one star at most
            return 1;
        //once passed the game
        if(userStep!=-1) total++;
        else
            // if user didn't finished this,
            // he won't get any stars
            return 0;

        //once get recommend steps
        if(userStep <= getRecommendStep()) total++;
        //once get recommend time
        if(userTime <= recommendTime()) total++;
        return total;
    }

    /**
     * How much star user is gain by the performance record.
     * @return Star user is gained by the record performance.
     */
	public int calStar() {
        return  calStar(userStep,userTime);
	}

    /**
     * What's this puzzle is about.
     * @return List of cars' information which construct the puzzle.
     */
	public ArrayList<Car> getCars(){
		return carList;
	}

    /**
     * The recommended time for user to solve this puzzle.
     * @return Recommend time for solve this puzzle.
     */
	public double recommendTime() {
        // use a approximate 2nd degree function to calculate
		double recTime = 0.22* recommendStep*recommendStep + 10;
		// need to be integer
        recTime =((int) recTime);
	    return recTime;
	}

    /**
     * Steps for perfectly solve this puzzle.
     * @return The perfect step to finish this puzzle.
     */
	public int getRecommendStep(){
		return recommendStep;
	}

    /**
     * User want to use a hint in this level, which means he would have
     * penalty of only getting one star.
     * @return Whether user get that hint.
     */
    public boolean useHint() {
        if (hinted && !save.isExpertMode()){
            // user already by this level's
            // hint and the user is play non-expert
            // mode, they would get a lot of
            // hint one he has purchase this level's hint
            return true;
        }
        if (save.useHint()){
            // save approve this level to use hint
            hinted = true;
            return true;
        }
        // use hint fail
        return false;
    }

    /**
     * Check whether this level store a puzzle.
     * @return Whether this level has a puzzle.
     */
    protected boolean isPuzzleLoaded() {
	    // use recommended step to
        // identify whether this level is empty
        return recommendStep == -1;
    }
}
