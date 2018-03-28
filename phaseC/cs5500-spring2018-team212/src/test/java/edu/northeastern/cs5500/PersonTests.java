package edu.northeastern.cs5500;

import java.util.List;

import org.junit.Test;

import edu.northeastern.springbootjdbc.controllers.AdminService;
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
		ps.insertPerson("{'firstname':'hello','lastname':'ak','email':'aisj@gma.com','password':'hi','role':'student'}");
		ps.login("{'username':'ak@gmail.com','password':'hi'}");
		ps.updatePerson("216", "{'firstName':'gg','lastName':'', 'password':'', 'username': ''}");
		dao.deletePerson("aisj@gma.com");
	}

	@Test
	public void test2() {
		PersonService ps = new PersonService();
		ps.selectPersonByUsername("");
	}

	@Test
	public void test3() {
		Person p = new Person("A", "B", "ab@ccs.neu.edu", "hsakjfka","2838237582", RoleType.STUDENT.name());
		dao.updatePerson(0, p);
		dao.deletePerson(0);
		ReportRecipient.OSCCR.name();
		ReportRecipient.PROFESSOR.name();
	}

	@Test
	public void testAdmin() {
		AdminService as = new AdminService();
		List<Person> pl = as.getUnapprovedUsers();
		as.approveUser(30);
		as.deleteUser(30);
		pl = as.getUnapprovedUsers();
		for (Person p: pl)
			p.toString();
		Person p = new Person("A", "B", "ab@ccs.neu.edu", "hsakjfka","2838237582", RoleType.STUDENT.name());
		p.setId(30);
		p.getIsApproved();
		dao.createPerson(p);
	}
}