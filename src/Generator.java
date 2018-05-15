import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Generator {
	private static final Random RANDOM = new Random();
	public Board generateRandomBoard(Algorithm solver, int desiredLength) {
	    ArrayList<Car> someCars = new ArrayList<Car>();
	    List<Board> path = new ArrayList<Board>();
	    //first red car input ID = 0 Coordinate x1 = random,y1 = 2,x2 =random +2 ,y2=2
	    int x =  RANDOM.nextInt(2);
	    Coordinate ori = new Coordinate(x,2,(x+2),2);
	    someCars.add(new Car(0,ori));
	    
	    Board board = new Board(someCars);
	    board.Board[x][2] = 0;
	    board.Board[x+2][2] = 0;
	    
	    int odd = 1;
		int even = 2;
		
		int pathLength = 0;
		while (pathLength < desiredLength) {
			if (board.Car.size() < 14) {
				int tries = 50;
				// keep trying to place the block on the board
				while (tries-- > 0) {
					Car rand = getRandomCar(odd, even);
					int [][] gameboard = board.getGameboard();
					if (isFree(gameboard, rand)) {
						// add the block
						board.Car.add(rand);
						// update the board
						int id = rand.num;
						board.Board[rand.Paths.get(0).x1][rand.Paths.get(0).y2] = id;
						board.Board[rand.Paths.get(0).x2][rand.Paths.get(0).y2] = id;

						//need to find the solution of the new board if it doesn't have solution
						//should regenerate a new board
						List<Board> solved = solver.solve(board, desiredLength + 1);
						// this new 'arisen' board can still be solved.
						if (solved != null && !solved.isEmpty() && solved.size() > pathLength) {
							// increment the index counters
							if (rand.Paths.get(0).x1 == rand.Paths.get(0).x2) {
								odd += 2;
							}
							else {
								even += 2;
							}
							break;
						}
						// remove the piece and try a new piece
						else {
							board.Car.remove(rand);
							board.Board[rand.Paths.get(0).x1][rand.Paths.get(0).y2] = -1;
							board.Board[rand.Paths.get(0).x2][rand.Paths.get(0).y2] = -1;
						}

					}					
				}
			}
		}
		return board;
	}
	
	private boolean isFree(int[][] gameboard, Car b) {

		if (b.Paths.get(0).x1 == b.Paths.get(0).x2) {
			// iterate over each piece of the block
			for (int i = b.Paths.get(0).x1; i < b.Paths.get(0).x2; i++) {
				// if the board is not empty at spot, not free
				if (i >= gameboard.length || gameboard[b.Paths.get(0).y1][i] != 0)
					return false;
			}
		}
		else {
			// iterate over each piece of the block
			for (int i = b.Paths.get(0).y1; i < b.Paths.get(0).y2; i++) {
				// if the board is not empty at spot, not free
				if (i >= gameboard.length || gameboard[i][b.Paths.get(0).y1] != 0)
					return false;
			}
		}

		return true;
	}
	
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
	
	private Car getRandomCar(int odd, int even) {
		boolean isHorz = RANDOM.nextBoolean();
		int id = isHorz ? odd : even;
		int col = RANDOM.nextInt(6);
		int row = RANDOM.nextInt(6);
		
		//blocks start col and row
		// if horizontal, we can't have it on the prisoner row
		if (isHorz && row == 2) {
			row++;
		}
		
		//getRandomSize is for setting the size of car 2/3
		//if is horizontal end column should plus the size of car
        if(isHorz) {
        	int x2 = col + getRandomSize();
            Coordinate next = new Coordinate (row,col,row,x2);
        	return new Car(id,next);
        }
        //if is not horizontal end row should plus the size of car
        int y2 = row + getRandomSize();
        Coordinate next = new Coordinate (row,col,y2,col);
		return new Car(id,next);
	}
	
	private int getRandomSize() {
		if (RANDOM.nextInt(4) == 0)
			return 3;
		else
			return 2;
	}
}
