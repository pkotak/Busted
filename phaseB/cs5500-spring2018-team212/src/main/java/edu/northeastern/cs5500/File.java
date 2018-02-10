package edu.northeastern.cs5500;

/**
 * Interface for facilitating the storage of Assignments
 * that are Files.
 */
public interface File extends Assignment {
	
	/**
	 * @return a int - submissionID iff the File has been
	 * uploaded successfully.
	 */
	public int uploadFile();
}
