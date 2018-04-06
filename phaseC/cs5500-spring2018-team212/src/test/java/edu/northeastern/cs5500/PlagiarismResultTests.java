package edu.northeastern.cs5500;

import org.junit.Test;

public class PlagiarismResultTests {

	@Test
	public void testPlagiarismResult() {
		PlagiarismResult pr = new PlagiarismResult(10, 1, 2, "s3://");
		pr.getAssignmentID1();
		pr.getAssignmentID2();
		pr.getPath();
		pr.getSimilarityScore();
		pr.toString();
	}

}
