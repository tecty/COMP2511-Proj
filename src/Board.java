import java.util.ArrayList;
class Board {
	public int [][] Board;
	public ArrayList<Car> Car;
	//移动车子的顺序，存车子的id
	public ArrayList<Integer> carID;
	
	
	
	public Board(ArrayList<Car> c) {
		this.Board = new int [6][6];
		for(int i = 0; i < 6; i ++) {
			for(int j = 0; j < 6; j ++) {
				this.Board[i][j] = -1;
			}
		}
		this.Car = c;
		this.carID = new ArrayList<Integer>();
	}
	
	
	//for debug
	public static void printB(Board in) {
		for(int i = 0;i < 6;i ++) {
			for(int j = 0;j < 6; j ++) {
				System.out.print(" " + in.Board[i][j]);
			}
			System.out.println();
		}
		
		for(int i = 0; i < in.Car.size();i ++) {
			System.out.print("\nCar " + in.Car.get(i).num +" : ");
			
			for(int j = 0; j < in.Car.get(i).Paths.size(); j ++) {
				System.out.print(" (" + in.Car.get(i).Paths.get( j ).x1 + ","+
						in.Car.get(i).Paths.get( j ).y1 + ") ("+ 
						in.Car.get(i).Paths.get( j ).x2 + ","+
						in.Car.get(i).Paths.get( j ).y2 + ") + " );
			}
					
		}
	}
	
	
	
	
	
	public static void main(String[] args){
	      
		  
		  
	      ArrayList<Car> Cs = new ArrayList<Car>();
	      
	      Coordinate Czero = new Coordinate(2,4,2,5);
	    //  System.out.println(Czero.x1 + " "+ Czero.x2 + " " + Czero.y1 + " " + Czero.y2);
	      Car x = new Car(0,Czero);
	      Cs.add(x);	
	      
	      Coordinate C1 = new Coordinate(0,3,2,3);
	    //  System.out.println(Czero.x1 + " "+ Czero.x2 + " " + Czero.y1 + " " + Czero.y2);
	      Car x1 = new Car(1,C1);
	      Cs.add(x1);	
	      
	      
	      Board initial = new Board(Cs);
	      initial.Board[2][4] = 0;
	      initial.Board[2][5] = 0;
	      
	      initial.Board[0][3] = 1;
	      initial.Board[1][3] = 1;
	      initial.Board[2][3] = 1;

	      printB(initial); 
	      
	      System.out.println("\n\nTest to move car0 to  (2,3), (2,4) ");
	      Coordinate test0 = new Coordinate(2,3,2,4);
	      initial.Car.get(0).Paths.add(test0);
	      System.out.println(initial.isValidMove(initial.Board, initial.Car.get(0)));
	      
	      System.out.println("\n\nTest to move car1 to  (3,3), (5,3) ");
	      Coordinate test1 = new Coordinate(3,3,5,3);
	      initial.Car.get(1).Paths.add(test1);
	      System.out.println(initial.isValidMove(initial.Board, initial.Car.get(1)));
	      
	      //sample for adding  Car1 
	      //如果只是移动了car 1 而且 car 1 的move 是允许的情况下，加入carNum
	      if(initial.isValidMove(initial.Board, initial.Car.get(1))) {
	    	  	  initial.moveCar(1);
	      }
	      
	      printB(initial);
	      
	      
	      
	      System.out.println("\n\nTest to move car0 to  (2,3), (2,4) ");
	      Coordinate test01 = new Coordinate(2,3,2,4);
	      initial.Car.get(0).Paths.add(test01);
	      System.out.println(initial.isValidMove(initial.Board, initial.Car.get(0)));
	      if(initial.isValidMove(initial.Board, initial.Car.get(0))) {
	    	  	  initial.moveCar(0);
	      }
	      
	      printB(initial);
	      
	      
	      
	      //undo last move
	      System.out.println("\n\n Test undo last move ");
	      initial.undo();
	      printB(initial);
	      
	      //undo last move
	      System.out.println("\n\n Test undo last move ");
	      initial.undo();
	      printB(initial);
	}
	
	public boolean undo() {
		//cannot undo at the stat of game
		if ( this.carID.size() == 0) {
			return false;
		}
		int cID = this.carID.get( this.carID.size() -1 );
		
		Car c = this.Car.get(cID);
		Coordinate co = c.Paths.get( c.Paths.size() -1 );
		Coordinate preCo = c.Paths.get( c.Paths.size() -2 );
		
		//move by row
		if(co.x1 == co.x2) {
			//reset previous
			for(int i = co.y1 ; i <= co.y2; i ++) {
				this.Board[co.x1][i] = -1;	
			}
			
			//add new
			for(int i = preCo.y1 ; i <= preCo.y2; i ++) {
				this.Board[preCo.x1][i] = cID;
			}
			
		}else if(co.y1 == co.y2) {
			//reset previous
			for(int i = co.x1; i <= co.x2; i ++) {
				this.Board[i][co.y1] = -1;
			}
			
			//add new
			for(int i = preCo.x1; i <= preCo.x2; i ++) {
				this.Board[i][preCo.y1] = cID;
			}
			
		}
		
		//remove form paths
		c.Paths.remove(c.Paths.size() -1 );
		//remove from carID
		this.carID.remove(this.carID.size() -1 );
		
		return true;
	}
	
	// add cID to carID list, reset previous board, move/add new board
	public void moveCar(int cID) {
		//add to carID list
		this.carID.add( cID);
		
		Car c = this.Car.get(cID);
		Coordinate co = c.Paths.get( c.Paths.size() -1 );
		Coordinate preCo = c.Paths.get( c.Paths.size() -2 );
		
		//move by row
		if(co.x1 == co.x2) {
			//reset previous
			for(int i = preCo.y1 ; i <= preCo.y2; i ++) {
				this.Board[preCo.x1][i] = -1;
			}
			
			//add new
			for(int i = co.y1 ; i <= co.y2; i ++) {
				this.Board[co.x1][i] = cID;	
			}
			
		}else if(co.y1 == co.y2) {
			//reset previous
			for(int i = preCo.x1; i <= preCo.x2; i ++) {
				this.Board[i][preCo.y1] = -1;
			}
			//add new
			for(int i = co.x1; i <= co.x2; i ++) {
				this.Board[i][co.y1] = cID;
			}
		}
		
	}
	
	
	
	public  boolean isValidMove(int [][] b, Car c) {
		//boolean isValid = false;
		Coordinate co = c.Paths.get(c.Paths.size()-1);
		
		if(c.num == 0 && (co.y1 > 5 || co.y2 > 5)) {
			//congratulations
			return true;
		}
		
		//out of range
		if(co.x1 > 5 || co.x2 > 5 || co.y2 > 5 || co.y2 > 5 ||
		 co.x1 < 0 || co.x2 < 0  || co.y2 < 0  || co.y2 < 0) {
			c.Paths.remove(  c.Paths.size()-1 );
			return false;
		}
		
		//move by row
		if(co.x1 == co.x2) {
			for(int i = co.y1 ; i <= co.y2; i ++) {
				if(b[co.x1][i] != -1 && b[co.x1][i] != c.num) {
					c.Paths.remove(  c.Paths.size()-1 );
					return false;
				}
			}
		}else if(co.y1 == co.y2) {
			for(int i = co.x1; i <= co.x2; i ++) {
				if(b[i][co.y1] != -1 && b[i][co.y1] != c.num) {
					c.Paths.remove(  c.Paths.size()-1 );
					return false;
				}
			}
		}
		
		
		/* debug
		for(int i = 0; i < b.length; i ++) {
			for(int j = 0; j < b.length; j ++) {
				System.out.print(" " + b[i][j]);
			}
			System.out.println();
		}
		*/
		
		return true;
	}
	
	
	
}
