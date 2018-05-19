package save;

import java.io.Serializable;
import java.util.ArrayList;

import game.Car;

public class Level implements Serializable{
	private static final long serialVersionUID = 1L;
	
	ArrayList<Car> carList;
	int recommendStep;
	
	int userStep;
	double userTime;
	
	public Level(ArrayList<Car> carList, int recommendStep) {
		this.carList = carList;
		
		//initialize default record
		//recommend stuff
		this.recommendStep = recommendStep;
		
		//user stuff
		userStep = -1; //infinity
		userTime = Double.POSITIVE_INFINITY;
		System.out.println("level fine");
	}
	
	public void update(int userStep, double userTime) {
		//should keep the best record in the saveslot
		if(this.userStep==-1 || this.userStep > userStep) {
			System.out.println("new "+userStep);
			this.userStep = userStep;
		}
		if(this.userTime==Double.POSITIVE_INFINITY || this.userTime > userTime) {
			System.out.println("new "+userTime);
			this.userTime = userTime;
		}
	}	
	
	public int userStar() {
		int total = 0;
		//once passed the game
		if(userStep!=-1) total++;
		//once get recommend steps
		if(userStep < recommendStep) total++;
		//once get recommend time
		if(userTime < recommendTime(recommendStep)) total++;
		return total;
	}
	
	public ArrayList<Car> getCars(){
		return carList;
	}
	
	//best time consumed
	private double recommendTime(int recommendTime) {
		return 0;
	}
	
}