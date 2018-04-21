package edu.northeastern.cs5500;

import static org.junit.Assert.*;

import org.junit.Test;

public class ConstantsTests {

	@Test
	public void test() {
		assertEquals("jplag-2.11.9-SNAPSHOT-jar-with-dependencies.jar", Constants.LIBRARY_PATH);
		assertEquals("python3", Constants.LANGUAGE);
		assertEquals("Results", Constants.OUTPUT_DIRECTORY);
		assertEquals("Python Code Sample", Constants.INPUT_DIRECTORY);
		assertEquals(2, Constants.STRICTNESS_EXTREME);
		assertEquals(5, Constants.STRICTNESS_MEDIUM);
		assertEquals(15, Constants.STRICTNESS_EASY);
		assertEquals("jdbc:mysql://team212.chh6n0jvv6vr.us-east-2.rds.amazonaws.com/plagiarismDetector", Constants.CONNECTION_STRING);
		assertEquals("admin", Constants.AWS_USERNAME);
		assertEquals("password", Constants.AWS_P);
		assertEquals("com.mysql.jdbc.Driver", Constants.JDBC_DRIVER);
		assertEquals("firstname", Constants.FIRSTNAME);
		assertEquals("lastname", Constants.LASTNAME);
		assertEquals("phone", Constants.PHONE);
		assertEquals("email", Constants.EMAIL);
		assertEquals("isApproved", Constants.APPROVED);
		assertEquals("AKIAJB6XAQ7PJSDTGGLQ", Constants.AWS_ACCESS_KEY);
		assertEquals("nXF4+vACg8mFgVk3E37zkplEaaPUlYrXCH4weig4", Constants.AWS_SECRET_KEY);
		assertEquals("us-east-2", Constants.S3_REGION);
		
	}

}
