package edu.northeastern.springbootjdbc.models;

/**
 * @author abhiruchi
 *
 */
public class Course {

	private int id;
	private String name;
	private String semester;
	private String code;
	
	public Course() {
		super();
	}
	public Course(String name, String semester, String code) {
		super();
		this.setName(name);
		this.setSemester(semester);
		this.setCode(code);
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSemester() {
		return semester;
	}
	public void setSemester(String semester) {
		this.semester = semester;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	@Override
	public String toString() {
		return "Course [id=" + id + ", name=" + name + ", semester=" + semester + ", code=" + code + "]";
	}	
}