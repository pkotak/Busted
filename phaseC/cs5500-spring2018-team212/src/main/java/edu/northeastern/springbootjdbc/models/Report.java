package edu.northeastern.springbootjdbc.models;

/**
 * Report representation
 * @author Paarth
 *
 */
public class Report {

	private int id;
	private int assignment1ID;
	private int assignment2ID;
	private int similarityscore;
	private String downloadLink;
	private Boolean isResolved = false;

	public Report(int asst1id, int ass2id, int ss, String downloadLink, Boolean ir) {
		super();
		// This is to avoid redundant reports being generated.
		if(asst1id > ass2id) {
			this.setAssignment1ID(ass2id);
			this.setAssignment2ID(asst1id);
		}
		else {
			this.setAssignment1ID(asst1id);
			this.setAssignment2ID(ass2id);
		}
		this.setSimilarityscore(ss);
		this.setDownloadLink(downloadLink);
		this.setIsResolved(ir);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAssignment1ID() {
		return assignment1ID;
	}

	public void setAssignment1ID(int assignment1id) {
		assignment1ID = assignment1id;
	}

	public int getAssignment2ID() {
		return assignment2ID;
	}

	public void setAssignment2ID(int assignment2id) {
		assignment2ID = assignment2id;
	}

	public int getSimilarityscore() {
		return similarityscore;
	}

	public void setSimilarityscore(int similarityscore) {
		this.similarityscore = similarityscore;
	}

	public String getDownloadLink() {
		return downloadLink;
	}

	public void setDownloadLink(String downloadLink) {
		this.downloadLink = downloadLink;
	}

	public Boolean getIsResolved() {
		return isResolved;
	}

	public void setIsResolved(Boolean isResolved) {
		this.isResolved = isResolved;
	}

	public void setIsResolved(int isResolved) {
		if (isResolved == 1)
			this.isResolved = true;
		else
			this.isResolved = false;
	}

	@Override
	public String toString() {
		return "Report [id=" + id + ", assignment1ID=" + assignment1ID + ", assignment2ID=" + assignment2ID
				+ ", similarityscore=" + similarityscore + ", downloadLink=" + downloadLink + ", isResolved="
				+ isResolved + "]";
	}

}
