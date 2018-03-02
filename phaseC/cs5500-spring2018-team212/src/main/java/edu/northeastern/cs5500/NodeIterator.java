package edu.northeastern.cs5500;

/**
 * Interface for iterating over the AST nodes.
 */
public interface NodeIterator {
	
	/**
	 * @param node
	 */
	public void visitNext(AST node);

}
