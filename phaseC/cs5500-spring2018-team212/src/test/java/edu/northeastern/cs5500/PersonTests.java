package edu.northeastern.cs5500;

import java.util.Random;

import org.junit.Test;

import edu.northeastern.springbootjdbc.controllers.PersonService;
import edu.northeastern.springbootjdbc.daos.PersonDao;
import edu.northeastern.springbootjdbc.models.Person;
import edu.northeastern.springbootjdbc.models.ReportRecipient;
import edu.northeastern.springbootjdbc.models.RoleType;


/**
 * Person Tests
 * @author Paarth
 *
 */
public class PersonTests {

	PersonDao dao = PersonDao.getInstance();
	@Test
	public void testFindPeople() {
		PersonService ps = new PersonService();
		Random r = new Random();
		Integer random = r.nextInt(50)+1;
		String email = "rree@gmail.com";
		ps.insertPerson(random.toString(), random.toString(), email, random.toString(), "123", "student");
		//		Person p = ps.selectPersonByUsername(email);
		//		ps.updatePerson(12, p);
		//		ps.deletePersonByUsername(p.getEmail());
		ps.selectAllPeople();


	}

	/////for coverage
	@Test
	public void test2() {
		PersonService ps = new PersonService();
		ps.updatePerson(0 , "");
		ps.selectPersonByUsername("");
	}

	/////for coverage
	@Test
	public void test3() {
		Person p = new Person("A", "B", "ab@ccs.neu.edu", "hsakjfka","2838237582", RoleType.STUDENT.name());
		dao.updatePerson(0, p);
		dao.deletePerson(0);
		ReportRecipient.OSCCR.name();
		ReportRecipient.PROFESSOR.name();
	}

}