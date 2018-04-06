package edu.northeastern.cs5500;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import edu.northeastern.springbootjdbc.controllers.CourseRoleService;
import edu.northeastern.springbootjdbc.daos.CourseDao;
import edu.northeastern.springbootjdbc.daos.CourseRoleDao;
import edu.northeastern.springbootjdbc.daos.PersonDao;
import edu.northeastern.springbootjdbc.models.Course;
import edu.northeastern.springbootjdbc.models.Person;

/**
 * CourseRoleController Test class 
 * @author Paarth
 *
 */
@RunWith(PowerMockRunner.class)
@ContextConfiguration(classes = CourseRoleService.class)
public class CourseRoleControllerTests {

	private MockMvc mockMvc;
	@Mock
	private CourseRoleDao mockDao;
	@Mock
	private PersonDao mockPersonDao;
	@Mock
	private CourseDao mockCourseDao;
	@InjectMocks
	private CourseRoleService courseRoleService;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(courseRoleService).build();
	}

	/**
	 * Test to find all the people for a course
	 * @throws Exception
	 */
	@Test
	public void testFindAllPersonsForCourse() throws Exception {
		mockDao = PowerMockito.mock(CourseRoleDao.class);
		mockDao.setInstance(mockDao);
		mockCourseDao.setInstance(mockCourseDao);
		List<Person> pList = new ArrayList<Person>();
		Person p1 = new Person("Denny", "Tiger", "denny@gmai.com", "hi", "123", "STUDENT");
		Person p2 = new Person("John", "Tiger", "j@gmai.com", "hi", "123", "PROFESSOR");
		pList.add(p1);
		pList.add(p2);
		PowerMockito.when(mockCourseDao.getCourseID("CS5500", "Spring")).thenReturn(3);
		PowerMockito.when(mockDao.getPersonsForCourse(3, "STUDENT")).thenReturn(pList);
		mockMvc.perform(
				get("/api/get/persons").param("code", "CS5500").param("semester", "Spring").param("role", "STUDENT"))
				.andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)));
	}

	/**
	 * Test to join a course
	 * @throws Exception
	 */
	@Test
	public void testJoinCourse() throws Exception {
		mockDao = PowerMockito.mock(CourseRoleDao.class);
		mockDao.setInstance(mockDao);
		String json = "{ courseId = 12, userId = 1}";
		PowerMockito.when(mockDao.joinCourseAsStudent(12, 1)).thenReturn(1);
		MvcResult result = mockMvc.perform(post("/api/user/course/join").content(json)).andExpect(status().isOk())
				.andReturn();

		assertEquals(result.getResponse().getContentAsString(), "1");
	}

	/**
	 * Test to add a TA to a course
	 * @throws Exception
	 */
	@Test
	public void testAddTAToCourse() throws Exception {
		mockDao = PowerMockito.mock(CourseRoleDao.class);
		mockDao.setInstance(mockDao);
		mockCourseDao.setInstance(mockCourseDao);
		mockPersonDao.setInstance(mockPersonDao);
		Person p1 = new Person("Denny", "Tiger", "denny@gmai.com", "hi", "123", "STUDENT");
		PowerMockito.when(mockPersonDao.findPersonByUsername("aj")).thenReturn(p1);
		PowerMockito.when(mockCourseDao.getCourseID("CS5500", "Fall")).thenReturn(5);
		PowerMockito.when(mockDao.addTA(5, p1.getId())).thenReturn(1);
		MvcResult result = mockMvc.perform(
				get("/api/add/courseTA").param("code", "CS5500").param("semester", "Fall").param("username", "aj"))
				.andExpect(status().isOk()).andReturn();

		assertEquals(result.getResponse().getContentAsString(), "1");
	}

	/**
	 * Test to delete a student from a course
	 * @throws Exception
	 */
	@Test
	public void testDeleteStudentFromCourse() throws Exception {
		mockDao = PowerMockito.mock(CourseRoleDao.class);
		mockDao.setInstance(mockDao);
		String json = "{ courseId = 12, userId = 1}";
		PowerMockito.when(mockDao.unmapProfessor(12, 1)).thenReturn(1);
		MvcResult result = mockMvc.perform(post("/api/user/course/drop").content(json)).andExpect(status().isOk())
				.andReturn();

		assertEquals(result.getResponse().getContentAsString(), "1");
	}

	/**
	 * Test to get all courses
	 * @throws Exception
	 */
	@Test
	public void testGetAllCourses() throws Exception {
		mockDao = PowerMockito.mock(CourseRoleDao.class);
		mockDao.setInstance(mockDao);
		mockPersonDao.setInstance(mockPersonDao);
		String json = "{ courseId = 12, userId = 1}";
		Person p1 = new Person("Paarth", "Kotak", "pk@gmail.com", "hi", "123", "STUDENT");
		Course c1 = new Course("MSD", "Fall", "CS5500");
		Course c2 = new Course("DBMS", "Fall", "CS5200");
		List<Course> cList = new ArrayList<Course>();
		cList.add(c1);
		cList.add(c2);
		PowerMockito.when(mockPersonDao.findPersonByUsername("pk")).thenReturn(p1);
		PowerMockito.when(mockDao.getCourseForPerson(p1.getId())).thenReturn(cList);
		mockMvc.perform(get("/api/courses").param("username", "pk").param("role", "STUDENT")).andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(2)));
	}

	/**
	 * Test to check if person is enrolled 
	 * @throws Exception
	 */
	@Test
	 public void testIfPersonEnrolled() throws Exception {
		 mockDao = PowerMockito.mock(CourseRoleDao.class);
		 mockDao.setInstance(mockDao);
		 List<Person> pList = new ArrayList<Person>();
		 Person p1 = new Person("John", "Snow", "snow@gmail.com", "hola","6666", "STUDENT");
		 p1.setId(5);
		 Person p2 = new Person("Denaerys", "Targ", "denny@gmail.com", "hiiiii","5555", "PROFESSOR");
		 p2.setId(2);
		 pList.add(p1);
		 pList.add(p2);
		 String json = "{ firstname = 'John', lastname = 'Snow', email ='snow@gmail.com', password = 'hola', phone = '6666', type = 'STUDENT'}";
		 PowerMockito.when(mockDao.getPersonsForCourse(6, "STUDENT")).thenReturn(pList);
		 MvcResult result = mockMvc.perform(post("/api/user/5/course/6").content(json))
				 .andExpect(status().isOk())
				 .andReturn();
		 
		 assertEquals(true, result.getResponse().getContentAsString().contains("-1"));
	 }
	
	/**
	 * Test to get professor email address
	 * @throws Exception
	 */
	@Test
	public void testGetProfEmail() throws Exception {
		mockDao = PowerMockito.mock(CourseRoleDao.class);
		mockDao.setInstance(mockDao);
		mockCourseDao.setInstance(mockCourseDao);
		
		 List<Person> pList = new ArrayList<Person>();
		 Person p1 = new Person("John", "Snow", "snow@gmail.com", "hola","6666", "PROFESSOR");
		 p1.setId(5);
		 Person p2 = new Person("Denaerys", "Targ", "denny@gmail.com", "hiiiii","5555", "PROFESSOR");
		 p2.setId(2);
		 pList.add(p1);
		 pList.add(p2);
		 
		PowerMockito.when(mockCourseDao.getCourseID("CS5500", "Fall2018")).thenReturn(66);
		PowerMockito.when(mockDao.getPersonsForCourse(66, "PROFESSOR")).thenReturn(pList);
		MvcResult result = mockMvc.perform(get("/api/get/profemail")
							.param("code", "CS5500")
							.param("semester", "Fall2018")).
				andExpect(status().isOk())
				.andReturn();
		
		assertEquals("snow@gmail.com", result.getResponse().getContentAsString());
	}

}
