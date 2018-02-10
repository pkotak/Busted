package edu.northeastern.cs5500;

/**
 * Interface for users who can be Professors, TAs, Administrator
 */
public interface User {
	
	/**
	 * logs in the User.
	 * @param username
	 * @param password
	 * @return User object
	 */
	public User login(String username, String password);
	
	/**
	 * resisters a new User.
	 * @param username
	 * @param password
	 * @return User Object
	 */
	public User register(String username, String password);
	
	/**
	 * logs out the User. 
	 */
	public void logout();

}
