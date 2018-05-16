import java.util.*;


public class Algorithm {

	public Board Algorithm(Board board) {
		
		ArrayList<Board> visited = new ArrayList<Board>();
		
		ArrayList<Board> queue = new ArrayList<Board>();
		queue.add( board);
		
//		System.out.println("\n\nQueue before while");
//		printQueue(queue);
//		
//		System.out.println("\n\tLOOP\n");
		int cccc =  0;
		while(!queue.isEmpty()) {
			//Board b = queue.poll();
			Board b = queue.remove(0);
			
			//System.out.println("SIZE " + b.carID.size());
//			if(b.carID.size() > 17) {
//				System.out.println("SIZE " + b.carID.size());
//				return null;
//			}
			
			if(listContainBoard(visited, b)) {
				//System.out.println("VISITED EXIST");
				continue;
			}
			
			
//			if(visited.contains(b)) {
//				continue;
//			}
			
			visited.add( b );
			
			//check final state
			if(unlockCar(b)) {
				//System.out.println("GAME SOLVED");
				return b;
			}
			
			addPossibleBoardsToQueue(queue, b);
			
			
//			cccc ++;
//			System.out.println("cccc = "+ cccc);
//			if(cccc == 2) {
//				System.out.println("PRINTING QUEUE");
//				printQueue(queue);
//				
//				//break;
//			}
			
//			System.out.println("PRINTING QUEUE");
//			printQueue(queue);
			
			//break;
		}
		
		
		//return board;
		return null;
	}
	
	
	
	private boolean listContainBoard(ArrayList<Board> list, Board b) {
		if(list.size() == 0) return false;
		
		boolean t = false;
		//for each board
		for(int j = 0; j < list.size(); j ++) {
			//get cur board
			Board curr = list.get(j);
			
			//for each car in cur board
			int k = 0;
			for(; k < curr.Car.size(); k ++) {
				//get cur car, and b.Car
				Car car = curr.Car.get(k);
				Car c = b.Car.get(k);
				//get last coordinate of corresponding car
				Coordinate co = car.Paths.get( car.Paths.size()-1);
				Coordinate oco = c.Paths.get( c.Paths.size()-1);
				
				if(co.x1 == oco.x1 && co.x2 == oco.x2  && co.y1 == oco.y1 && co.y2 == oco.y2 ) {
					continue;
				}
				
				break;
				
				
			}
			if(k == curr.Car.size() ) return true;
			
		}
		
		return false;
	}
	
	
	
	
	
	
	
	
	private void printQueue(ArrayList<Board> queue) {
		System.out.println();
		for(Board b: queue) {
			b.printB(b);
			System.out.println();
		}
		
	}
	
	
	private void addPossibleBoardsToQueue(ArrayList<Board> queue, Board b) {
		//add all next possible boards to queue
		for(int i = 0; i < b.Car.size(); i ++) {
				//System.out.println("\n i = " + i);
			Car c = b.Car.get(i);
			Coordinate co = c.Paths.get( c.Paths.size()-1 );
			
//			System.out.println("PRINTING B");
//			b.printB(b);
//			System.out.println();
			
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
//			ArrayList<Car> car1 = (ArrayList<Car>)b.Car.clone();
//			ArrayList<Integer> carID = (ArrayList<Integer>)b.carID.clone();
//			ArrayList<Integer> carID1 = (ArrayList<Integer>)b.carID.clone();
//			
			ArrayList<Car> car = b.copyCarList();
			ArrayList<Integer> carID = new ArrayList<Integer>(b.carID);
			ArrayList<Car> car1 = b.copyCarList();
			ArrayList<Integer> carID1 = new ArrayList<Integer>(b.carID);
			
			//move by row
			if(co.x1 == co.x2) {
				
				//move left
				if(co.y1 > 0 && b.Board[co.x1][co.y1-1] == -1) {
					//System.out.println("LEFT");

					//move car to furtherest distance
					int count = co.y1;
					int length = co.y2 - co.y1;
					//System.out.println(length);
					
					while(count > 0 && b.Board[co.x1][count -1] == -1) {
						count --;
					}
					//System.out.println(count);
					//get new position
					Coordinate left = new Coordinate(co.x1, count ,co.x2, count + length);
			//System.out.println(co.x1 +" "+ count +" - "+  co.x2 +" "+ (count + length));
					//update position
				//	ArrayList<Car> car = (ArrayList<Car>)b.Car.clone();
					car.get(c.num).Paths.add(left);
				//	ArrayList<Integer> carID = (ArrayList<Integer>)b.carID.clone();
					carID.add(c.num);
					
					//add new board to queue
					queue.add(new Board(car, carID));					
					
				}
				//System.out.println(co.y2);
				
				//move right
				if(co.y2 < 6-1 && b.Board[co.x1][co.y2+1] == -1) {
					//System.out.println("RIGHT");
					
					//move car to furtherest distance
					int count = co.y2;
					int length = co.y2 - co.y1;
					
					//System.out.println("count = "+count);
		
					while(count < 6-1 && b.Board[co.x1][count +1] == -1) {
						count ++;
	
					}
					
					//get new position
					Coordinate right = new Coordinate(co.x1, count - length ,co.x2, count);
					//update position
					//ArrayList<Car> car1 = (ArrayList<Car>)b.Car.clone();
					car1.get(c.num).Paths.add(right);
					//ArrayList<Integer> carID1 = (ArrayList<Integer>)b.carID.clone();
					carID1.add(c.num);
					
					//add new board to queue
					queue.add(new Board(car1, carID1));	
					
					//break;

				}
				
			}else if(co.y1 == co.y2) {
			//move by column
				
				//move up
				if(co.x1 > 0 && b.Board[co.x1-1][co.y1] == -1) {
					//System.out.println("UP");
					
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
				if(co.x2 < 6-1  && b.Board[co.x2 +1][co.y1] == -1) {
					//System.out.println("DOWN");
					
					//move car to furtherest distance
					int count = co.x2;
					int length = co.x2 - co.x1;
					//System.out.println(length);
					int j = co.x2;
					while(count < 6-1 && b.Board[count + 1][co.y1] == -1) {
						count ++;
					}
					//System.out.println(count);
					
					//get new position
					Coordinate down = new Coordinate(count -length, co.y1 , count, co.y2);
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

