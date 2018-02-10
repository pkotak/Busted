package edu.northeastern.cs5500;

/**
 * Interface for visiting the AST node.
 */
public interface ASTNodeVisitor {
	
	/**
	 * @param node
	 * @return the AST node after visiting this Expression node
	 * in the Abstract Syntax Tree.
	 */
	public AST visit(Expression node);
	
	/**
	 * @param node
	 * @return the AST node after visiting this Statement node
	 * in the Abstract Syntax Tree.
	 */
	public AST visit(Statement node);
	
	/**
	 * @param node
	 * @return the AST node after visiting this Declaration node
	 * in the Abstract Syntax Tree.
	 */
	public AST visit(Declaration node);
	
	/**
	 * @param node
	 * @return the AST node after visiting this Variable node
	 * in the Abstract Syntax Tree.
	 */
	public AST visit(Variable node);
	
	/**
	 * @param node
	 * @return the AST node after visiting this Operator node
	 * in the Abstract Syntax Tree.
	 */
	public AST visit(Operator node);
	
}