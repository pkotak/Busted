package edu.northeastern.springbootjdbc.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import edu.northeastern.springbootjdbc.daos.CourseDao;
import edu.northeastern.springbootjdbc.daos.CourseRoleDao;
import edu.northeastern.springbootjdbc.models.Course;

/**
 * Course web Service
 * @author abhiruchi
 *
 */
@RestController
public class CourseService {

	/**
	 * Creates a course in the database and 
	 * makes the person creating the course the professor for that course.
	 * @return the number of rows affected
	 *        - indicating whether operation was successful.
	 */
	@RequestMapping("/api/create/course")
	public @ResponseBody int createCourse(
			@RequestParam("name") String name,
			@RequestParam("code") String code,
			@RequestParam("semester") String semester,
			@RequestParam("personid") int personID) {
		CourseDao cdao = CourseDao.getInstance();
		CourseRoleDao crdao = CourseRoleDao.getInstance();
		Course c = new Course(name, semester, code);
		int r = cdao.createCourse(c);
		crdao.mapProfessor(r, personID);
		return r;
	}
	
	/**
	 * Updates a course in the database
	 * @return the number of rows affected
	 *        - indicating whether operation was successful.
	 */
	@RequestMapping("/api/update/course")
	public @ResponseBody int updateCourse(
			@RequestParam("name") String name,
			@RequestParam("code") String semester,
			@RequestParam("semester") String code,
			@RequestParam("courseID") int courseID) {
		CourseDao cdao = CourseDao.getInstance();
		Course c = new Course(name, semester, code);
		return cdao.updateCourse(courseID, c);
	}
	
	/**
	 * Deletes a course in the database
	 * @return the number of rows affected
	 *        - indicating whether operation was successful.
	 */
	@RequestMapping("/api/delete/course")
	public @ResponseBody int deleteCourse(
			@RequestParam("courseID") int courseID, 
			@RequestParam("personID") int personID) {
		CourseRoleDao.getInstance().unmapProfessor(courseID, personID);
		CourseDao cdao = CourseDao.getInstance();
		return cdao.deleteCourse(courseID);
	}
	
	/**
	 * Find all courses
	 * @return List of all the courses in the database
	 */
	@RequestMapping("/api/findall/course")
	public List<Course> selectAllCourses() {
		CourseDao cdao = CourseDao.getInstance();
		return cdao.findAllCourses();
	}	
}