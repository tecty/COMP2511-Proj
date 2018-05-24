package save;

import setting.Setting;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Manage IO between class and file system.
 */
public class SaveManager {
    /**
     * Check whether there is a duplicate file in the file system.
     * @param fileName The save's file name want to be in file system.
     * @return Whether there's duplicate file name in system.
     */
	public static boolean checkDuplicate(String fileName) {
		File file = new File("saving/"+fileName+".sav");
		return file.exists();
	}

    /**
     * Load all the save name in to an array list.
     * @return ArrayList contain all the saves' name.
     */
	protected static ArrayList<String> loadAllSaves()  {
		ArrayList<String> saveList = new ArrayList<>();
		File dir = new File("saving");
		dir.mkdir();
		if(dir.isDirectory()) {
			for(String each : dir.list()) {
				System.out.println("Find saveslot: "+each);
				saveList.add(each);
			}
		}
		return saveList;
	}

    /**
     * Get the last modify time from file system, by file name.
     * @param fileName A file name in Saving directory.
     * @return Last modify time (in String) of this file.
     */
	protected static String lastModifiedTime(String fileName) {
		File file = new File("saving/"+fileName);
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");			
		return sdf.format(file.lastModified());
	}

    /**
     * Save a save to file system.
     * @param newSave Given save which need to save in file system.
     */
	public static void save(GameSave newSave)  {
		File file = new File(newSave.getFileName());
		//always refresh the file(whether it is existing or not)
		file.delete();
		System.out.println("Saving file...");
		try {
			FileOutputStream fileOut = new FileOutputStream("saving/"+newSave.getFileName());
			BufferedOutputStream bufferedStream = new BufferedOutputStream(fileOut);
			ObjectOutputStream outputStream = new ObjectOutputStream(bufferedStream);

			outputStream.writeObject(newSave);
			outputStream.close();
			fileOut.close();

//			System.out.println("File saved.");

		} catch (IOException e) {
			System.out.println("Failed to save, I/O exception");
			e.printStackTrace();
		}
	}

    /**
     * Load a save by it's filename.
     * @param fileName The file name of this save.
     * @return Save object associate with this file name.
     */
	public static GameSave load(String fileName) {
		File file = new File("saving/"+fileName);
		GameSave saveslot = null;
		
		if (file.exists()) {
			try {
//				System.out.println("Loading file");

				FileInputStream fileIn = new FileInputStream("saving/"+fileName);
				ObjectInputStream inputStream = new ObjectInputStream(fileIn);

				saveslot = (GameSave) inputStream.readObject();
				inputStream.close();
				fileIn.close();

//				System.out.println("File loaded");

			} catch (IOException | ClassNotFoundException e) {
				System.out.println("Failed to load! " + e.getLocalizedMessage());
			}
		} else {
			System.out.println("Nothing to load.");
		}

		return saveslot;
	}

    /**
     * Repair a save if that save is broken due to a early exit.
     * @param save A save may need to be repair.
     */
    protected static void tryRepairSave(GameSave save){
        for (int i = 0; i < GameSave.NUM_OF_LEVEL; i++) {
            if (save.getLevel(i).isPuzzleLoaded()){
                // submit this level for background generation
                PuzzleCreatorThread thisThread =
                        new PuzzleCreatorThread(save.getLevel(i));
                Setting.puzzleCreator.submit(thisThread);
            }
        }
    }
}