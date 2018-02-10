package edu.northeastern.cs5500;

import java.util.List;

/**
 * Report generation interface 
 *
 */
public interface Report {
	
	/**
	 * @param submissionId
	 * @return Report
	 */
	public Report fetchReport(int submissionId);
	
	/**
	 * @param submissionId
	 * @return a plagiarism score calculated against
	 *         other submissions
	 */
	public int calculateSimilarityScore(int submissionId);
	
	/**
	 * @param submissionId
	 * @return
	 */
	public int[] fetchAllSimilarityScores(int submissionId);
	
	/**
	 * @return
	 */
	public List<Integer> getSevereFiles();
	
	/**
	 * @return
	 */
	public List<Integer> getWarningFiles();

}
