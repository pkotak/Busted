package edu.northeastern.springbootjdbc.controllers;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import edu.northeastern.springbootjdbc.daos.CourseRoleDao;
import edu.northeastern.springbootjdbc.daos.PersonDao;
import edu.northeastern.springbootjdbc.models.Person;
import edu.northeastern.springbootjdbc.models.RoleType;

/**
 * Person Web Service
 * @author Paarth
 *
 */
@RestController
public class PersonService {

	private static final Logger LOGGER = Logger.getLogger(CourseRoleDao.class.getName());
	
	/**
	 * Creates a person in the database
	 * @return 
	 * @return the number of rows affected
	 */
	@RequestMapping("/api/register")
	public @ResponseBody List<Person> insertPerson(@RequestParam("firstname") String firstname,
			@RequestParam("lastname") String lastname,
			@RequestParam("email") String email,
			@RequestParam("password") String password,
			@RequestParam("phone") String phone,
			@RequestParam("type") String type) {
		PersonDao dao = PersonDao.getInstance();
		Person p = new Person(firstname, lastname, email, password, phone, type);
		dao.createPerson(p);
		return dao.findAllPeople();
	}

	/**
	 * Find all people
	 * @return List of people in the database
	 */
	@RequestMapping("/api/find/people")
	public List<Person> selectAllPeople() {
		PersonDao dao = PersonDao.getInstance();
		return dao.findAllPeople();
	}
	
	/**
	 * Find a person by user name
	 * @param username unique string based on which the search is conducted
	 * @return Person details
	 */
	@RequestMapping("/api/find/person")
	public @ResponseBody Person selectPersonByUsername(@RequestParam("username") String username) {
		PersonDao dao = PersonDao.getInstance();
		return dao.findPersonByUsername(username);
	}
	
	/**
	 * Find a person by user name
	 * @param username unique string based on which the search is conducted
	 * @return Person details
	 */
	@RequestMapping(value = "/api/person/{userid}", method=RequestMethod.PUT)
	public @ResponseBody Person updatePerson(@PathVariable("id") int id,
			@RequestBody String json) {
		PersonDao dao = PersonDao.getInstance();
		JSONObject obj;
		try {
			obj = new JSONObject(json);
			String firstname = obj.getString("firstName");
			String lastname = obj.getString("lastName");
			String password = obj.getString("password");
			String email = obj.getString("email");
			Person p = new Person(firstname, lastname, email, password, "123", RoleType.STUDENT.name());
			dao.updatePerson(id,p);
		} catch (JSONException e) {
			LOGGER.log(Level.INFO, e.toString());
		}
		return dao.findPersonById(id);
	}

}
