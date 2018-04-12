package edu.northeastern.cs5500;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.jpa.criteria.expression.SearchedCaseExpression.WhenClause;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.zeroturnaround.zip.ZipUtil;

import edu.northeastern.springbootjdbc.controllers.AssignmentService;
import edu.northeastern.springbootjdbc.daos.AssignmentDao;
import edu.northeastern.springbootjdbc.daos.CourseDao;
import edu.northeastern.springbootjdbc.models.Assignment;
import edu.northeastern.springbootjdbc.models.Course;

@RunWith(PowerMockRunner.class)
@ContextConfiguration(classes = AssignmentService.class)
public class AssignmentControllerTests {

	private MockMvc mockMvc;
	@Mock
	private AssignmentDao mockDao;
	@Mock
	private CourseDao mockCourseDao;
	@InjectMocks
	private AssignmentService assignmentService;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(assignmentService).build();
	}

	@Test
	public void testAssignmentCreate() throws Exception {
		mockDao = PowerMockito.mock(AssignmentDao.class);
		mockDao.setInstance(mockDao);
		String json = "{ name : 'HW1', studentId : 15, courseId : 6, duedate : '2018-05-20'}";
		PowerMockito.when(mockDao.createAssignment(any(Assignment.class))).thenReturn(1);
		MvcResult result = mockMvc
				.perform(post("/api/assignment/new").contentType(MediaType.APPLICATION_JSON).content(json))
				.andExpect(status().isOk()).andReturn();

		assertEquals("1", result.getResponse().getContentAsString());
	}

	@Test
	public void testAssignmentUpdate() throws Exception {
		mockDao = PowerMockito.mock(AssignmentDao.class);
		mockDao.setInstance(mockDao);
		Assignment assign = new Assignment("HW1", 1, new Date(2018, 5, 10), new Date(2018, 5, 20), false, true,
				"123123214", 123, "", 6);
		PowerMockito.when(mockDao.updateAssignment(6, assign)).thenReturn(1);
		String json = " {\"studentId\": 1,\"name\": \"HW555\",\"duedate\": \"2016-04-04\",\"courseId\": 6,\"githublink\": \"http://\"}";
		MvcResult result = mockMvc.perform(put("/api/assignment/{assignmentId}", 3).content(json))
				.andExpect(status().isOk()).andReturn();
	}

	@Test
	public void testGetSubmissions() throws Exception {
		mockDao = PowerMockito.mock(AssignmentDao.class);
		mockDao.setInstance(mockDao);
		List<Assignment> aList = new ArrayList<Assignment>();
		Assignment a1 = new Assignment("HW1", 15, new Date(2018, 5, 10), new Date(2018, 5, 20), false, true,
				"123123214", 123, "", 6);
		Assignment a2 = new Assignment("HW2", 15, new Date(2018, 5, 10), new Date(2018, 5, 20), false, true,
				"123123214", 123, "", 8);
		aList.add(a1);
		aList.add(a2);
		PowerMockito.when(mockDao.getSubmissionsForOneStudent("HW1", 2, 15)).thenReturn(aList);
		mockMvc.perform(get("/api/course/2/assignment/HW1/user/15")).andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(2)));

	}

	@Test
	public void testIndividualComparison() throws Exception {
		mockDao = PowerMockito.mock(AssignmentDao.class);
		mockCourseDao = PowerMockito.mock(CourseDao.class);
		mockCourseDao.setInstance(mockCourseDao);
		mockDao.setInstance(mockDao);
		Course c1 = new Course("Algo", "Spring2018", "CS5800");
		Course c2 = new Course("Algo", "Spring2018", "CS5800");
		Map<Integer, String> map = new HashMap<Integer, String>();
		map.put(1, "Algo");
		map.put(2, "28");
		PowerMockito.when(mockCourseDao.findCoursebyID(28)).thenReturn(c1);
		PowerMockito.when(mockCourseDao.findCoursebyID(28)).thenReturn(c2);
		PowerMockito.when(mockDao.getInfoforAssignment(1)).thenReturn(map);
		PowerMockito.when(mockDao.getInfoforAssignment(2)).thenReturn(map);

		mockMvc.perform(get("/api/assignment/individual/1/2")).andExpect(status().isOk());
	}

	@Test
	public void testGettingSubmissions() throws Exception {
		mockDao = PowerMockito.mock(AssignmentDao.class);
		mockDao.setInstance(mockDao);
		List<Assignment> aList = new ArrayList<Assignment>();
		Assignment a1 = new Assignment("HW1", 15, new Date(2018, 5, 10), new Date(2018, 5, 20), false, true,
				"123123214", 123, "", 6);
		Assignment a2 = new Assignment("HW2", 15, new Date(2018, 5, 10), new Date(2018, 5, 20), false, true,
				"123123214", 123, "", 8);
		aList.add(a1);
		aList.add(a2);
		PowerMockito.when(mockDao.getSubmissionsForAssignment(any(String.class), any(Integer.class))).thenReturn(aList);
		
		mockMvc.perform(get("/api/course/3/assignment/HW1")).andExpect(status().isOk());
	}
	
	@Test
	public void testFindAssignmentById() throws Exception {
		mockDao = PowerMockito.mock(AssignmentDao.class);
		mockDao.setInstance(mockDao);
		Assignment a1 = new Assignment("HW1", 15, new Date(2018, 5, 10), new Date(2018, 5, 20), false, true,
				"123123214", 123, "", 6);
		PowerMockito.when(mockDao.findAssignmentById(any(Integer.class))).thenReturn(a1);
		
		mockMvc.perform(get("/api/course/assignment/5")).andExpect(status().isOk());
	}
	
	@Test
	public void testAvailableAssignments() throws Exception {
		mockDao = PowerMockito.mock(AssignmentDao.class);
		mockDao.setInstance(mockDao);
		List<Assignment> aList = new ArrayList<Assignment>();
		Assignment a1 = new Assignment("HW1", 15, new Date(2018, 5, 10), new Date(2018, 5, 20), false, true,
				"123123214", 123, "", 6);
		Assignment a2 = new Assignment("HW2", 15, new Date(2018, 5, 10), new Date(2018, 5, 20), false, true,
				"123123214", 123, "", 8);
		aList.add(a1);
		aList.add(a2);
		PowerMockito.when(mockDao.getAvailableAssignments(any(Integer.class), any(Integer.class))).thenReturn(aList);
		
		mockMvc.perform(get("/api/5/course/10/assignment")).andExpect(status().isOk());
	}
	
	@Test
	public void testDeleteAssignment() throws Exception {
		mockDao = PowerMockito.mock(AssignmentDao.class);
		mockDao.setInstance(mockDao);
		Assignment a1 = new Assignment("HW1", 15, new Date(2018, 5, 10), new Date(2018, 5, 20), false, true,
				"123123214", 123, "", 6);
		PowerMockito.when(mockDao.deleteAssignment(any(Integer.class))).thenReturn(5);
		
		mockMvc.perform(delete("/api/course/assignment/5")).andExpect(status().isOk());
	}
	
	@Test
	public void testUploadGit() throws Exception {
		mockDao = PowerMockito.mock(AssignmentDao.class);
		mockCourseDao = PowerMockito.mock(CourseDao.class);
		mockCourseDao.setInstance(mockCourseDao);
		mockDao.setInstance(mockDao);
		Assignment a1 = new Assignment("HW1", 15, new Date(2018, 5, 10), new Date(2018, 5, 20), false, true,
				"123123214", 123, "", 6);
		Course c1 = new Course("Algo", "Spring2018", "CS5800");
		String json = "{hwName : 'HW1', githublink : 'github.com', courseid : 2, studentid : 5, parentAssignment : 7}";
		PowerMockito.when(mockDao.findAssignmentById(any(Integer.class))).thenReturn(a1);
		PowerMockito.when(mockCourseDao.findCoursebyID(any(Integer.class))).thenReturn(c1);
		PowerMockito.when(mockDao.createAssignment(any(Assignment.class))).thenReturn(0);
		mockMvc.perform(post("/api/assignment/uploadGit").content(json)).andExpect(status().isOk());
	}

}
