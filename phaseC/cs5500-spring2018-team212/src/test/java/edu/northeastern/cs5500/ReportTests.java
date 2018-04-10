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
		List<Report> reports = rs.findAllReports(715, "HW3");
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
	
	@Test
	public void test2() {
		
		Report r1 = new Report(32, 23, 78, "", true);
		r1.toString();
	}
	@Test
	public void test3() {
		ReportDao rs = ReportDao.getInstance();
		rs.getReport(100);
	}
}