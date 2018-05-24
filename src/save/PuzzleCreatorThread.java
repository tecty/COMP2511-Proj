package save;

import puzzleModel.Algorithm;
import puzzleModel.Board;
import puzzleModel.Generator;

/**
 * Thread object for load a puzzle to a given level
 */
public class PuzzleCreatorThread implements Runnable {
    Level level;

    /**
     * Inject the tread's task by level and how many the step
     * is required to generate that level.
     * @param level Level the puzzle to put.
     */
    public PuzzleCreatorThread(Level level){
        this.level = level;
    }

    /**
     * Run the thread to load puzzle.
     */
    @Override
    public void run() {
        if (level == null){
            System.out.println("Couldn't find a puzzle for this");
            return;
        }
        // load the new puzzle and correct
        // recommend step into this save
        level.loadPuzzle();
    }


}
