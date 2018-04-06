package edu.northeastern.cs5500;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.zeroturnaround.zip.commons.FileUtils;

/**
 * class to run the JPlag library.
 * @author abhiruchi
 *
 */
public class CallLibrary implements ICallLibrary{

	private static final Logger LOGGER = Logger.getLogger(CallLibrary.class.getName());
	private static final String PATH_DELIM = "/";


	/* (non-Javadoc)
	 * @see edu.northeastern.cs5500.ICallLibrary#compareFiles(java.lang.String, java.lang.String, int, int)
	 */
	@Override
	public void compareFiles(String ipdir, String opdir, int strictness) {
		String jarCommand = "java -jar ";
		String cmd = "";
		cmd = jarCommand + Constants.LIBRARY_PATH +
				" -l " + Constants.LANGUAGE + 
				" -r " + opdir + 
				" -t " + strictness +
				" -s " + ipdir;
		try {
			Runtime.getRuntime().exec(cmd);
		} catch (IOException e) {
			LOGGER.log(Level.INFO, e.toString());
		}
	}

	/** 
	 * method to put two given directories into one in the given path.
	 * @param dir1, dir2 - given directories to be merged.
	 * @param rootdir - path of the root directory
	 * @return
	 */
	public static String mergeDir(String dir1, String dir2, String rootdir) {
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

	/**
	 * method to get all the other directories present in the rootdirectory except the given directory.
	 * @param newdir - given directory.
	 * @param rootdir - the root directory.
	 * @return the list of other directories present in the rootdirectory
	 */
	public static List<File> getOtherDirs(String newdir, String rootdir) {
		File folder = new File(rootdir);
		String cdirname = newdir.replace(rootdir, "");
		cdirname = cdirname.replace("/", "");
		List<File> fileList = new ArrayList<File>();
		File[] listOfFiles = folder.listFiles();
		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isDirectory()) {
				File x = listOfFiles[i];
				String xname = x.getName();
				if (!xname.equalsIgnoreCase(cdirname))
					fileList.add(x);
			}
		}
		return fileList;
	}

	/**
	 * method to compare the files in given input directory for plagiarism with JPlag
	 * with every other directory, incrementally. 
	 * @param newdir
	 * @param rootdir
	 * @param strictness
	 * @return
	 */
	public static String compareDir(String newdir, String rootdir, int strictness) {
		CallLibrary cl = new CallLibrary();
		List<File> listOfDir = getOtherDirs(newdir, rootdir);
		Boolean b = true;
		String rootopdir = rootdir + "op";
		String rootmergedir = rootdir + "merge";
		for (int i = 0; i < listOfDir.size(); i++) {
			String currdir = listOfDir.get(i).getName();
			String ipdir = mergeDir(newdir, rootdir+PATH_DELIM+currdir, rootmergedir);
			String mdir = ipdir.replaceAll(rootmergedir, "");
			String tempopdir = rootopdir+mdir+"op";
			new File(tempopdir).mkdirs();

			cl.compareFiles(ipdir, tempopdir, strictness);	
		}
		if(cl.deleteDir(rootmergedir))
			b = new File(rootmergedir).delete();
		return rootopdir;
	}

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

	/* (non-Javadoc)
	 * @see edu.northeastern.cs5500.ICallLibrary#getReports(java.lang.String, java.lang.String, int, int)
	 */
	@Override
	public List<PlagiarismResult> getReports(String newdir, String rootdir, int strictness){
		try{
			String resdir = compareDir(newdir, rootdir, strictness);
			Thread.sleep(10000); 
			HtmlParser parser = new HtmlParser();
			return parser.getSimilarityScore(resdir);
		}
		catch(Exception e){  
			LOGGER.info(e.toString());
		}
		return new ArrayList<PlagiarismResult>();
	}

	/**
	 * method to compare the files in given two directories for plagiarism with JPlag
	 * @param newdir
	 * @param rootdir
	 * @param strictness
	 * @return
	 */
	public static String compareTwoFiles(String dir1, String dir2, int strictness){
		CallLibrary cl = new CallLibrary();
		String ipdir = mergeDir(dir1, dir2, "test_merge");
		String rootopdir = "test_op";
		String opdir = rootopdir + PATH_DELIM + new File(dir1).getName() + new File(dir2).getName();
		new File(opdir).mkdirs();
		cl.compareFiles(ipdir, opdir, strictness);
		return rootopdir;

	}

	/* (non-Javadoc)
	 * @see edu.northeastern.cs5500.ICallLibrary#getIndividualReport(java.lang.String, java.lang.String, int, int)
	 */
	@Override
	public List<PlagiarismResult> getIndividualReport(String dir1, String dir2, int strictness){	
		try{
			String rootopdir = compareTwoFiles(dir1, dir2, strictness);
			Thread.sleep(10000); 
			HtmlParser parser = new HtmlParser();
			return parser.getSimilarityScore(rootopdir);

		}
		catch(Exception e){  
			LOGGER.info(e.toString());
		}
		return new ArrayList<PlagiarismResult>();
	}


}