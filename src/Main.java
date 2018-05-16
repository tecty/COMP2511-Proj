
import java.util.*;

public class Main {
	
	// VERSION HASHSET, CAR COMPARE LENGTH
	public static void main(String[] args){
		/*
		  System.out.println("TESTING EASY GAME");
	      ArrayList<Car> Cs = new ArrayList<Car>();
	      
	      ArrayList<Coordinate> Czero = new ArrayList<Coordinate>();
	      Czero.add( new Coordinate(2,1,2,2) );
	    //  System.out.println(Czero.x1 + " "+ Czero.x2 + " " + Czero.y1 + " " + Czero.y2);
	      Car x = new Car(0,Czero);
	      Cs.add(x);	
	      
	     // Coordinate C1 = new Coordinate(0,3,2,3);
	      ArrayList<Coordinate> C1 = new ArrayList<Coordinate>();
	      C1.add( new Coordinate(1,3,3,3) );
	    //  System.out.println(Czero.x1 + " "+ Czero.x2 + " " + Czero.y1 + " " + Czero.y2);
	      Car x1 = new Car(1,C1);
	      Cs.add(x1);
	      
	      
	      Board initial = new Board(Cs, new ArrayList<Integer>());
	      initial.printB(initial); 
	      
	      Algorithm alg = new Algorithm();
	      Board game = alg.Algorithm( initial );
	      
	      if(game != null)
	    	  	game.printB(game);
	      else 
	    	  	System.out.println("\n\n\tGAME returnned null");
	    	  
	      */
	      System.out.println("\n\nTESTING HARD GAME\n");
	      ArrayList<Car> C = new ArrayList<Car>();
	      ArrayList<Coordinate> Co;
	      Car cr;
	      Co = new ArrayList<Coordinate>();
	      Co.add(new Coordinate(2,0,2,1) );
	      cr = new Car(0, Co);
	      C.add(cr);
	      
	      Co = new ArrayList<Coordinate>();
	      Co.add(new Coordinate(0,0,0,2) );
	      cr = new Car(1, Co);
	      C.add(cr);
	      
	      Co = new ArrayList<Coordinate>();
	      Co.add(new Coordinate(1,2,3,2) );
	      cr = new Car(2, Co);
	      C.add(cr);
	      
	      Co = new ArrayList<Coordinate>();
	      Co.add(new Coordinate(3,0,4,0) );
	      cr = new Car(3, Co);
	      C.add(cr);
	      
	      Co = new ArrayList<Coordinate>();
	      Co.add(new Coordinate(5,0,5,2) );
	      cr = new Car(4, Co);
	      C.add(cr);
	      
	      Co = new ArrayList<Coordinate>();
	      Co.add(new Coordinate(0,5,2,5) );
	      cr = new Car(5, Co);
	      C.add(cr);
	      
	      Co = new ArrayList<Coordinate>();
	      Co.add(new Coordinate(3,4,3,5) );
	      cr = new Car(6, Co);
	      C.add(cr);
	      
	      Co = new ArrayList<Coordinate>();
	      Co.add(new Coordinate(4,4,5,4) );
	      cr = new Car(7, Co);
	      C.add(cr);
	      
	      Board b = new Board(C, new ArrayList<Integer>());
	      b.printB(b);
	      
	      Algorithm alg = new Algorithm();
	      Board game = alg.Algorithm( b );
	      
	      if(game != null)
	    	  	game.printB(game);
	      else 
	    	  	System.out.println("\n\n\tGAME returnned null");

	}
}
