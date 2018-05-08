import java.util.ArrayList;
class Board {
	public int [][] Board;
	public ArrayList<Car> Car;
	
	
	
	public Board(ArrayList<Car> c) {
		this.Board = new int [6][6];
		for(int i = 0; i < 6; i ++) {
			for(int j = 0; j < 6; j ++) {
				this.Board[i][j] = -1;
			}
		}
		this.Car = c;
	}
	
	
	
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
	      System.out.println(isValidMove(initial.Board, initial.Car.get(0)));
	      
	      System.out.println("\n\nTest to move car1 to  (3,3), (5,3) ");
	      Coordinate test1 = new Coordinate(3,3,5,3);
	      initial.Car.get(1).Paths.add(test1);
	      System.out.println(isValidMove(initial.Board, initial.Car.get(1)));
	}
	
	public static boolean isValidMove(int [][] b, Car c) {
		boolean isValid = false;
		Coordinate co = c.Paths.get(c.Paths.size()-1);
		
		if(c.num == 0 && (co.y1 > 5 || co.y2 > 5)) {
			return true;
		}
		
		//out of range
		if(co.x1 > 5 || co.x2 > 5 || co.y2 > 5 || co.y2 > 5)
			return false;

		//move by row
		if(co.x1 == co.x2) {
			for(int i = co.y1 ; i <= co.y2; i ++) {
				if(b[co.x1][i] != -1) {
					return false;
				}
			}
		}else if(co.y1 == co.y2) {
			for(int i = co.x1; i <= co.x2; i ++) {
				if(b[i][co.y1] != -1) {
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
