package edu.northeastern.springbootjdbc.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


import edu.northeastern.springbootjdbc.daos.ReportDao;
import edu.northeastern.springbootjdbc.models.Report;

/**
 * Report Web Service
 * @author sirushti
 *
 */
@RestController
public class ReportService {
	/**
	 * Create a report
	 * @return the number of rows affected after report created
	 */
	@CrossOrigin(origins = {"http://localhost:4200", "http://ec2-18-222-88-122.us-east-2.compute.amazonaws.com:4200"})
	@RequestMapping("/api/report/{courseid}/get/{hwName}")
	public @ResponseBody List<Report> getReports(@PathVariable int courseid, @PathVariable String hwName) {
		ReportDao dao = ReportDao.getInstance();
		return dao.findAllReports(courseid, hwName);
	}
	
	/** Get Report By ID
	 * @return the number of rows affected after report created
	 */
	@CrossOrigin(origins = {"http://localhost:4200", "http://ec2-18-222-88-122.us-east-2.compute.amazonaws.com:4200"})
	@RequestMapping("/api/report/{reportid}")
	public @ResponseBody Report getReportById(@PathVariable int reportid) {
		ReportDao dao = ReportDao.getInstance();
		return dao.getReport(reportid);
	}
}