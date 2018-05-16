import java.util.ArrayList;
import java.util.List;
class Board {
	public int [][] Board;
	public ArrayList<Car> Car;
	//绉诲姩杞﹀瓙鐨勯『搴忥紝瀛樿溅瀛愮殑id
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
					this.Board[co.x1][i] = c.num;	
				}
				
			}else if(co.y1 == co.y2) {
				//update new
				for(int i = co.x1; i <= co.x2; i ++) {
					this.Board[i][co.y1] = c.num;
				}
			}
		}	
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
		//涓�鏍�
//		for(int i = 0; i< this.Car.size(); i ++) {
//			 if (!this.Car.get(i).equals(b.Car.get(i))) return false;
//		}
		
		
		return true;
		
	}
	
	
//	@Override
//	protected ArrayList<Car> clone() throws CloneNotSupportedException {
//		return (ArrayList<Car>) super.clone();
//	}
//	@Override
//	protected Board clone() throws CloneNotSupportedException {
//		return (Board) super.clone();
//	}
	
	
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
			System.out.print("\nCar " + in.Car.get(i).num +" : ");
			
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
	}
	
	
	
	
	/* MAIN TO TEST BOARD, CAR AND COORDINATE
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
	      //濡傛灉鍙槸绉诲姩浜哻ar 1 鑰屼笖 car 1 鐨刴ove 鏄厑璁哥殑鎯呭喌涓嬶紝鍔犲叆carNum
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
	*/
	
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
	
	public List<Board> getPossible() {
		List<Board> possibleBoards = new ArrayList<Board>();

		/** Construct all boards that arise from this one */
		for (int i = 0; i < this.Car.size(); i++) {
			Car car = this.Car.get(i);

			// try to move each block once is horizontal block
			if (car.Paths.get(0).x1 == car.Paths.get(0).x2) {
				// Can the block move to the left?
				if ((car.Paths.get(0).y1 > 0) && (this.Board[car.Paths.get(0).x1][car.Paths.get(0).y1 - 1] == -1)) {

					// All moves go to as far left as they can
					int counter = car.Paths.get(0).y1;
					while (counter > 0 && this.Board[car.Paths.get(0).x1][counter - 1] == -1) {
						counter--;
						// added to make it only move one space
						break;
					}

					// Construct a new list of blocks to make new gameboard
					ArrayList<Car> cars = new ArrayList<Car>();
					for (int j = 0; j < this.Car.size(); j++) {
						if (j == i) {
							Coordinate l = new Coordinate(car.Paths.get(0).x1,counter,car.Paths.get(0).x2,counter+(car.Paths.get(0).y2-car.Paths.get(0).y1+1));
							ArrayList <Coordinate> ls = new ArrayList <Coordinate> ();
						//	blocks.add(j, new Block(block.id, block.y, counter, block.isHorizontal, block.kind, block.length));
							ls.add(l);
							cars.add(j, new Car(car.num, ls));
						}
						else
							cars.add(j, this.Car.get(j).getCopy());
					}
					possibleBoards.add(new Board(cars, new ArrayList<Integer>()));

				}

				// Can the block move to the right?
				if ((car.Paths.get(0).y2 < 6) && (this.Board[car.Paths.get(0).x1][car.Paths.get(0).y2] == -1)) {

					// All moves go to as far right as they can
					int counter = car.Paths.get(0).y1;
					while (counter < 6 - (car.Paths.get(0).y2 -  car.Paths.get(0).y1) && this.Board[car.Paths.get(0).x1][counter +(car.Paths.get(0).y2 -  car.Paths.get(0).y1)] == -1) {
						counter++;
						// added to make it only move one space
						break;
					}

					// Construct a new list of blocks to make new gameboard
					ArrayList<Car> cars = new ArrayList<Car>();
					for (int j = 0; j < this.Car.size(); j++) {
						if (j == i) {
					//		blocks.add(j, new Block(block.id, block.y, counter, block.isHorizontal, block.kind, block.length));
							Coordinate l = new Coordinate(car.Paths.get(0).x1,counter,car.Paths.get(0).x2,counter+(car.Paths.get(0).y2-car.Paths.get(0).y1+1));
							ArrayList <Coordinate> ls = new ArrayList <Coordinate> ();
							ls.add(l);
							cars.add(j, new Car(car.num,ls));
						}
						else
							cars.add(j, this.Car.get(j).getCopy());
					}
					possibleBoards.add(new Board(cars, new ArrayList<Integer>()));

				}
			}
			else {

				// Can the block move up?
				if ((car.Paths.get(0).x1 > 0) && (this.Board[car.Paths.get(0).x1- 1][car.Paths.get(0).y1] == -1)) {

					// All moves go to as far up as they can
					int counter = car.Paths.get(0).x1;
					while (counter > 0 && this.Board[counter - 1][car.Paths.get(0).y1] == -1) {
						counter--;
						// added to make it only move one space
						break;
					}

					// Construct a new list of blocks to make new gameboard
					ArrayList<Car> cars = new ArrayList<Car>();
					for (int j = 0; j < this.Car.size(); j++) {
						if (j == i) {
							Coordinate l = new Coordinate(counter,car.Paths.get(0).y1,counter+(car.Paths.get(0).x2-car.Paths.get(0).x1+1),car.Paths.get(0).y2);
							ArrayList <Coordinate> ls = new ArrayList<Coordinate>();
							ls.add(l);
							cars.add(j, new Car(car.num,ls));
							//blocks.add(j, new Block(block.id, counter, block.x, block.isHorizontal, block.kind, block.length));
						}
						else
							cars.add(j, this.Car.get(j).getCopy());
					}
					possibleBoards.add(new Board(cars, new ArrayList<Integer>()));
				}

				// Can the block move down?
				if ((car.Paths.get(0).x2 < 6) && (this.Board[car.Paths.get(0).x2][car.Paths.get(0).y1] == -1)) {

					// All moves go to as far down as they can
					int counter = car.Paths.get(0).x1;
					while (counter < 6 - ((car.Paths.get(0).x2 - car.Paths.get(0).x1)+1) && this.Board[counter +  ((car.Paths.get(0).x2 - car.Paths.get(0).x1)+1)][car.Paths.get(0).y1] == -1) {
						counter++;
						// added to make it only move one space
						break;
					}

					// Construct a new list of blocks to make new gameboard
					ArrayList<Car> cars = new ArrayList<Car>();
					for (int j = 0; j < this.Car.size(); j++) {
						if (j == i) {
							Coordinate l = new Coordinate (counter,car.Paths.get(0).y1,counter+ ((car.Paths.get(0).x2 - car.Paths.get(0).x1)+1),car.Paths.get(0).y2);
							ArrayList <Coordinate> ls = new ArrayList<Coordinate>();
							ls.add(l);
							cars.add(j,new Car(car.num,ls));
							//blocks.add(j, new Block(block.id, counter, block.x, block.isHorizontal, block.kind, block.length));
						}
						else {
							cars.add(j,this.Car.get(j).getCopy());
							//blocks.add(j, this.blocks.get(j).getCopy());
						}
					}
					possibleBoards.add(new Board(cars, new ArrayList<Integer>()));
				}

			}

		}

		return possibleBoards;
	}

	
}
