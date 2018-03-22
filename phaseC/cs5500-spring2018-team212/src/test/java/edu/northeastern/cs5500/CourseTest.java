package edu.northeastern.cs5500;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

import edu.northeastern.springbootjdbc.controllers.CourseService;
import edu.northeastern.springbootjdbc.daos.CourseDao;
import edu.northeastern.springbootjdbc.models.Course;

public class CourseTest {

	CourseDao cdao =  CourseDao.getInstance();
	CourseService cs = new CourseService();
	@Test
	public void testnewCourse() {
		Course c = new Course("Algo", "Spring2018", "CS5800");
		int id = cs.createCourse(c.getName(), c.getCode(), c.getSemester(), 1);  
		c.setId(id);
		assertEquals(id, c.getId());
		c.setName("Algorithms");
		assertEquals(1, cs.updateCourse(c.getName(), c.getSemester(), c.getCode(), c.getId()));
		assertEquals(1, cs.deleteCourse(id, 1));
	}


	@Test
	public void test2() {
		assertEquals(2, cdao.getCourseIDbyCode("CS5200").size());
	}
	
	@Test
	public void test3() {
		Course c = new Course("DBMS", "Spring2018", "CS5200");
		c.setId(2);
		assertEquals(c.toString(), cdao.findCoursebyID(2).toString());
	}
	
	@Test
	public void test4() {
		assertFalse(cdao.findAllCourses().isEmpty());
	}
	
	@Test
	public void test5() {
		assertEquals(3, cdao.getCourseID("CS5200", "Fall2017"));
	}
	
	@Test
	public void test6() {
		assertFalse(cs.selectAllCourses().isEmpty());
	}
	
}