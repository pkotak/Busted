package edu.northeastern.springbootjdbc.models;

public class ActivityLog {

	private int id;
	private int personD;
	private ReportRecipient recipient;
	private String summary;
	
	public ActivityLog() {
		super();
	}
	
	public ActivityLog(int id, int personID, ReportRecipient rr, String summary) {
		super();
		this.setId(id);
		this.setPersonD(personID);
		this.setRecipient(rr);
		this.setSummary(summary);
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPersonD() {
		return personD;
	}
	public void setPersonD(int personD) {
		this.personD = personD;
	}
	public ReportRecipient getRecipient() {
		return recipient;
	}
	public void setRecipient(ReportRecipient recipient) {
		this.recipient = recipient;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	
}
