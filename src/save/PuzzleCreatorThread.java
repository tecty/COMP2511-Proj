package save;

import puzzleModel.Algorithm;
import puzzleModel.Board;
import puzzleModel.Generator;

public class PuzzleCreatorThread implements Runnable {
    Level level;
    int requireStep;

    /**
     * Inject the tread's task by level and how many the step
     * is required to generate that level.
     * @param level Level the puzzle to put.
     * @param requireStep Required step for that puzzle
     */
    public PuzzleCreatorThread(Level level, int requireStep){
        this.level = level;
        this.requireStep = requireStep;
    }
    @Override
    public void run() {
//        System.out.println("try to generate a level");

        //the generated set of arranged cars and the corresponding recommend steps are imported in
        Generator generator = new Generator();

        // print which level it has generated
        System.out.println("Generated level: "+
                this.level.save.allLevels.indexOf(level));

        // use algorithm to generate the board
        puzzleModel.Board board =  generator.generateRandomBoard(requireStep);
        // solve the puzzle to get a correct recommended step
        Algorithm alg = new Algorithm();
        Board solved = alg.solve(board);

        // load the new puzzle and correct
        // recommend step into this save
        level.loadPuzzle(
                board.toCarList(),
                solved.carID.size() + 1);
        // finish generation
        return;
    }


}
