package edu.northeastern.cs5500;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
	 * method to compare the files in given input directory for plagiarism with JPlag
	 * with every other directory, incrementally. 
	 * @param newdir
	 * @param rootdir
	 * @param strictness
	 * @return
	 */
	public static String compareDir(String newdir, String rootdir, int strictness) {
		Boolean b = true;
		CallLibrary cl = new CallLibrary();
		List<File> listOfDir = new Directory().getOtherDirs(newdir, rootdir);
		String rootopdir = rootdir + "op";
		String rootmergedir = rootdir + "merge";
		for (int i = 0; i < listOfDir.size(); i++) {
			String currdir = listOfDir.get(i).getName();
			String ipdir = new Directory().mergeDir(newdir, rootdir+PATH_DELIM+currdir, rootmergedir);
			String mdir = ipdir.replaceAll(rootmergedir, "");
			String tempopdir = rootopdir+mdir+"op";
			new File(tempopdir).mkdirs();

			cl.compareFiles(ipdir, tempopdir, strictness);	
		}

		if(new Directory().deleteDir(rootmergedir))	
			b = b & new File(rootmergedir).delete();
		return rootopdir;
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
		String ipdir = new Directory().mergeDir(dir1, dir2, "test_merge");
		String rootopdir = "test_op";
		String opdir = rootopdir + PATH_DELIM + new File(dir1).getName()+"_"+ new File(dir2).getName()+"op";
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