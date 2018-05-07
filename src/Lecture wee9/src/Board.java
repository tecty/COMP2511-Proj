import java.util.ArrayList;
class Board {
	public int [][] Board;
	public ArrayList<Car> Car;
	
	public Board(ArrayList<Car> Cars) {
		this.Board = new int [6][6];
		for(int i = 0; i < 6; i ++) {
			for(int j = 0; j < 6; j ++) {
				this.Board[i][j] = -1;
			}
		}
		this.Car = Cars;
	}
	public static void printB(Board in) {
		for(int i = 0;i < 6;i ++) {
			for(int j = 0;j < 6; j ++) {
				System.out.print(" " + in.Board[i][j]);
			}
			System.out.println();
		}
		
		for(int i = 0; i < in.Car.size();i ++) {
			System.out.println("Car " + in.Car.get(i).num +"   (" + in.Car.get(i).Paths.get(i).x1 + ","+ in.Car.get(i).Paths.get(i).y1 + ") ("+ in.Car.get(i).Paths.get(i).x2 + ","+in.Car.get(i).Paths.get(i).y2 + ")" );
		}
	}
	  public static void main(String[] args){
	      
		  
		  
	      ArrayList<Car> Cs = new ArrayList<Car>();
	      
	      Coordinate Czero = new Coordinate(2,4,2,5);
	    //  System.out.println(Czero.x1 + " "+ Czero.x2 + " " + Czero.y1 + " " + Czero.y2);
	      Car x = new Car(0,Czero);
	      Cs.add(x);	
//	      for(int i = 0;i < 5;i ++) {
//	    	  int x = random
//	    	  Coordinate x = new Coordinate()
//	      }
	      //Board initial = new Board(b,Cs);
	      
	      Board initial = new Board(Cs);
	      initial.Board[2][4] = 0;
	      initial.Board[2][5] = 0;
	      printB(initial); 
	 }
}
