package edu.northeastern.cs5500;

/**
 * Interface for visiting the Assignments.
 */
public interface AssignmentVisitor {
	
	/**
	 * Method for visiting the File interface.
	 * @param f
	 */
	public void visit(File f);
	
	/**
	 * Method for visiting the Directory interface.
	 * @param d
	 */
	public void visit(Directory d);

}