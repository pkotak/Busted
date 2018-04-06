package edu.northeastern.cs5500;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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

import edu.northeastern.springbootjdbc.controllers.CourseService;
import edu.northeastern.springbootjdbc.daos.CourseDao;
import edu.northeastern.springbootjdbc.models.Course;

/**
 * Course Services Test Class
 * @author Paarth
 *
 */
@RunWith(PowerMockRunner.class)
@ContextConfiguration(classes = CourseService.class)
public class CourseControllerTests {

	private MockMvc mockMvc;
	@Mock
	private CourseDao mockDao;
	@InjectMocks
	private CourseService courseService;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(courseService).build();
	}

	/**
	 * Test to create a user
	 * @throws Exception
	 */
	@Test
	public void testCreateUser() throws Exception {
		mockDao = PowerMockito.mock(CourseDao.class);
		Course c = new Course("Algo", "Spring2018", "CS5800");
		String json = "{ name : 'Algor', semester : 'Spring2018', code : 'CS5800'}";
		PowerMockito.when(mockDao.createCourse(c)).thenReturn(1);
		System.out.println(mockDao.createCourse(c));
		mockDao.setInstance(mockDao);
		MvcResult result = mockMvc.perform(post("/api/user/{userId}/courses", 3).content(json))
				.andExpect(status().isOk())
				.andReturn();
		
		assertEquals("0", result.getResponse().getContentAsString());
	}

	/**
	 * Test to find a course by ID
	 * @throws Exception
	 */
	@Test
	public void testFindCourseById() throws Exception {
		mockDao = PowerMockito.mock(CourseDao.class);
		mockDao.setInstance(mockDao);
		Course c = new Course("Algo", "Spring2018", "CS5800");
		PowerMockito.when(mockDao.findCoursebyID(2)).thenReturn(c);
		mockMvc.perform(get("/api/user/3/course/2"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name", is("Algo")))
				.andExpect(jsonPath("$.semester", is("Spring2018")));
	}
	
	/**
	 * Test to update course by ID
	 * @throws Exception
	 */
	@Test
	public void testUpdateCourseById() throws Exception{
		mockDao = PowerMockito.mock(CourseDao.class);
		mockDao.setInstance(mockDao);
		Course c = new Course("Algo", "Spring2018", "CS5800");
		String json = "{ name : 'Algor', semester : 'Spring20189', code : 'CS5800'}";
		PowerMockito.when(mockDao.updateCourse(2,c)).thenReturn(0);
		MvcResult result = mockMvc.perform(post("/api/user/course/{courseId}",2).content(json))
					.andExpect(status().isOk())
					.andReturn();
		
		assertEquals(result.getResponse().getContentAsString(), "0");
	}

	/**
	 * Test to find all courses
	 * @throws Exception
	 */
	@Test
	public void testAllCoursesList() throws Exception {
		mockDao = PowerMockito.mock(CourseDao.class);
		mockDao.setInstance(mockDao);
		List<Course> cList = new ArrayList<Course>();
		Course c1 = new Course("Algo", "Spring2018", "CS5800");
		Course c2 = new Course("MSD", "Spring2018", "CS5500");
		cList.add(c1);
		cList.add(c2);
		PowerMockito.when(mockDao.findAllCourses()).thenReturn(cList);
		mockMvc.perform(get("/api/allCourses"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(2)));
	}

	/**
	 * Test to delete a course
	 * @throws Exception
	 */
	@Test
	public void testDeleteCourse() throws Exception {
		mockDao = PowerMockito.mock(CourseDao.class);
		mockDao.setInstance(mockDao);
		PowerMockito.when(mockDao.deleteCourse(25)).thenReturn(25);
		MvcResult result = mockMvc.perform(delete("/api/user/course/{courseId}", 25))
						.andExpect(status().isOk())
						.andReturn();
		
		assertEquals(result.getResponse().getContentAsString(), "25");
	}

}
