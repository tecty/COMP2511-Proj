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
			
			System.out.println("PRINTING QUEUE");
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
			System.out.println();
		}
		
	}
	
	
	private void addPossibleBoardsToQueue(Queue<Board> queue, Board b) {
		//add all next possible boards to queue
		for(int i = 0; i < b.Car.size(); i ++) {
				System.out.println("\n i = " + i);
			Car c = b.Car.get(i);
			Coordinate co = c.Paths.get( c.Paths.size()-1 );
			
			System.out.println("PRINTING B");
			b.printB(b);
			System.out.println();
			
//			ArrayList<Car> car = new ArrayList(b.Car);
//			ArrayList<Integer> carID = new ArrayList(b.carID);
//			ArrayList<Car> car1 = new ArrayList(b.Car);
//			ArrayList<Integer> carID1 = new ArrayList(b.carID);
//			
//			ArrayList<Car> car = new ArrayList<Car>(b.Car);
//			ArrayList<Integer> carID = new ArrayList<Integer>(b.carID);
//			ArrayList<Car> car1 = new ArrayList<Car>(b.Car);
//			ArrayList<Integer> carID1 = new ArrayList<Integer>(b.carID);
			
//			ArrayList<Car> car = (ArrayList<Car>)b.Car.clone();
//			ArrayList<Integer> carID = (ArrayList<Integer>)b.carID.clone();
//			ArrayList<Car> car1 = (ArrayList<Car>)b.Car.clone();
//			ArrayList<Integer> carID1 = (ArrayList<Integer>)b.carID.clone();
			
			ArrayList<Car> car = b.copyCarList();
			ArrayList<Integer> carID = (ArrayList<Integer>)b.carID.clone();
			ArrayList<Car> car1 =  b.copyCarList();
			ArrayList<Integer> carID1 = (ArrayList<Integer>)b.carID.clone();
			
			//move by row
			if(co.x1 == co.x2) {
				
				//move left
				if(co.y1 > 0 && b.Board[co.x1][co.y1-1] == -1) {
					System.out.println("LEFT");

					//move car to furtherest distance
					int count = co.y1;
					int length = co.y2 - co.y1;
					
					while(count > 0 && b.Board[co.x1][count -1] == -1) {
						count --;
					}
					//get new position
					Coordinate left = new Coordinate(co.x1, count ,co.x2, count + length);
					//update position
				//	ArrayList<Car> car = (ArrayList<Car>)b.Car.clone();
					car.get(c.num).Paths.add(left);
				//	ArrayList<Integer> carID = (ArrayList<Integer>)b.carID.clone();
					carID.add(c.num);
					
					//add new board to queue
					queue.add(new Board(car, carID));					
					
				}
				
				//move right
				if(co.y2 < 6 && b.Board[co.x1][co.y2+1] == -1) {
					System.out.println("RIGHT");
					
					//move car to furtherest distance
					int count = co.y1;
					int length = co.y2 - co.y1;
					while(count < 6 -length  && b.Board[co.x1][count + length] == -1) {
						count ++;
					}
					//get new position
					Coordinate right = new Coordinate(co.x1, count ,co.x2, count + length);
					//update position
					//ArrayList<Car> car1 = (ArrayList<Car>)b.Car.clone();
					car1.get(c.num).Paths.add(right);
					//ArrayList<Integer> carID1 = (ArrayList<Integer>)b.carID.clone();
					carID1.add(c.num);
					
					//add new board to queue
					queue.add(new Board(car1, carID1));	

				}
				
			}else if(co.y1 == co.y2) {
			//move by column
				
				//move up
				if(co.x1 > 0 && b.Board[co.x1-1][co.y1] == -1) {
					System.out.println("UP");
					
					//move car to furtherest distance
					int count = co.x1;
					int length = co.x2 - co.x1;
					while(count > 0 && b.Board[count -1][co.y1] == -1) {
						count --;
					}
					//get new position
					Coordinate up = new Coordinate(count, co.y1 , count + length, co.y2);
					//update position
				//	ArrayList<Car> car = (ArrayList<Car>)b.Car.clone();
					car.get(c.num).Paths.add(up);
					//ArrayList<Integer> carID = (ArrayList<Integer>)b.carID.clone();
					carID.add(c.num);
					
					//add new board to queue
					queue.add(new Board(car, carID));					
					
				}
				
				//move down
				if(co.x1 > 0 && b.Board[co.x2 +1][co.y1] == -1) {
					System.out.println("DOWN");
					
					//move car to furtherest distance
					int count = co.x1;
					int length = co.x2 - co.x1;
					while(count < 6 - length && b.Board[count + length][co.y1] == -1) {
						count ++;
					}
					//get new position
					Coordinate down = new Coordinate(count, co.y1 , count + length, co.y2);
					//update position
			//		ArrayList<Car> car1 = (ArrayList<Car>)b.Car.clone();
					car1.get(c.num).Paths.add(down);
				//	ArrayList<Integer> carID1 = (ArrayList<Integer>)b.carID.clone();
					carID1.add(c.num);
					
					//add new board to queue
					queue.add(new Board(car1, carID1));					
					
				}
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

