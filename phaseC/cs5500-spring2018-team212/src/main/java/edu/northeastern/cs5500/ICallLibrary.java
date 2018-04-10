package edu.northeastern.cs5500;

import java.util.List;

/**
 * interface for making calls to the JPlag Library.
 * @author abhiruchi
 *
 */
public interface ICallLibrary {

	/**
	 * method to compare the files in given input directory for plagiarism with JPlag. 
	 * @param ipdir
	 * @param opdir
	 * @param strictness
	 * @param threshold
	 */
	public void compareFiles(String ipdir, String opdir, int strictness);

	
	/**
	 * method to get the reports for the plagiarism check run on the given directory
	 * with every other directory present in the root directory if the 
	 * plagiarism score crosses the given threshold, and given strictness.
	 * @param newdir - given directory
	 * @param rootdir - root directory
	 * @param strictness - given strictness score
	 * @param threshold - given threshold for plagiarsism score.
	 * @return the list of the reports of the plagiarism check
	 */
	public List<PlagiarismResult> getReports(String newdir, String rootdir, int strictness);
	
	/**
	 * method to get the reports for the plagiarism check run on the given two directories
	 * if the plagiarism score crosses the given threshold, and given strictness.
	 * @param dir1 - directory 1
	 * @param dir2 - directory 2
	 * @param strictness - given strictness score
	 * @param threshold - given threshold for plagiarsism score.
	 * @return the list of the reports of the plagiarism check
	 */
	public List<PlagiarismResult> getIndividualReport(String dir1, String dir2, int strictness);
}
