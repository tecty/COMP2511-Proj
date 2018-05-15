
import java.util.*;

public class Main {

	public static void main(String[] args){
		  
	      ArrayList<Car> Cs = new ArrayList<Car>();
	      
	      Coordinate Czero = new Coordinate(2,1,2,2);
	    //  System.out.println(Czero.x1 + " "+ Czero.x2 + " " + Czero.y1 + " " + Czero.y2);
	      Car x = new Car(0,Czero);
	      Cs.add(x);	
	      
	     // Coordinate C1 = new Coordinate(0,3,2,3);
	      Coordinate C1 = new Coordinate(1,3,3,3);
	    //  System.out.println(Czero.x1 + " "+ Czero.x2 + " " + Czero.y1 + " " + Czero.y2);
	      Car x1 = new Car(1,C1);
	      Cs.add(x1);	
	      
	      
	      Board initial = new Board(Cs, new ArrayList<Integer>());
//	      initial.Board[2][1] = 0;
//	      initial.Board[2][2] = 0;
//	      
//	      initial.Board[0][3] = 1;
//	      initial.Board[1][3] = 1;
//	      initial.Board[2][3] = 1;

	      initial.printB(initial); 
	      
	      Algorithm alg = new Algorithm();
	      Board game = alg.Algorithm( initial );
	      
	      if(game != null)
	    	  	game.printB(game);
	      else 
	    	  	System.out.println("\n\n\tGAME returnned null");
	    	  	
	      
	}
}
