package setting;

import save.GameSave;

import java.util.concurrent.ExecutorService;

public class Setting {
    public static GameSave save;
    public static Boolean music;
    // the Executor for creating puzzles explicitly
    // TODO: create and destroy in main
    public static ExecutorService puzzleCreator;
}
