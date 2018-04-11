package edu.northeastern.cs5500;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.zeroturnaround.zip.commons.FileUtils;

/**
 * implementation of IDirectory interface.
 * @author abhiruchi
 *
 */
public class Directory implements IDirectory {

	private static final Logger LOGGER = Logger.getLogger(CallLibrary.class.getName());
	private static final String PATH_DELIM = "/";

	/* (non-Javadoc)
	 * @see edu.northeastern.cs5500.IDirectory#mergeDir(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public String mergeDir(String dir1, String dir2, String rootdir) {
		File srcDir1 = new File(dir1);
		File srcDir2 = new File(dir2);

		String destination = rootdir + PATH_DELIM + srcDir1.getName() + srcDir2.getName() ;
		File destDir1 = new File(destination + PATH_DELIM + srcDir1.getName());
		File destDir2 = new File(destination + PATH_DELIM + srcDir2.getName());

		try {
			FileUtils.copyDirectory(srcDir1, destDir1);
			FileUtils.copyDirectory(srcDir2, destDir2);
		} catch (IOException e) {
			LOGGER.info(e.toString());
		} 
		return destination;
	}

	/* (non-Javadoc)
	 * @see edu.northeastern.cs5500.IDirectory#getOtherDirs(java.lang.String, java.lang.String)
	 */
	@Override
	public List<File> getOtherDirs(String newdir, String rootdir) {
		File folder = new File(rootdir);
		String currdir = new File(newdir).getName();
		List<File> fileList = new ArrayList<File>();
		File[] listOfFiles = folder.listFiles();
		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isDirectory()) {
				File x = listOfFiles[i];
				String xname = x.getName();
				if (!xname.equalsIgnoreCase(currdir))
					fileList.add(x);
			}
		}
		return fileList;
	}

	/* (non-Javadoc)
	 * @see edu.northeastern.cs5500.IDirectory#deleteDir(java.lang.String)
	 */
	@Override
	public boolean deleteDir(String path) {
		Boolean b = true;	
		File dir = new File(path);	
		File[] entries = dir.listFiles();	
		for(File f: entries){	
			if(f.isDirectory()) {	
				deleteDir(f.getPath());	
			}	
			b = b && f.delete();	
		}	
		return b;
	}

}
