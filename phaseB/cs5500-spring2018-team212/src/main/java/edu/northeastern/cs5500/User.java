package edu.northeastern.cs5500;

/**
 * Interface for users who can be Professor's,TA's, Administrator
 *
 */
public interface User {
	
	/**
	 * @param username
	 * @param password
	 * @return User object
	 */
	public User login(String username, String password);
	
	/**
	 * @param usr
	 * @return true iff the user is successfully created in
	 * 		   the system
	 */
	public boolean register(User usr);

}
