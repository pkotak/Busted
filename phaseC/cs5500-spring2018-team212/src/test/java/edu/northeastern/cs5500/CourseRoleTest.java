package edu.northeastern.cs5500;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.northeastern.springbootjdbc.controllers.CourseRoleService;
import edu.northeastern.springbootjdbc.daos.CourseRoleDao;
import edu.northeastern.springbootjdbc.models.CourseRole;
import edu.northeastern.springbootjdbc.models.Person;
import edu.northeastern.springbootjdbc.models.RoleType;

public class CourseRoleTest {

	CourseRoleDao crdao =  CourseRoleDao.getInstance();
	CourseRoleService crs = new CourseRoleService();
	
	@Test
	public void testfindProfessor1() {
		assertEquals(1, crs.getPersonsforCourse("CS5500", "Spring2018", RoleType.PROFESSOR.name()).size());		
	}
	
	@Test
	public void testfindProfessor2() {
		assertEquals(0, crs.getPersonsforCourse("CS4400", "Fall2017", RoleType.PROFESSOR.name()).size());
		
	}

	@Test
	public void testfindCourses1() {
		Person p = new Person("Jose", "Annunziato", "jga@ccs.neu.edu", "password", "8734653652", RoleType.PROFESSOR.name());
		p.setId(3);
		assertEquals(2, crs.getCoursesforPerson(p.getEmail(), p.getType()).size());
		
	}
	
	@Test
	public void testfindStudents() {
		assertFalse(crs.getPersonsforCourse("CS5500", "Spring2018", RoleType.STUDENT.name()).isEmpty());
	}
	
	@Test
	public void testaddTA() {
		Person p = new Person("Jane", "Doe", "jad@gmail.com", "bye", "3762029850", RoleType.TA.name());
		assertEquals(1, crs.addCourseTA("CS5500", "Spring2018", p.getEmail()));
	}
	
	@Test
	public void testjoinCourse() {
		Person p = new Person("Jake", "Dot", "jt@gmail.com", "byebye", "3762142550", RoleType.STUDENT.name());
		p.setId(8);
		assertEquals(1, crs.joinCourse("CS5200", "Fall2017", p.getId()));
	}
	
	@Test
	public void testjoinCourse2() {
		Person p =new Person();
		assertEquals(0, crs.joinCourse("", "", p.getId()));
	}
	
	@Test
	public void testEmail() {
		Person p = new Person("Jose", "Annunziato", "jga@ccs.neu.edu", "password", "8734653652", RoleType.PROFESSOR.name());
		p.setId(3);
		CourseRole cr = new CourseRole(1, p.getId(), p.getType(), 23);
		new CourseRole();
		cr.setId(cr.getId());
		cr.setCourseID(cr.getCourseID());
		cr.setPersonID(cr.getPersonID());
		cr.setRoleType(cr.getRoleType());
		cr.toString();
		assertEquals(p.getEmail(), crs.getProfEmail("CS5500", "Spring2018"));
	}
}