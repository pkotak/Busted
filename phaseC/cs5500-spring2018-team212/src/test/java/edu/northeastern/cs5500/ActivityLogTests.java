package edu.northeastern.cs5500;

import org.junit.Test;

import edu.northeastern.springbootjdbc.models.ActivityLog;
import edu.northeastern.springbootjdbc.models.ReportRecipient;

public class ActivityLogTests {

	@Test
	public void test() {
		ActivityLog al1 = new ActivityLog(1, 1, ReportRecipient.OSCCR, "");
		ActivityLog al2 = new ActivityLog(1, 1, ReportRecipient.PROFESSOR, "");
		al1.setId(al1.getId());
		al1.setPersonD(al1.getPersonD());
		al2.setRecipient(al2.getRecipient());
		al2.setSummary(al2.getSummary());
		al2 = new ActivityLog();
	}

}
