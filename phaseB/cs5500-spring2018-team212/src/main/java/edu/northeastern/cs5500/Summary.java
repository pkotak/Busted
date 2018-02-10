package edu.northeastern.cs5500;

/**
 * Interface for generating a summary of the Reports.
 */
public interface Summary {
	
	/**
	 * @param user
	 * E-mail the plagiarism statistics to a User
	 */
	public abstract void emailSummary(User user);

}
