package edu.northeastern.cs5500;

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
		PersonDao dao = PersonDao.getInstance();
		ps.insertPerson("{'firstname':'hello','lastname':'ak','email':'aisj@gma.com','password':'hi','role':'student'}");
		ps.login("{'username':'ak@gmail.com','password':'hi'}");
		ps.updatePerson("216", "{'firstName':'gg','lastName':'', 'password':'', 'username': ''}");
		dao.deletePerson("aisj@gma.com");
	}

	/////for coverage
	@Test
	public void test2() {
		PersonService ps = new PersonService();
//		ps.updatePerson(0 , "");
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