package edu.northeastern.cs5500;

import org.junit.Test;

public class S3Tests {

	@Test
	public void testS3Upload() {
		S3.putObject("plagiarismDetector", "pom.xml", "pom.xml", false);
		S3.putObject("plagiarismDetector", "pom.xml", "pom.xml", true);
	}
}
