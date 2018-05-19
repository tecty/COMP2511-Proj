package gameModel;
import java.util.ArrayList;
class Board {
	public int [][] Board;
	public ArrayList<Car> Car;
	//record the order of moving car
	public ArrayList<Integer> carID;
	
	
	
	public Board(ArrayList<Car> c, ArrayList<Integer> carID) {
		//initialize board
		this.Board = new int [6][6];
		for(int i = 0; i < 6; i ++) {
			for(int j = 0; j < 6; j ++) {
				this.Board[i][j] = -1;
			}
		}
		
		this.Car = c;
		//this.carID = new ArrayList<Integer>();
		this.carID = carID;
		
			
		updateBoard();
	}
	
	public void updateBoard() {
		for(int j = 0; j < this.Car.size(); j ++) {
			Car c = this.Car.get(j);
			Coordinate co = c.Paths.get( c.Paths.size()-1 );
			
			//move by row
			if(co.x1 == co.x2) {
				//update new
				for(int i = co.y1 ; i <= co.y2; i ++) {
					this.Board[co.x1][i] = c.getCarID();	
				}
				
			}else if(co.y1 == co.y2) {
				//update new
				for(int i = co.x1; i <= co.x2; i ++) {
					this.Board[i][co.y1] = c.getCarID();
				}
			}
		}	
	}
	
	public int getPathSize() {
		return this.carID.size()+1;
	}
	
	
	public ArrayList<Car> copyCarList(){
		ArrayList<Car> car = new ArrayList<Car>();
		for(Car c : this.Car ) {
			car.add( c.getCopy() );
		}
		return car;
	}
	
	
	@Override
	public int hashCode() {
		//System.out.println("CALLING HashBoard");
		int result = ((this.Car == null) ? 0 : this.Car.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object o) {
		//System.out.println("CALLING EqualsBoard");
		if (this == o) return true;
			
		if (o == null || !(o instanceof Board)) return false;
			
		Board b = (Board) o;
		if((this.Car == null && b.Car != null) ) {
			return false;
		}
		else if (!this.Car.equals(b.Car)) {
			return false;
		}
		return true;
		
	}
	
	
	//for debug
	public static void printB(Board in) {
		for(int i = 0;i < 6;i ++) {
			for(int j = 0;j < 6; j ++) {
				if(in.Board[i][j] == -1)
					System.out.print(" " + in.Board[i][j]);
				else
					System.out.print("  " + in.Board[i][j]);
			}
			System.out.println();
		}
		
		for(int i = 0; i < in.Car.size();i ++) {
			System.out.print("\nCar " + in.Car.get(i).getCarID() +" : ");
			
			for(int j = 0; j < in.Car.get(i).Paths.size(); j ++) {
				System.out.print(" (" + in.Car.get(i).Paths.get( j ).x1 + ","+
						in.Car.get(i).Paths.get( j ).y1 + ") ("+ 
						in.Car.get(i).Paths.get( j ).x2 + ","+
						in.Car.get(i).Paths.get( j ).y2 + ") + " );
			}
					
		}
		
		System.out.print("\nCarID list : ");
		for(int i = 0; i < in.carID.size(); i ++) {
			System.out.print(" " + in.carID.get(i));
		}
		System.out.println();
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
			//reset current
			for(int i = co.y1 ; i <= co.y2; i ++) {
				this.Board[co.x1][i] = -1;	
			}
			
			//reset previous
			for(int i = preCo.y1 ; i <= preCo.y2; i ++) {
				this.Board[preCo.x1][i] = cID;
			}
			
		}else if(co.y1 == co.y2) {
			//reset current
			for(int i = co.x1; i <= co.x2; i ++) {
				this.Board[i][co.y1] = -1;
			}
			
			//add previous
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
		//move by column
			for(int i = co.x1; i <= co.x2; i ++) {
				if(b[i][co.y1] != -1 && b[i][co.y1] != c.num) {
					c.Paths.remove(  c.Paths.size()-1 );
					return false;
				}
			}
		}
		
		
		
		return true;
	}
	
	public List<Board> getPossible() {
//		System.out.println("\n GETTING POSSIBLE BOARD");
		List<Board> possibleBoards = new ArrayList<Board>();
		for(int i = 0; i < this.Car.size();i ++) {
			Car car = this.Car.get(i);
		   // horizontal block move left
			if(car.Paths.get(0).x1 == car.Paths.get(0).x2) {
		    	if((car.Paths.get(0).y1 > 0) && this.Board[car.Paths.get(0).x1][car.Paths.get(0).y1-1] == -1) {
		    		Coordinate get = new Coordinate(car.Paths.get(0).x1,(car.Paths.get(0).y1-1),car.Paths.get(0).x2,(car.Paths.get(0).y2 -1));
		    	    ArrayList<Car>cars = new ArrayList<Car>();
		    	    for(int j = 0;j < this.Car.size();j ++) {
		    	    	if(j == i) {
		    	    		ArrayList<Coordinate> paths = new ArrayList<Coordinate>();
		    	    		paths.add(get);
		    	    		cars.add(new Car(j,paths));
		    	    	}
		    	    	else {
		    	    		 cars.add(j,this.Car.get(j).getCopy());
		    	    	}
		    	    }
		    	    Board g = new Board (cars,(new ArrayList <Integer>()));
		    	    possibleBoards.add(g);
		    	}
		    	if((car.Paths.get(0).y2 < 5) && this.Board[car.Paths.get(0).x1][car.Paths.get(0).y2 + 1] == -1) {
		    		Coordinate get = new Coordinate(car.Paths.get(0).x1,(car.Paths.get(0).y1+1),car.Paths.get(0).x2,(car.Paths.get(0).y2 +1));
		    	    ArrayList<Car>cars = new ArrayList<Car>();
		    	    for(int j = 0;j < this.Car.size();j ++) {
		    	    	if(j == i) {
		    	    		ArrayList<Coordinate> paths = new ArrayList<Coordinate>();
		    	    		paths.add(get);
		    	    		cars.add(new Car(j,paths));
		    	    	}
		    	    	else {
		    	    		 cars.add(j,this.Car.get(j).getCopy());
		    	    	}
		    	    }
		    	    Board g = new Board (cars,(new ArrayList <Integer>()));
		    	    possibleBoards.add(g);
		    	}
		    }
			else {
				//vertical block move up
		    	if((car.Paths.get(0).x1 > 0) && this.Board[car.Paths.get(0).x1 - 1][car.Paths.get(0).y1] == -1) {
		    		Coordinate get = new Coordinate((car.Paths.get(0).x1-1),car.Paths.get(0).y1,(car.Paths.get(0).x2-1),car.Paths.get(0).y2);
		    	    ArrayList<Car>cars = new ArrayList<Car>();
		    	    for(int j = 0;j < this.Car.size();j ++) {
		    	    	if(j == i) {
		    	    		ArrayList<Coordinate> paths = new ArrayList<Coordinate>();
		    	    		paths.add(get);
		    	    		cars.add(new Car(j,paths));
		    	    	}
		    	    	else {
		    	    		 cars.add(j,this.Car.get(j).getCopy());
		    	    	}
		    	    }
		    	    Board g = new Board (cars,(new ArrayList <Integer>()));
		    	    possibleBoards.add(g);
		    	}
		    	//move down
		    	if((car.Paths.get(0).x2 < 5) && this.Board[car.Paths.get(0).x2+1][car.Paths.get(0).y2 ] == -1) {
		    		Coordinate get = new Coordinate((car.Paths.get(0).x1+1),car.Paths.get(0).y1,(car.Paths.get(0).x2+1),car.Paths.get(0).y2);
		    	    ArrayList<Car>cars = new ArrayList<Car>();
		    	    for(int j = 0;j < this.Car.size();j ++) {
		    	    	if(j == i) {
		    	    		ArrayList<Coordinate> paths = new ArrayList<Coordinate>();
		    	    		paths.add(get);
		    	    		cars.add(new Car(j,paths));
		    	    	}
		    	    	else {
		    	    		 cars.add(j,this.Car.get(j).getCopy());
		    	    	}
		    	    }
		    	    Board g = new Board (cars,(new ArrayList <Integer>()));
		    	    possibleBoards.add(g);
		    	}
			}
		}
//		System.out.println("Finish possible board ");
		return possibleBoards;
	}

	public ArrayList<Car> getUnmovedCar() {
		// return the unmoved car of the solved map 
		ArrayList<Car> return_list= new ArrayList<>();
		for (int i = 0; i < Car.size(); i++) {
			if (Car.get(i).getPathSize() == 1) {
				// only have unused car
				return_list.add(Car.get(i));
			}
		}
		return return_list;
	}
	public void deleteCarByList(ArrayList<Car> carList) {
		for (int i = carList.size()-1; i >0; i--) {
			Car.remove(carList.get(i).getCarID());
		}
		for(int i = 0; i < 6; i ++) {
			for(int j = 0; j < 6; j ++) {
				this.Board[i][j] = -1;
			}
		}
		// re-render the board.
		updateBoard();

	}

}
