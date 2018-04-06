package edu.northeastern.cs5500;

import java.util.List;

import org.junit.Test;

import edu.northeastern.springbootjdbc.controllers.ReportService;
import edu.northeastern.springbootjdbc.daos.ReportDao;
import edu.northeastern.springbootjdbc.models.Report;

public class ReportTests {

	@Test
	public void test() {
		ReportDao rs = ReportDao.getInstance();
		List<Report> reports = rs.findAllReports(599, "HW1");
		Report report = reports.get(0);
		rs.deleteReport(report.getId());
		rs.createReport(report);
		rs.updateReport(report.getId(), report);
		report.setIsResolved(1);
		report.setIsResolved(0);
		report.toString();
		ReportService reps = new ReportService();
		reps.getReports(599, "HW1");
	}
}
