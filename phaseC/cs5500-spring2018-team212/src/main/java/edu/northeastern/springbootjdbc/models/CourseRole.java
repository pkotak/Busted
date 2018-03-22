package edu.northeastern.springbootjdbc.models;

/**
 * @author abhiruchi
 *
 */
public class CourseRole {

	private int id;
	private int personID;
	private int courseID;
	private String roleType;
	
	public CourseRole() {
		super();
	}
	
	public CourseRole(int id, int personID, String roleType, int courseID) {
		super();
		this.setId(id);
		this.setPersonID(personID);
		this.setCourseID(courseID);
		this.setRoleType(roleType);
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPersonID() {
		return personID;
	}
	public void setPersonID(int personID) {
		this.personID = personID;
	}
	public int getCourseID() {
		return courseID;
	}
	public void setCourseID(int courseID) {
		this.courseID = courseID;
	}
	public String getRoleType() {
		return roleType;
	}
	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}
	
	@Override
	public String toString() {
		return "CourseRole [id=" + id + ", personID=" + personID + ", courseID=" + courseID + ", roleType=" + roleType
				+ "]";
	}

}