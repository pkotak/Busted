package edu.northeastern.cs5500;

import java.io.File;
import java.util.List;

/**
 * interface for Directory functions
 * @author abhiruchi
 *
 */
public interface IDirectory {

	/** 
	 * method to put two given directories into one in the given path.
	 * @param dir1, dir2 - given directories to be merged.
	 * @param rootdir - path of the root directory
	 * @return
	 */
	public String mergeDir(String dir1, String dir2, String rootdir);
	
	/**
	 * method to get all the other directories present in the rootdirectory except the given directory.
	 * @param newdir - given directory.
	 * @param rootdir - the root directory.
	 * @return the list of other directories present in the rootdirectory
	 */
	public List<File> getOtherDirs(String newdir, String rootdir);
	
	
	/**
	 * method to delete the directory from the filesystem.
	 * @param path - the path of the Directory
	 * @return - a boolean indicating the directory was deleted.
	 */
	public boolean deleteDir(String path);
}
