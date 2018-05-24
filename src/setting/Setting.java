package setting;

import save.GameSave;

import java.util.concurrent.ExecutorService;

/**
 * A global class for record the global variables.
 */
public class Setting {
    public static GameSave save;
    public static Boolean music;
    // the Executor for creating puzzles explicitly
    public static ExecutorService puzzleCreator;
}
