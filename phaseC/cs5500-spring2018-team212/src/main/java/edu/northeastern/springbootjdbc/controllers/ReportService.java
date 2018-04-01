package edu.northeastern.springbootjdbc.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.northeastern.springbootjdbc.daos.ReportDao;
import edu.northeastern.springbootjdbc.models.Report;

/**
 * Report Web Service
 * @author Paarth
 *
 */
@RestController
public class ReportService {

	/**
	 * Create a report
	 * @return the number of rows affected after report created
	 */
	@CrossOrigin(origins = {"http://localhost:4200", "http://ec2-18-222-88-122.us-east-2.compute.amazonaws.com:4200"})
	@RequestMapping("/api/create/report")
	public int insertStudent() {
		ReportDao dao = ReportDao.getInstance();
		Report r = new Report(1, 2, 85, "http://github/hrua", false);
		return dao.createReport(r);
	}
}
