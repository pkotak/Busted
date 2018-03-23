package edu.northeastern.cs5500;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * class to check the metadata for the assignments
 * @author abhiruchi
 *
 */

public class MetadataAnalyser {

	private static final Logger LOGGER = Logger.getLogger(CallLibrary.class.getName());


	/**
	 * method to extract metadata
	 * @param filepath of the given file
	 * @return the metadata as string
	 */
	public String getMetaData(String filepath) {
		Path path = FileSystems.getDefault().getPath(filepath);
		StringBuilder jsonString = new StringBuilder();
		try {
			jsonString.append("Owner: " + Files.getOwner(path) + "\n").toString();
		} catch (IOException e) {
			LOGGER.log(Level.INFO, e.toString());
		}
		return jsonString.toString();
	}
	
	/**
	 * method to compare metadata
	 * @param f1path - path of first file
	 * @param f2path - path of second file
	 * @return int indicating comparison files
	 */
	public int checkifSame(String f1path, String f2path) {
		String f1md = getMetaData(f1path);
		String f2md = getMetaData(f2path);
		return f1md.compareTo(f2md);
	}
}