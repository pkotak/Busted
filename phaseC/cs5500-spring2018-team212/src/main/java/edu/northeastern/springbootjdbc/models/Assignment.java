package edu.northeastern.springbootjdbc.models;

import java.sql.Date;

/**
 * @author abhiruchi
 *
 */
public class Assignment {
	
	private int id;
	private int courseID;
	private String name;
	private int studentID;
	private Date uploadDate;
	private Date duedate;
	private Boolean metadatamismatch;
	private Boolean isChecked;
	private String shaID;
	private int plagiarismCount;
	private String githubLink;
	
	public Assignment(String name, int studentID, Date uploadDate, Date duedate, Boolean metadatamismatch,
	Boolean isChecked, String shaID, int plagiarismCount, String githubLink,  int courseID) {
		super();
		this.setCourseID(courseID);
		this.setName(name);
		this.setStudentID(studentID);
		this.setUploadDate(uploadDate);
		this.setDuedate(duedate);
		this.setMetadatamismatch(metadatamismatch);
		this.setIsChecked(isChecked);
		this.setShaID(shaID);
		this.setPlagiarismCount(plagiarismCount);
		this.setGithubLink(githubLink);
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCourseID() {
		return courseID;
	}
	public void setCourseID(int courseID) {
		this.courseID = courseID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getStudentID() {
		return studentID;
	}
	public void setStudentID(int studentID) {
		this.studentID = studentID;
	}
	public Date getUploadDate() {
		return uploadDate;
	}
	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}
	public Date getDuedate() {
		return duedate;
	}
	public void setDuedate(Date duedate) {
		this.duedate = duedate;
	}
	public Boolean getMetadatamismatch() {
		return metadatamismatch;
	}
	public void setMetadatamismatch(Boolean metadatamismatch) {
		this.metadatamismatch = metadatamismatch;
	}
	public void setMetadatamismatch(int metadatamismatch) {
		if (metadatamismatch == 1)
			this.metadatamismatch = true;
		else
			this.metadatamismatch = false;
	}
	public Boolean getIsChecked() {
		return isChecked;
	}
	public void setIsChecked(Boolean isChecked) {
		this.isChecked = isChecked;
	}
	public void setIsChecked(int isChecked) {
		if (isChecked == 1)
			this.isChecked = true;
		else
			this.isChecked = false;
	}
	public String getShaID() {
		return shaID;
	}
	public void setShaID(String shaID) {
		this.shaID = shaID;
	}
	public int getPlagiarismCount() {
		return plagiarismCount;
	}
	public void setPlagiarismCount(int plagiarismCount) {
		this.plagiarismCount = plagiarismCount;
	}
	public String getGithubLink() {
		return githubLink;
	}
	public void setGithubLink(String githubLink) {
		this.githubLink = githubLink;
	}

}
