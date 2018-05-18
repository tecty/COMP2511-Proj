package save;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SaveManager {
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
	
	public static Object load(String fileName) throws IOException, ClassNotFoundException{
		ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(Paths.get(fileName)));
		return ois.readObject();
	}
}