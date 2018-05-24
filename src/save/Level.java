package save;

import java.io.Serializable;
import java.util.ArrayList;

import game.Car;
import puzzleModel.Algorithm;
import puzzleModel.Board;
import puzzleModel.Generator;

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


	public Level(GameSave save, int levelId) {
	    // inject basic information about this level
		this.save = save;
		this.levelId = levelId;
		// whether this level is hinted
        hinted = false;
	}

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
	
	public int calStar() {
        return  calStar(userStep,userTime);
	}
	
	public ArrayList<Car> getCars(){
		return carList;
	}
	
	//best time consumed
	public double recommendTime() {
        // use a approximate 2nd degree function to calculate
		double recTime = 0.22* recommendStep*recommendStep + 10;
		// need to be integer
        recTime =((int) recTime);
	    return recTime;
	}

	public int getRecommendStep(){
		return recommendStep;
	}

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

    protected boolean isPuzzleLoaded() {
	    // use recommended step to
        // identify whether this level is empty
        return recommendStep == -1;
    }
}
