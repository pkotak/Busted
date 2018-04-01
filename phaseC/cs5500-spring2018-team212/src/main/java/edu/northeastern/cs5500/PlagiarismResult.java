package edu.northeastern.cs5500;

public class PlagiarismResult {
	private int similarityScore;
	private int assignmentID1;
	private int assignmentID2;
	private String path;
	
	public PlagiarismResult(int similarityScore, int aid1, int aid2, String path) {
		this.setSimilarityScore(similarityScore);
		this.setPath(path);
		this.setAssignmentID1(aid1);
		this.setAssignmentID2(aid2);
	}

	public int getSimilarityScore() {
		return similarityScore;
	}
	public void setSimilarityScore(int similarityScore) {
		this.similarityScore = similarityScore;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public int getAssignmentID2() {
		return assignmentID2;
	}

	public void setAssignmentID2(int assignmentID2) {
		this.assignmentID2 = assignmentID2;
	}

	public int getAssignmentID1() {
		return assignmentID1;
	}

	public void setAssignmentID1(int assignmentID1) {
		this.assignmentID1 = assignmentID1;
	}

	@Override
	public String toString() {
		return "PlagiarismResult [similarityScore=" + similarityScore + ", assignmentID1=" + assignmentID1
				+ ", assignmentID2=" + assignmentID2 + ", path=" + path + "]";
	}
}
