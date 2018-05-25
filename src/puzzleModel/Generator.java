package puzzleModel;

import java.util.ArrayList;
import java.util.Random;

/**
 * Board Generator class generate the game initial boards
 */
public class Generator {
    private static final Random RANDOM = new Random();

    /**
     * main function for generate a board
     * @param desiredLength the solution length of output board and it needs to have greater than or equal solution path compare to desired path
     * @param startTime the use of algorithm time it should not be too long
     * @return a board with initial state and car list
     */
    public Board generateRandomBoard(int desiredLength, long startTime) {
        ArrayList<Car> someCars = new ArrayList<Car>();

        // first red car input ID = 0 Coordinate x1 = random,y1 = 2,x2 =random +2 ,y2=2
        int x = RANDOM.nextInt(2);
        Coordinate ori = new Coordinate(2, x, 2, (x + 1));
        ArrayList<Coordinate> oro = new ArrayList<Coordinate>();
        oro.add(ori);
        someCars.add(new Car(0, oro));

        // create a new board with the first red car
        Board board = new Board(someCars, new ArrayList<Integer>());
        // gridMatrix.printB(board);

        //path length is the solution length of current board
        int pathLength = 0;
        
        /** Main loop of this generator algorithm 
         * while(the solution length of current board < desired length){
         *      if the board is generator for a long time restart it
         *      if the board has no solution restart it
         *      if the board is full of car but the path length is not enough restart
         *      if the board is nearly full and the gaps between desired length and path length is too large restart it
         *      if the board still have enough space to place car 
         *      	random a car and check if it can fit the board place it else rerandom it
         *          if the car can fit the board however it has no solution after place it delete this car 
         * }
         */
        while (pathLength < desiredLength) {

            if (System.currentTimeMillis() - startTime > 10000) {
                return generateRandomBoard(desiredLength, System.currentTimeMillis());
            }

            Algorithm alg = new Algorithm();
            Board solved = alg.solve(board);

            if (solved == null && pathLength < desiredLength) {
                return generateRandomBoard(desiredLength, startTime);
            }

            pathLength = solved.carID.size() + 1;
//	        System.out.println("solution length = " + pathLength);

            if (board.carList.size() >= 13) {
                return generateRandomBoard(desiredLength, startTime);
            }

            if (board.carList.size() >= 10 && (desiredLength - pathLength) >= 5) {
                return generateRandomBoard(desiredLength, startTime);
            }

            if (board.carList.size() < 13) {
                Car rand = getRandomCar(board.carList.size());
                int[][] gameboard = board.gridMatrix;
                if (isFree(gameboard, rand)) {
                    board.carList.add(rand);
                    board.updateBoard();
                    Algorithm alg1 = new Algorithm();
                    Board solved1 = alg1.solve(board);
                    if (solved1 == null || (solved1 != null && (solved1.carList.size() + 1) <= pathLength)) {
                        if (rand.Paths.get(0).x1 == rand.Paths.get(0).x2) {
                            int k = rand.Paths.get(0).y1;
                            while (k <= rand.Paths.get(0).y2) {
                                board.gridMatrix[rand.Paths.get(0).x1][k] = -1;
                                k++;
                            }
                        } else {
                            int k = rand.Paths.get(0).x1;
                            while (k <= rand.Paths.get(0).x2) {
                                board.gridMatrix[k][rand.Paths.get(0).y1] = -1;
                                k++;
                            }
                        }
                        board.carList.remove(board.carList.size() - 1);
                    }
                }
            }else {
            	return generateRandomBoard(desiredLength, startTime);
            } 
        }
        return board;
    }

    public Board generateRandomBoard(int desiredLength){
        // a redirect for simple calling
        return generateRandomBoard(desiredLength,
                System.currentTimeMillis()
        );
    }
    
    /**
     * isFree function check the if is free to add this car to this board
     * @param gameboard board that going to place a new car
     * @param b car that needs to be placed
     * @return boolean value, true for free to add false is car clash
     */
    private boolean isFree(int[][] gameboard, Car b) {
        if (b.Paths.get(0).x1 == b.Paths.get(0).x2) {
            for (int i = b.Paths.get(0).y1; i <= b.Paths.get(0).y2; i++) {
                if (gameboard[b.Paths.get(0).x1][i] != -1) {
                    return false;
                }
            }
        } else {
            for (int i = b.Paths.get(0).x1; i <= b.Paths.get(0).x2; i++) {
                if (gameboard[i][b.Paths.get(0).y1] != -1) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * getRandomCar function for random a car
     * @param Cid the car id of this random car
     * @return a car that has random coordinate on the board
     */
    private Car getRandomCar(int Cid) {
        boolean isHorz = RANDOM.nextBoolean();
        int id = Cid;
        int size = getRandomSize();

        // blocks start col and row
        // if horizontal, we can't have it on the 2 row which has red car on it
        if (isHorz) {
            int y1 = RANDOM.nextInt(6 - size);
            int y2 = y1 + size - 1;
            int row = RANDOM.nextInt(6);
            if (row == 2) {
                row++;
            }
            Coordinate next = new Coordinate(row, y1, row, y2);
            ArrayList<Coordinate> nextP = new ArrayList<Coordinate>();
            nextP.add(next);
            return new Car(id, nextP);
        }
        //otherwise is a vertical car it can have any coordinate that it wants
        int x1 = RANDOM.nextInt(6 - size);
        int x2 = x1 + size - 1;
        int col = RANDOM.nextInt(6);
        Coordinate next = new Coordinate(x1, col, x2, col);
        ArrayList<Coordinate> nextP = new ArrayList<Coordinate>();
        nextP.add(next);
        return new Car(id, nextP);
    }
    /**
     * getRandomSize function random the size of car whether is 2 or 3
     * @return the size of car 2 or 3
     */
    private int getRandomSize() {
        if (RANDOM.nextInt(4) == 0)
            return 3;
        else
            return 2;
    }
}
