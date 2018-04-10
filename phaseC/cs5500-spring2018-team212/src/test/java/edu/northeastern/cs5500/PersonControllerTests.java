package edu.northeastern.cs5500;

import static org.hamcrest.Matchers.is;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import edu.northeastern.springbootjdbc.controllers.PersonService;
import edu.northeastern.springbootjdbc.daos.PersonDao;
import edu.northeastern.springbootjdbc.models.Person;

@RunWith(PowerMockRunner.class)
@ContextConfiguration(classes = PersonService.class)
public class PersonControllerTests {

	private MockMvc mockMvc;
	@Mock
	private PersonDao mockDao;
	@InjectMocks
	private PersonService personService;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(personService).build();
	}

	@Test
	public void testFindById() throws Exception {
		mockDao = PowerMockito.mock(PersonDao.class);
		Person p = new Person("Denny", "Tiger", "denny@gmai.com", "hi", "123", "STUDENT");
		PowerMockito.when(mockDao.findPersonById(3)).thenReturn(p);
		System.out.println(mockDao.findPersonById(3));
		mockDao.setInstance(mockDao);
		mockMvc.perform(get("/api/user/{userid}", 3)).andExpect(status().isOk())
				.andExpect(jsonPath("$.firstName", is("Denny")));

		assertEquals(mockDao.findPersonById(3), p);
	}

	@Test
	public void testFindByCredentials() throws Exception {
		mockDao = PowerMockito.mock(PersonDao.class);
		mockDao.setInstance(mockDao);
		Person p = new Person("Denny", "Tiger", "denny@gmai.com", "hi", "123", "STUDENT");
		PowerMockito.when(mockDao.findPersonByUsername("denny@gma.com")).thenReturn(p);
		mockMvc.perform(get("/api/user/").param("username", "denny@gma.com")).andExpect(status().isOk())
				.andExpect(jsonPath("$.firstName", is("Denny")));

	}

	@Test
	public void testFindAll() throws Exception {
		mockDao = PowerMockito.mock(PersonDao.class);
		mockDao.setInstance(mockDao);
		List<Person> pList = new ArrayList<Person>();
		Person p1 = new Person("Denny", "Tiger", "denny@gmai.com", "hi", "123", "STUDENT");
		Person p2 = new Person("John", "Snow", "jsnow@gmail.com", "hiuuu", "6666", "PROFESSOR");
		pList.add(p1);
		pList.add(p2);
		PowerMockito.when(mockDao.findAllPeople(false)).thenReturn(pList);
		mockMvc.perform(get("/api/user/all")).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)));
	}

	@Test
	public void testSelectPersonByUsername() throws Exception {
		mockDao = PowerMockito.mock(PersonDao.class);
		mockDao.setInstance(mockDao);
		Person p = new Person("Denny", "Tiger", "denny@gmai.com", "hi", "123", "STUDENT");
		PowerMockito.when(mockDao.findPersonByUsername("jga@ccs.neu.edu")).thenReturn(p);
		mockMvc.perform(get("/api/user").param("username", "jga@ccs.neu.edu")).andExpect(status().isOk())
				.andExpect(jsonPath("$.email", is("denny@gmai.com")));
	}

	@Test
	public void testCreate() throws Exception {
		mockDao = PowerMockito.mock(PersonDao.class);
		mockDao.setInstance(mockDao);
		Person p = new Person("Denny", "Tiger", "denny@gmai.com", "hi", "123", "STUDENT");
		String json = "{ firstname = 'Denny', lastname = 'Tiger', email = 'denny@gmai.com', password = 'hi', phone = '123', role = 'STUDENT'}";
		PowerMockito.when(mockDao.createPerson(p)).thenReturn(1);
		mockMvc.perform(post("/api/register").content(json)).andExpect(status().isOk());
	}
	
	@Test
	public void testUpdate() throws Exception {
		mockDao = PowerMockito.mock(PersonDao.class);
		mockDao.setInstance(mockDao);
		Person p1 = new Person("Denny", "Tiger", "denny@gmai.com", "hi", "123", "STUDENT");
		Person p2 = new Person("Denaerys", "Targ", "denny@gmail.com", "hiiiii", "5555", "PROFESSOR");
		String json = "{ firstname = 'Denny', lastname = 'Tiger', email = 'denny@gmai.com', password = 'hi', phone = '123', role = 'STUDENT'}";
		PowerMockito.when(mockDao.updatePerson(1, p2)).thenReturn(1);
		mockMvc.perform(post("/api/register").content(json)).andExpect(status().isOk());
	}

}
