import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Generator {
	private static final Random RANDOM = new Random();
	public Board generateRandomBoard(Algorithm solver, int desiredLength) {
	    ArrayList<Car> someCars = new ArrayList<Car>();
	  //  List<Board> path = new ArrayList<Board>();
	    //first red car input ID = 0 Coordinate x1 = random,y1 = 2,x2 =random +2 ,y2=2
	    int x =  RANDOM.nextInt(2);
	    Coordinate ori = new Coordinate(2,x,2,(x+1));
	    ArrayList <Coordinate> oro = new ArrayList<Coordinate> ();
	    oro.add(ori);
	    someCars.add(new Car(0,oro));
	    
	    
	    Board board = new Board(someCars,new ArrayList<Integer>());
	    Board.printB(board);
	    
	    int pathLength = 0;
	    Algorithm algo = new Algorithm();
		Board solvedo = algo.Algorithm(board);
		while (pathLength < desiredLength && solvedo.carID.size()+1 >= pathLength) {
			System.out.println("path length =  " + pathLength + "desiredLength =  " + desiredLength);
			if (board.Car.size() < 14) {
				int tries = 50;
				while(tries-- >0) {
				    Car rand = getRandomCar(board.Car.size());
				    System.out.println("car id = " + rand.num + "	x1 = " + rand.Paths.get(0).x1 + "	y1 = " + rand.Paths.get(0).y1 +"	x2 = " +rand.Paths.get(0).x2 +	"	y2 = "+rand.Paths.get(0).y2);
				    int [][] gameboard = board.Board;
				    if(isFree(gameboard,rand)) {
				    	board.Car.add(rand);
				    	board.updateBoard();
				    	System.out.println("after update :");
						Board.printB(board);
						
						Algorithm alg = new Algorithm();
						Board solved = alg.Algorithm(board);
						
						if (solved != null && solved.carID.size()+1 >= pathLength) {
							//pathLength = solved.carID.size()+1;
							System.out.println("board solution length = " + (solved.carID.size()+1));
							break;	
						}
						else {
							if(rand.Paths.get(0).x1 == rand.Paths.get(0).x2) {
							   int k = rand.Paths.get(0).y1;
							   while(k <= rand.Paths.get(0).y2) {
								   board.Board[rand.Paths.get(0).x1][k] = -1;
							   }
							}
							else {
								int k = rand.Paths.get(0).x1;
								   while(k <= rand.Paths.get(0).x2) {
									   board.Board[k][rand.Paths.get(0).y1] = -1;
								   }
							}
							board.Car.remove(board.Car.size()-1);
						}
				    }
				}
				
				Board winner = board;
				int steps = pathLength;
				
				for(Board b : board.getPossible()) {
					Algorithm alg = new Algorithm();
					Board solved = alg.Algorithm(b);
					if(solved != null) {
						System.out.println("the solution of this board " + (solved.carID.size()+1));
						int solvedLength = solved.carID.size()+1;
						if(solvedLength >= desiredLength) {
							return b;
						}
						else if(solvedLength >= steps) {
							steps = solvedLength;
							winner = b;
						}
						else {
							continue;
						}
					}
					else {
						continue;
					}
				}
				board = winner;
				pathLength = steps;
				System.out.println("winner board: ");
				Board.printB(board);
				
			}
		}
		Algorithm alg = new Algorithm();
		Board solved = alg.Algorithm(board);
		System.out.println("the solution of this board " + (solved.carID.size()+1));
		return board;
	}
	
	private boolean isFree(int[][] gameboard, Car b) {
		if(b.Paths.get(0).x1 == b.Paths.get(0).x2) {
			for(int i = b.Paths.get(0).y1;i <= b.Paths.get(0).y2;i++) {
				if(gameboard[b.Paths.get(0).x1][i] != -1) {
					return false;
				}
			}
		}
		else {
			for(int i = b.Paths.get(0).x1;i <= b.Paths.get(0).x2;i++) {
				if(gameboard[i][b.Paths.get(0).y1] != -1) {
					return false;
				}
			}
		}
		return true;
	}
	/*  
		//if is horizontal
		if (b.Paths.get(0).x1 == b.Paths.get(0).x2) {
			// iterate over each piece of the block
			for (int i = b.Paths.get(0).y1; i <= b.Paths.get(0).y2; i++) {
				// if the board is not empty at spot, not free
				if (i >= gameboard.length || gameboard[b.Paths.get(0).y1][i] != -1)
					return false;
			}
		}
		else {
			// iterate over each piece of the block
			for (int i = b.Paths.get(0).y2; i <= b.Paths.get(0).y2; i++) {
				// if the board is not empty at spot, not free
				if (i >= gameboard.length || gameboard[i][b.Paths.get(0).y1] != -1)
					return false;
			}
		}
		return true;
		*/
	
	/*
	 * for actual version should be like:
	 * private Car getRandomCr(int odd, int even) {
		boolean isHorz = RANDOM.nextBoolean();
		int id = isHorz ? odd : even;
		int col = RANDOM.nextInt(6);
		int row = RANDOM.nextInt(6);
		// if horizontal, we can't have it on the prisoner row
		if (isHorz && row == 2) {
			row++;
		}
		return new Car(id, row, col, isHorz,getRandomSize());
	}
	 */
	
	private Car getRandomCar(int Cid) {
		boolean isHorz = RANDOM.nextBoolean();
		int id = Cid;
		int size = getRandomSize();
		
		//blocks start col and row
		// if horizontal, we can't have it on the prisoner row
		
		if(isHorz) {
			int y1 = RANDOM.nextInt(6-size);
			int y2 = y1 + size - 1;
			int row = RANDOM.nextInt(6);
			if (row == 2) {
				row++;
			}
			Coordinate next = new Coordinate (row,y1,row,y2);
			ArrayList <Coordinate> nextP = new ArrayList <Coordinate>();
	        nextP.add(next);
	        return new Car(id,nextP);			
		}
		int x1 = RANDOM.nextInt(6-size);
		int x2 = x1 + size - 1;
		int col = RANDOM.nextInt(6);
		Coordinate next = new Coordinate (x1,col,x2,col);
		ArrayList <Coordinate> nextP = new ArrayList <Coordinate>();
        nextP.add(next);
        return new Car(id,nextP);
	}
		//getRandomSize is for setting the size of car 2/3
		//if is horizontal end column should plus the size of car
   /*     if(isHorz) {
        	int x2 = col + getRandomSize();
            Coordinate next = new Coordinate (row,col,row,x2);
            ArrayList <Coordinate> nextP = new ArrayList <Coordinate>();
            nextP.add(next);
        	return new Car(id,nextP);
        }
        //if is not horizontal end row should plus the size of car
        int y2 = row + getRandomSize();
        Coordinate next = new Coordinate (row,col,y2,col);
        ArrayList <Coordinate> nextP = new ArrayList <Coordinate>();
        nextP.add(next);
		return new Car(id,nextP);
		*/
	
	private int getRandomSize() {
		if (RANDOM.nextInt(4) == 0)
			return 3;
		else
			return 2;
	}
	
	
}
