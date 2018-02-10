package edu.northeastern.cs5500;

/**
 * abstract class for visiting Assignment
 */
public abstract class ASTAssignmentVisitor {

	/**
	 * @param f
	 */
	public abstract void visit(File f);
	
	/**
	 * @param d
	 */
	public abstract void visit(Directory d);
}
