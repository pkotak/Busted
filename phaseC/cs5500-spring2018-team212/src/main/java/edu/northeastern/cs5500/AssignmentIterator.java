package edu.northeastern.cs5500;

/**
 * Interface for iterating over the Assignments in the Directory.
 */
public interface AssignmentIterator {
	
	/**
	 * @param a
	 */
	public void visitNext(Assignment a);

}
