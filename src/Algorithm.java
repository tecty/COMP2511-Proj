import java.util.*;


public class Algorithm {

	public Board Algorithm(Board board) {
		
		Set<Board> visited = new HashSet<Board>();
		
		Queue<Board> queue = new LinkedList<Board>();
		queue.add( board);
		
		System.out.println("\n\nQueue before while");
		printQueue(queue);
		
		System.out.println("\n\tLOOP\n");
		while(!queue.isEmpty()) {
			Board b = queue.poll();
			
			if(visited.contains(b)) {
				continue;
			}
			
			visited.add( b );
			
			//check final state
			if(unlockCar(b)) {
				return b;
			}
			
			addPossibleBoardsToQueue(queue, b);
			
			printQueue(queue);
			
			break;
		}
		
		
		//return board;
		return null;
	}
	
	private void printQueue(Queue<Board> queue) {
		System.out.println();
		for(Board b: queue) {
			b.printB(b);
		}
		System.out.println();
	}
	
	
	private void addPossibleBoardsToQueue(Queue<Board> queue, Board b) {
		//add all next possible boards to queue
		for(int i = 0; i < b.Car.size(); i ++) {
			Car c = b.Car.get(i);
			Coordinate co = c.Paths.get( c.Paths.size()-1 );
			
			//move by row
			if(co.x1 == co.x2) {
				
				//move to left
				if(co.y1 > 0 && b.Board[co.x1][co.y1-1] == -1) {
					//move car to furtherest distance
					int count = co.y1;
					while(count > 0 && b.Board[co.x1][count -1] == -1) {
						count --;
					}
					//get new position
					int length = co.y2 - co.y1 + 1;
					Coordinate left = new Coordinate(co.x1, count ,co.x2, count + length);
					//update position
					ArrayList<Car> car = (ArrayList<Car>)b.Car.clone();
					car.get(c.num).Paths.add(left);
					ArrayList<Integer> carID = (ArrayList<Integer>)b.carID.clone();
					carID.add(c.num);
					
					//add new board to queue
					queue.add(new Board(car, carID));
					
					
				}
				
				//move to right
				if(co.y2 < 6 && b.Board[co.x1][co.y1+1] == -1) {
					
				}
				
			}else if(co.y1 == co.y2) {
			//move by column
				
			}
			
		}
		
	}
	
	
	//check final state
	private boolean unlockCar(Board b) {
		//get coordinates of Car0 
		Car c = b.Car.get(0);
		Coordinate co = c.Paths.get(  c.Paths.size() -1  );
		
		//if the right of Car0 is empty, the game is cleared
		for(int i = co.y2; i < 6; i ++) {
			if(b.Board[co.x1][i] != -1 && b.Board[co.x1][i] != c.num ) {
				return false;
			}
		}
		
		return true;
	}
	
}

