package puzzleModel;

import java.util.ArrayList;
import java.util.Random;

public class Generator {
    private static final Random RANDOM = new Random();

    public Board generateRandomBoard(int desiredLength, long startTime) {
        ArrayList<Car> someCars = new ArrayList<Car>();

        // first red car input ID = 0 Coordinate x1 = random,y1 = 2,x2 =random +2 ,y2=2
        int x = RANDOM.nextInt(2);
        Coordinate ori = new Coordinate(2, x, 2, (x + 1));
        ArrayList<Coordinate> oro = new ArrayList<Coordinate>();
        oro.add(ori);
        someCars.add(new Car(0, oro));

        // create a new board
        Board board = new Board(someCars, new ArrayList<Integer>());
        // gridMatrix.printB(board);

        int pathLength = 0;

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
            } else {
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

    private Car getRandomCar(int Cid) {
        boolean isHorz = RANDOM.nextBoolean();
        int id = Cid;
        int size = getRandomSize();

        // blocks start col and row
        // if horizontal, we can't have it on the prisoner row

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
        int x1 = RANDOM.nextInt(6 - size);
        int x2 = x1 + size - 1;
        int col = RANDOM.nextInt(6);
        Coordinate next = new Coordinate(x1, col, x2, col);
        ArrayList<Coordinate> nextP = new ArrayList<Coordinate>();
        nextP.add(next);
        return new Car(id, nextP);
    }

    private int getRandomSize() {
        if (RANDOM.nextInt(4) == 0)
            return 3;
        else
            return 2;
    }
}
