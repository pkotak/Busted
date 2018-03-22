package edu.northeastern.springbootjdbc.controllers;

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
	@RequestMapping("/api/create/report")
	public int insertStudent() {
		ReportDao dao = ReportDao.getInstance();
		Report r = new Report(1, 1, 2, 85, "http://github/hrua", false);
		return dao.createReport(r);
	}
}
