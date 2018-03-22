package edu.northeastern.springbootjdbc.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import edu.northeastern.springbootjdbc.daos.CourseDao;
import edu.northeastern.springbootjdbc.daos.CourseRoleDao;
import edu.northeastern.springbootjdbc.daos.PersonDao;
import edu.northeastern.springbootjdbc.models.Course;
import edu.northeastern.springbootjdbc.models.Person;
import edu.northeastern.springbootjdbc.models.RoleType;

/**
 * CourseRole web Service
 * @author abhiruchi
 *
 */
@RestController
public class CourseRoleService {

	/**
	 * method for student to join a class
	 * @return the number of rows affected
	 *        - indicating whether operation was successful.
	 */
	@RequestMapping("/api/add/student")
	public @ResponseBody int joinCourse(
			@RequestParam("code") String code,
			@RequestParam("semester") String semester,
			@RequestParam("personid") int personID) {
		CourseDao cdao = CourseDao.getInstance();
		CourseRoleDao crdao = CourseRoleDao.getInstance();
		int cid = cdao.getCourseID(code, semester);
		return crdao.joinCourseAsStudent(cid, personID);
	}
	
	/**
	 * method to add TA for a course
	 * @return the number of rows affected
	 *        - indicating whether operation was successful.
	 */
	@RequestMapping("/api/add/courseTA")
	public @ResponseBody int addCourseTA(
			@RequestParam("code") String code,
			@RequestParam("semester") String semester,
			@RequestParam("username") String username) {
		int personID =  PersonDao.getInstance().findPersonByUsername(username).getId();
		int cid = CourseDao.getInstance().getCourseID(code, semester);
		return CourseRoleDao.getInstance().addTA(cid, personID);
	}
	
	/**
	 * method to get Persons for a course
	 * @return the list of Persons with given role.
	 */
	@RequestMapping("/api/get/persons")
	public @ResponseBody List<Person> getPersonsforCourse(
			@RequestParam("code") String code,
			@RequestParam("semester") String semester,
			@RequestParam("role") String role) {
		int cid = CourseDao.getInstance().getCourseID(code, semester);
		return CourseRoleDao.getInstance().getPersonsForCourse(cid, role);
	}
	
	/**
	 * method to get Courses for a person
	 * @return the list of Courses for the Person
	 */
	@RequestMapping("/api/get/courses")
	public @ResponseBody List<Course> getCoursesforPerson(
			@RequestParam("username") String username,
			@RequestParam("role") String role) {
		int personid = PersonDao.getInstance().findPersonByUsername(username).getId();
		return CourseRoleDao.getInstance().getCourseForPerson(personid, role);
	}
	
	/**
	 * method to get course Professor's email
	 * @return the String of Professor's email.
	 */
	@RequestMapping("/api/get/profemail")
	public @ResponseBody String getProfEmail(
			@RequestParam("code") String code,
			@RequestParam("semester") String semester) {
		int cid = CourseDao.getInstance().getCourseID(code, semester);
		return CourseRoleDao.getInstance().getPersonsForCourse(cid, RoleType.PROFESSOR.name()).get(0).getEmail();
	}
}
