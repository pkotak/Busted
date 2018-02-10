package edu.northeastern.cs5500;

/**
 * Interface for users who can be Professors, TAs, Administrator
 */
public interface User {
	
	/**
	 * @param username
	 * @param password
	 * @return User object
	 */
	public User login(String username, String password);
	
	/**
	 * @param user
	 * @return true iff the user is successfully created in
	 * the system
	 */
	public boolean register(User user);
	
	/**
	 * @param user
	 * @return true iff the user is successfully removed from
	 * the system
	 */
	public boolean deleteUser(User user);

}
