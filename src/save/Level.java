package save;

import java.io.Serializable;
import java.util.ArrayList;

import game.Car;

public class Level implements Serializable{
	private static final long serialVersionUID = 1L;
	
	ArrayList<Car> carList = new ArrayList<>();
	int recommendStep =-1;
	
	int userStep=-1;
	double userTime = Double.POSITIVE_INFINITY;
	// which save own this level
	GameSave save;

	public Level(GameSave save) {
		this.save = save;
	}

	public void loadPuzzle(ArrayList<Car> carList, int recommendStep){
		this.carList = carList;
		this.recommendStep = recommendStep;
		// flush the save to disk
		this.save.flush();
	}

	public void update(int userStep, double userTime) {
		// the previous star
		int old_star = userStar();
		//should keep the best record in the saveslot
		if(this.userStep==-1 || this.userStep > userStep) {
			System.out.println("new "+userStep);
			this.userStep = userStep;
		}
		if(this.userTime==Double.POSITIVE_INFINITY || this.userTime > userTime) {
			System.out.println("new "+userTime);
			this.userTime = userTime;
		}

		// user may gain more star if they replay
		int new_star = userStar();
		if (new_star != old_star){
			// gain more hint
			save.addHint(new_star-old_star);
		}
		// flush the user record to disk
		save.flush();
	}	
	
	public int userStar() {
		int total = 0;
		// this puzzle is not generated
		if (recommendStep == -1)
			return total;

		//once passed the game
		if(userStep!=-1) total++;
		else
			// if user didn't finished this,
			// he won't get any stars
			return 0;

		//once get recommend steps
		if(userStep < recommendStep) total++;
		//once get recommend time
		if(userTime < recommendTime()) total++;
		return total;
	}
	
	public ArrayList<Car> getCars(){
		return carList;
	}
	
	//best time consumed
	private double recommendTime() {
		return 10*recommendStep;
	}
	
}
