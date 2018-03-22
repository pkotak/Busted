package edu.northeastern.cs5500;

public class Constants {
	private Constants() {
		//Private constructor
	}
	
	public static final String LIBRARY_PATH = "jplag-2.11.9-SNAPSHOT-jar-with-dependencies.jar";
	public static final String LANGUAGE = "python3";
	public static final String OUTPUT_DIRECTORY = "Results";
	public static final String INPUT_DIRECTORY = "Python Code Sample";
	public static final int STRICTNESS_EXTREME = 2;
	public static final int STRICTNESS_MEDIUM = 5;
	public static final int STRICTNESS_EASY = 15;
	public static final String CONNECTION_STRING = "jdbc:mysql://team212.chh6n0jvv6vr.us-east-2.rds.amazonaws.com/plagiarismDetector";
	public static final String AWS_USERNAME = "admin";
	public static final String AWS_P = "password";
	public static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	public static final String FIRSTNAME = "firstname";
	public static final String LASTNAME = "lastname";
	public static final String PHONE = "phone";
}
