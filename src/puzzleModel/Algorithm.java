package puzzleModel;

/**
 * Algorithm contains a solve function that implements Dijkstra's algorithm
 * @author Huiyue Zhang
 *
 */
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Algorithm {
	/**
	 * function that get a Board, and tries to solve the puzzle
	 * returns Board if puzzle has solution, otherwise returns null 
	 * 
	 * Theory:
	 * while(queue not empty){
	 * 		b = pop queue
	 * 		if (visited contains b) continue
	 * 		
	 * 		add b to visited
	 * 		if (car0 in  b can move to (2,4) (2,5) )
	 * 			return b
	 * 		
	 * 		addPossibleBoardsToQueue(queue, b)
	 * }
	 * @param board
	 * @return Board
	 */
    public Board solve(Board board) {

        Set<Board> visited = new HashSet<Board>();
        ArrayList<Board> queue = new ArrayList<Board>();

        queue.add(board);
        while (!queue.isEmpty()) {
            // gridMatrix b = queue.poll();
            Board b = queue.remove(0);

            //check if state of b is already seen
            if (visited.contains(b)) {
                continue;
            }
            //add b to visited
            visited.add(b);

            // check final state
            if (unlockCar(b)) {
                return b;
            }
            //add possile boards to queue
            addPossibleBoardsToQueue(queue, b);
        }
        // if the given board has no solution, return null
        return null;
    }
    
    
   
    /**
     * function to add possible movements of every car to the queue
     * 
     * Theory:
     * for(each car in carList){
     *		if (car moves by row) {
     *			if(car can  move left){
     *				move car to as far left as it can
     *				add its position to car.Paths
     *				add its ID to the list carID
     *				create new Board with car and carID
     *				add the new Board to queue
     *			}
     *			if(car can  move right){
     *				move car to as far right as it can
     *				add its position to car1.Paths
     *				add its ID to the list carID1
     *				create new Board with car1 and carID1
     *				add the new Board to queue
     *			}
     *		}else if (car moves by column) {
     *			if(car can  move up){
     *				move car to as far up as it can
     *				add its position to car.Paths
     *				add its ID to the list carID
     *				create new Board with car and carID
     *				add the new Board to queue
     *			}
     *			if(car can  move down){
     *				move car to as far down as it can
     *				add its position to car1.Paths
     *				add its ID to the list carID1
     *				create new Board with car1 and carID1
     *				add the new Board to queue
     *			}
     *		}
     * }
     * 
     * @param queue
     * @param b
     */
    private void addPossibleBoardsToQueue(ArrayList<Board> queue, Board b) {

        // add all next possible boards to queue
        for (int i = 0; i < b.carList.size(); i++) {
            Car c = b.carList.get(i);
            Coordinate co = c.Paths.get(c.Paths.size() - 1);
            ArrayList<Car> car = b.copyCarList();
            ArrayList<Integer> carID = new ArrayList<Integer>(b.carID);
            ArrayList<Car> car1 = b.copyCarList();
            ArrayList<Integer> carID1 = new ArrayList<Integer>(b.carID);

            // move by row
            if (co.x1 == co.x2) {

                // move left
                if (co.y1 > 0 && b.gridMatrix[co.x1][co.y1 - 1] == -1) {

                    // move car to furtherest distance
                    int count = co.y1;
                    int length = co.y2 - co.y1;

                    while (count > 0 && b.gridMatrix[co.x1][count - 1] == -1) {
                        count--;
                    }

                    // get new position
                    Coordinate left = new Coordinate(co.x1, count, co.x2, count + length);

                    // update position
                    car.get(c.carID).Paths.add(left);
                    carID.add(c.carID);

                    // add new board to queue
                    queue.add(new Board(car, carID));
                }

                // move right
                if (co.y2 < 6 - 1 && b.gridMatrix[co.x1][co.y2 + 1] == -1) {
                    // move car to furthest distance
                    int count = co.y2;
                    int length = co.y2 - co.y1;

                    while (count < 6 - 1 && b.gridMatrix[co.x1][count + 1] == -1) {
                        count++;
                    }

                    // get new position
                    Coordinate right = new Coordinate(co.x1, count - length, co.x2, count);

                    // update position
                    car1.get(c.carID).Paths.add(right);
                    carID1.add(c.carID);

                    // add new board to queue
                    queue.add(new Board(car1, carID1));

                    // break;

                }

            // move by column
            } else if (co.y1 == co.y2) {

                // move up
                if (co.x1 > 0 && b.gridMatrix[co.x1 - 1][co.y1] == -1) {

                    // move car to furthest distance
                    int count = co.x1;
                    int length = co.x2 - co.x1;
                    while (count > 0 && b.gridMatrix[count - 1][co.y1] == -1) {
                        count--;
                    }

                    // get new position
                    Coordinate up = new Coordinate(count, co.y1, count + length, co.y2);

                    // update position
                    car.get(c.carID).Paths.add(up);
                    carID.add(c.carID);

                    //add new board to queue
                    queue.add(new Board(car, carID));
                }

                // move down
                if (co.x2 < 6 - 1 && b.gridMatrix[co.x2 + 1][co.y1] == -1) {

                    // move car to furthest distance
                    int count = co.x2;
                    int length = co.x2 - co.x1;
                    int j = co.x2;
                    while (count < 6 - 1 && b.gridMatrix[count + 1][co.y1] == -1) {
                        count++;
                    }

                    // get new position
                    Coordinate down = new Coordinate(count - length, co.y1, count, co.y2);

                    // update position
                    car1.get(c.carID).Paths.add(down);
                    carID1.add(c.carID);

                    // add new board to queue
                    queue.add(new Board(car1, carID1));
                }
            }
        }
    }

    
    /**
     * function that checks if car0 can move to (2,4) (2,5)
     * @param b
     * @return boolean
     */
    private boolean unlockCar(Board b) {
        // get coordinates of Car0
        Car c = b.carList.get(0);
        Coordinate co = c.Paths.get(c.Paths.size() - 1);

        // if the left of Car0 is car free, the game is cleared
        for (int i = co.y2; i < 6; i++) {
            if (b.gridMatrix[co.x1][i] != -1 && b.gridMatrix[co.x1][i] != c.carID) {
                return false;
            }
        }
        return true;
    }
    
    
    /**
     * for debug
     * function to print every board in the queue
     * @param queue
     */
    private void printQueue(ArrayList<Board> queue) {
        System.out.println();
        for (Board b : queue) {
            b.printB(b);
            System.out.println();
        }
    }
    
}
