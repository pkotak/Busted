package edu.northeastern.cs5500;

/**
 * Interface for facilitating the storage of Assignments
 * that are Directories.
 */
public interface Directory extends Assignment {
	
	/**
	 * @return a int - submissionID iff the Directory has been
	 * uploaded successfully.
	 */
	public int uploadDirectory();
}