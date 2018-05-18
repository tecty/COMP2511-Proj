package save;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SaveManager {
	//check the latest saving 
	
	public static void save(GameSave newSave, String fileName) throws IOException {
		File file = new File(fileName);
		//always refresh the file(whether it is existing or not)
		file.delete();
		System.out.println("Saving file...");
		try {
			FileOutputStream fileOut = new FileOutputStream(fileName);
			BufferedOutputStream bufferedStream = new BufferedOutputStream(fileOut);
			ObjectOutputStream outputStream = new ObjectOutputStream(bufferedStream);

			outputStream.writeObject(newSave);
			outputStream.close();
			fileOut.close();

			System.out.println("File saved.");

		} catch (IOException e) {
			System.out.println("Failed to save, I/O exception");
			e.printStackTrace();
		}
	}
	
	
	public static GameSave load(String fileName) throws IOException, ClassNotFoundException{
		File file = new File(fileName);
		GameSave saveslot = null;
		
		if (file.exists()) {
			try {
				System.out.println("Loading file");

				FileInputStream fileIn = new FileInputStream(fileName);
				ObjectInputStream inputStream = new ObjectInputStream(fileIn);

				saveslot = (GameSave) inputStream.readObject();
				inputStream.close();
				fileIn.close();

				System.out.println("File loaded");

			} catch (IOException | ClassNotFoundException e) {
				System.out.println("Failed to load! " + e.getLocalizedMessage());
			}
		} else {
			System.out.println("Nothing to load.");
		}
		return saveslot;
	}
}