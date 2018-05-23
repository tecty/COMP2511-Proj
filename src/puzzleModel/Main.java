package puzzleModel;

public class Main {

    public static void main(String[] args) {
        Generator g = new Generator();
        long startTime = System.currentTimeMillis();
        // setting desired path as argument of generator
        Board b = g.generateRandomBoard(12
                , startTime);
//		for (int i = 4; i < 18; i+=2) {
//		    System.out.println("Generating board of "+ i + " steps ");
//			b = g.generateRandomBoard(i,startTime);
//		}

        Algorithm alg = new Algorithm();
        Board solved = alg.solve(b);
        System.out.println("Solution of output board = " + (solved.carID.size() + 1));
        System.out.println("Time: " + (System.currentTimeMillis() - startTime));
        Board.printB(b);
        Board.printB(solved);
        b.deleteCarByList(solved.getUnmovedCar());
        Board.printB(b);
    }
}
