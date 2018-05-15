import java.util.*;


public class Algorithm {

	public Board algorithm(Board board) {
		
		Set<Board> visited = new HashSet<Board>();
		
		Queue<Board> queue = new LinkedList<Board>();
		queue.add( board);
		
		
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
			
			
			
		}
		
		
		//return board;
		return null;
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

