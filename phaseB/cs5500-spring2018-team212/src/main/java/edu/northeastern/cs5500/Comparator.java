package edu.northeastern.cs5500;

/**
 * Interface for comparing Assignments.
 */
public interface Comparator {
	
	/**
	 * @param a1
	 * @param a2
	 * @return the similarity score between the two Assignments.
	 */
	public int compareAssignments(Assignment a1, Assignment a2);

}
