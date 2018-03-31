package edu.northeastern.cs5500;

import java.util.Properties;

import javax.mail.Session;

public interface ISendMailSSL {

	/**
	 * Method to send the email
	 * @param password 
	 * @param to recipient
	 * @param sub subject of the message
	 * @param msg message to be send
	 */
	void send(String password, String to, String sub, String msg, String filepath);
	
	/**
	 * Method to get properties from a configuration file
	 * @return properties having the key value pairs
	 */
	Properties getProperties(String filepath);
	
	
	/**
	 * @param props the properties needed to initial the session
	 * @return the session
	 */
	Session getSession(Properties props);
	
	/**
	 * Method to compose the message
	 * @param session
	 * @param to recipient
	 * @param sub subject
	 * @param msg message content
	 */
	void composeMessage(Session session, String to, String sub, String msg);
}