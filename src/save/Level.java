package save;

import java.io.Serializable;
import java.util.ArrayList;

import game.Car;
import puzzleModel.Algorithm;
import puzzleModel.Board;
import puzzleModel.Generator;

public class Level implements Serializable{
	private static final long serialVersionUID = 1L;
	
	ArrayList<Car> carList = new ArrayList<>();

	// level id for the id in the array in that save slot
    private int levelId;

	// not puzzle for this level
	private int recommendStep =-1;
	
	private int userStep=-1;
	private double userTime = Double.POSITIVE_INFINITY;
	// which save own this level
	private GameSave save;

	public Level(GameSave save, int levelId) {
		this.save = save;
		this.levelId = levelId;
	}

	public void loadPuzzle(){
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
        int old_star = userStar();
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
        int new_star = userStar();
        if (needUpdate){
            if (new_star != old_star) {
                // now this level have gained more star
                // gain more hint
                save.addHint(new_star - old_star);
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
	
	public int userStar() {
        return  calStar(userStep,userTime);
	}
	
	public ArrayList<Car> getCars(){
		return carList;
	}
	
	//best time consumed
	public double recommendTime() {
		return 10*recommendStep;
	}

	public int getRecommendStep(){
		return recommendStep;
	}
}
