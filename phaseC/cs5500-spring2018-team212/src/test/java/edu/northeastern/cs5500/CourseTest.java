package edu.northeastern.cs5500;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.HashMap;
import java.util.Map;

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
		int id = cs.createCourse("3","{\"name\":\""+c.getName()+ "\",\"code\":\""+c.getCode()+"\","+"\"semester\":\"" +c.getSemester()+"\"}");  
		c.setId(id);
		assertEquals(id, c.getId());
		c.setName("Algorithms");
		assertEquals(1, cs.updateCourse(c.getName(), c.getSemester(), c.getCode(), c.getId()));
		assertEquals(1, cs.deleteCourse(c.getId()));
	}


	@Test
	public void testupdateCourseById() {
		CourseService cs = new CourseService();
		cs.updateCourseById("2", "{'name':'DBMS', 'semester':'Fall2016','code':'CS5200'}");
	}
	
	@Test
	public void testselectCourseById() {
		CourseService cs = new CourseService();
		System.out.println(cs.selectCoursesByCourseId("5", "2"));
	}
	
	@Test
	public void test2() {
		assertEquals(3, cdao.getCourseIDbyCode("CS5200").size());
	}
	
	@Test
	public void test3() {
		Course c = new Course("DBMS", "Spring2018", "CS5200");
		c.setId(2);
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
	
	@Test
	public void test7() {
		Map<String, String> hmap = new HashMap<String, String>();
		hmap.put("semester", "Spring 2018");
		cs.selectCoursesbySemester(hmap);
		cs.selectCoursesbySemester(new HashMap<String, String>());
	}
	
	
	
}