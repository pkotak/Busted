package edu.northeastern.cs5500;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MetadataAnalyser {

	private static final Logger LOGGER = Logger.getLogger(CallLibrary.class.getName());


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
	
	public int checkifSame(String f1path, String f2path) {
		String f1md = getMetaData(f1path);
		String f2md = getMetaData(f2path);
		return f1md.compareTo(f2md);
	}
}