package edu.northeastern.cs5500;

import java.sql.Date;

import org.junit.Test;

import edu.northeastern.springbootjdbc.models.Assignment;

public class AssignmentTests {

	@Test
	public void test() {
		Assignment a = new Assignment("HW1", 1, Date.valueOf("2018-03-22"), Date.valueOf("2018-04-21"), false, false, "", 0, "", 1);
		a.setCourseID(a.getCourseID());
		a.setDuedate(a.getDuedate());
		a.setGithubLink(a.getGithubLink());
		a.setId(a.getId());
		a.getIsChecked();
		a.setIsChecked(1);
		a.setIsChecked(0);
		a.getMetadatamismatch();
		a.setMetadatamismatch(1);
		a.setMetadatamismatch(0);
		a.setPlagiarismCount(a.getPlagiarismCount());
		a.setShaID(a.getShaID());
		a.setName(a.getName());
		a.setStudentID(a.getStudentID());
		a.setUploadDate(a.getUploadDate());
	}

}
