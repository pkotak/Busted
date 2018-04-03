package edu.northeastern.springbootjdbc.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import edu.northeastern.springbootjdbc.daos.PersonDao;
import edu.northeastern.springbootjdbc.models.Person;

/**
 * Admin Web Service
 * @author abhiruchi
 *
 */
@RestController
public class AdminService {
		PersonDao dao = PersonDao.getInstance();
		
		/**
		 * Find all unapproved users.
		 * @return a list of all the unapproved persons
		 */
		@CrossOrigin(origins = "http://localhost:4200")
		@RequestMapping("/api/admin/find/unapprovedusers")
		public @ResponseBody List<Person> getUnapprovedUsers() {
			return dao.findAllPeople(true);
		}

		/**
		 * Approve a user with given personID
		 * @return int indicating whether the person was approved
		 */
		@CrossOrigin(origins = "http://localhost:4200")
		@RequestMapping("/api/admin/approve/user/{id}")
		public @ResponseBody int approveUser(@PathVariable("id") String id) {
			return dao.approvePerson(Integer.parseInt(id));
		}

		/**
		 * Delete a user with given personID
		 * @return int indicating whether the person was removed from the database
		 */
		@CrossOrigin(origins = "http://localhost:4200")
		@RequestMapping(value="/api/user/{userId}", method=RequestMethod.DELETE)
		public @ResponseBody int deleteUser(@PathVariable("userId") String userid) {
			return dao.deletePerson(Integer.parseInt(userid));
		}
		
		/**
		 * Creates a person in the database
		 * @return 
		 * @return the number of rows affected
		 */
		@CrossOrigin(origins = "http://localhost:4200")
		@RequestMapping("/api/admin/register")
		public @ResponseBody Person insertPerson(@RequestBody String json) {
			PersonService ps = new PersonService();
			return ps.insertPerson(json);
		}
}