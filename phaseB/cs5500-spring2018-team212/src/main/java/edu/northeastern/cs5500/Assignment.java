package edu.northeastern.cs5500;

/**
 * Interface for facilitating the storage of Assignments.
 */
public interface Assignment {
	
	/**
	 * @return an Assignment.
	 */
	public Assignment getAssignment(int submissionID);
}