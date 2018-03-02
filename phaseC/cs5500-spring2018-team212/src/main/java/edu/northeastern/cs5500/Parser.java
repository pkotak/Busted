package edu.northeastern.cs5500;

/**
 * Interface for parsing the Assignment into an AST.
 */
public interface Parser {
	
	/**
	 * @param a
	 * @return the AST generated after parsing the Assignment.
	 */
	public abstract AST parse(Assignment a);
}
