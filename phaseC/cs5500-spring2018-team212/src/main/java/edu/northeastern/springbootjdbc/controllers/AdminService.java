package edu.northeastern.springbootjdbc.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
		@RequestMapping("/api/find/unapprovedusers")
		public @ResponseBody List<Person> getUnapprovedUsers() {
			return dao.findAllPeople(true);
		}

		/**
		 * Approve a user with given personID
		 * @return int indicating whether the person was approved
		 */
		@CrossOrigin(origins = "http://localhost:4200")
		@RequestMapping("/api/approve/user")
		public @ResponseBody int approveUser(@RequestParam("userid") int userid) {
			return dao.approvePerson(userid);
		}

		/**
		 * Delete a user with given personID
		 * @return int indicating whether the person was removed from the database
		 */
		@CrossOrigin(origins = "http://localhost:4200")
		@RequestMapping("/api/approve/user")
		public @ResponseBody int deleteUser(@RequestParam("userid") int userid) {
			return dao.deletePerson(userid);
		}
		
		/**
		 * Creates a person in the database
		 * @return 
		 * @return the number of rows affected
		 */
		@CrossOrigin(origins = "http://localhost:4200")
		@RequestMapping("/api/register")
		public @ResponseBody Person insertPerson(@RequestBody String json) {
			PersonService ps = new PersonService();
			return ps.insertPerson(json);
		}
}