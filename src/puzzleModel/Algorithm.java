package puzzleModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Algorithm {

    public Board solve(Board board) {

        Set<Board> visited = new HashSet<Board>();
        ArrayList<Board> queue = new ArrayList<Board>();

        queue.add(board);
        while (!queue.isEmpty()) {
            // gridMatrix b = queue.poll();
            Board b = queue.remove(0);

            if (b.carID.size() > 17) {
//				System.out.println("SIZE " + b.carID.size());
                return null;
            }

            if (visited.contains(b)) {
                continue;
            }

            visited.add(b);

            // check final state
            if (unlockCar(b)) {
                return b;
            }

            addPossibleBoardsToQueue(queue, b);
        }

        // return board;
        return null;
    }

    private boolean listContainBoard(ArrayList<Board> list, Board b) {
        if (list.size() == 0) return false;

        boolean t = false;

        // for each board
        for (int j = 0; j < list.size(); j++) {

            // get curr board
            Board curr = list.get(j);

            // for each car in curr board
            for (int k = 0; k < curr.carList.size(); k++) {

                // get curr car, and b.carList
                Car car = curr.carList.get(k);
                Car c = b.carList.get(k);

                // get last coordinate of corresponding car
                Coordinate co = car.Paths.get(car.Paths.size() - 1);
                Coordinate oco = c.Paths.get(c.Paths.size() - 1);

                if (co.x1 == oco.x1 && co.x2 == oco.x2 && co.y1 == oco.y1 && co.y2 == oco.y2) {
                    continue;
                }
                if (k == curr.carList.size())
                    return true;
                break;
            }
        }
        return false;
    }

    private void printQueue(ArrayList<Board> queue) {
        System.out.println();
        for (Board b : queue) {
            b.printB(b);
            System.out.println();
        }
    }

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

                    // move car to furthest distance
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

    // check final state
    public boolean unlockCar(Board b) {

        // get coordinates of Car0
        Car c = b.carList.get(0);
        Coordinate co = c.Paths.get(c.Paths.size() - 1);

        // if the right of Car0 is empty, the game is cleared
        for (int i = co.y2; i < 6; i++) {
            if (b.gridMatrix[co.x1][i] != -1 && b.gridMatrix[co.x1][i] != c.carID) {
                return false;
            }
        }
        return true;
    }
}
