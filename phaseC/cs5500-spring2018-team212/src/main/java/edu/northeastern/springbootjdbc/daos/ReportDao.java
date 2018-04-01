package edu.northeastern.springbootjdbc.daos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import edu.northeastern.cs5500.Constants;
import edu.northeastern.springbootjdbc.models.Report;

/**
 * Report Database Interaction Class
 * 
 * @author Paarth
 *
 */
public class ReportDao {

	private static ReportDao instance = null;
	private static final Logger LOGGER = Logger.getLogger(PersonDao.class.getName());

	public static ReportDao getInstance() {
		if (instance == null)
			return new ReportDao();
		else
			return instance;
	}

	private ReportDao() {
	}

	/**
	 * Inserts a report into the database
	 * 
	 * @param report
	 *            Report object
	 * @return Number of rows affected in the database
	 */
	public int createReport(Report report) {

		int rowsAffected = 0;
		Connection conn = null;
		PreparedStatement reportStatement = null;
		try {
			Class.forName(Constants.JDBC_DRIVER);
			conn = DriverManager.getConnection(Constants.CONNECTION_STRING, Constants.AWS_USERNAME, Constants.AWS_P);

			String personInsert = "insert INTO Report (assignment1, assignment2, similarityscore, downloadlink, isResolved) "
					+ "VALUES (?,?,?,?,?)";

			try {
				reportStatement = conn.prepareStatement(personInsert);
				reportStatement.setInt(1, report.getAssignment1ID());
				reportStatement.setInt(2, report.getAssignment2ID());
				reportStatement.setInt(3, report.getSimilarityscore());
				reportStatement.setString(4, report.getDownloadLink());
				reportStatement.setBoolean(5, report.getIsResolved());
				rowsAffected += reportStatement.executeUpdate();
			} finally {
				if (reportStatement != null)
					reportStatement.close();
			}

		} catch (ClassNotFoundException e) {
			LOGGER.info(e.toString());
		} catch (SQLException e) {
			LOGGER.info(e.toString());
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				LOGGER.info(e.toString());
			}
		}
		return rowsAffected;

	}

	/**
	 * Updates Report details in the database
	 * 
	 * @param reportId
	 *            the specific report Id
	 * @param report
	 *            The report object to be inserted
	 * @return Number of rows affected in the database
	 */
	public int updateReport(int reportId, Report report) {
		int rowsAffected = 0;
		Connection conn = null;
		PreparedStatement reportStatement = null;
		try {
			Class.forName(Constants.JDBC_DRIVER);
			conn = DriverManager.getConnection(Constants.CONNECTION_STRING, Constants.AWS_USERNAME, Constants.AWS_P);
			String reportUpdate = "UPDATE Report "
					+ "SET id = ?, assignment1 = ?, assignment2 = ?, similarityscore = ?,downloadlink = ?, isResolved = ? "
					+ "WHERE id = ?";
			try {
				reportStatement = conn.prepareStatement(reportUpdate);
				reportStatement.setInt(1, reportId);
				reportStatement.setInt(2, report.getAssignment1ID());
				reportStatement.setInt(3, report.getAssignment2ID());
				reportStatement.setInt(4, report.getSimilarityscore());
				reportStatement.setString(5, report.getDownloadLink());
				reportStatement.setBoolean(6, report.getIsResolved());
				reportStatement.setInt(7, reportId);
				rowsAffected += reportStatement.executeUpdate();
			} finally {
				if (reportStatement != null)
					reportStatement.close();
			}

		} catch (ClassNotFoundException e) {
			LOGGER.info(e.toString());
		} catch (SQLException e) {
			LOGGER.info(e.toString());
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				LOGGER.info(e.toString());
			}
		}
		return rowsAffected;
	}

	/**
	 * Delete a report from the database
	 * 
	 * @param reportId
	 *            the specific Id for the report
	 * @return Number of rows affected in the database
	 */
	public int deleteReport(int reportId) {
		int rowsAffected = 0;
		Connection conn = null;
		PreparedStatement reportStatement = null;
		try {
			Class.forName(Constants.JDBC_DRIVER);
			conn = DriverManager.getConnection(Constants.CONNECTION_STRING, Constants.AWS_USERNAME, Constants.AWS_P);

			String reportDelete = "DELETE FROM Report where id = ?";
			try {
				reportStatement = conn.prepareStatement(reportDelete);
				reportStatement.setInt(1, reportId);
				rowsAffected += reportStatement.executeUpdate();
			} finally {
				if (reportStatement != null)
					reportStatement.close();
			}

		} catch (ClassNotFoundException e) {
			LOGGER.info(e.toString());
		} catch (SQLException e) {
			LOGGER.info(e.toString());
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				LOGGER.info(e.toString());
			}
		}
		return rowsAffected;
	}

	/**
	 * Find all the reports
	 * 
	 * @return the List of all the Reports and their details
	 */
	public List<Report> findAllReports() {
		List<Report> reportList = new ArrayList<Report>();
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet results = null;
		try {
			Class.forName(Constants.JDBC_DRIVER);
			conn = DriverManager.getConnection(Constants.CONNECTION_STRING, Constants.AWS_USERNAME, Constants.AWS_P);

			String sql = "select * from Report";
			try {
				statement = conn.prepareStatement(sql);
				try {
					results = statement.executeQuery();
					while (results.next()) {
						int id = Integer.parseInt(results.getString("id"));
						int assign1Id = results.getInt("assignment1");
						int assign2Id = results.getInt("assignment2");
						int similarityScore = results.getInt("similarityscore");
						String downloadLink = results.getString("downloadlink");
						boolean isResolved = results.getBoolean("isResolved");
						Report report = new Report(assign1Id, assign2Id, similarityScore, downloadLink, isResolved);
						report.setId(id);
						reportList.add(report);
					}
				} finally {
					if (results != null)
						results.close();
				}
			} finally {
				if (statement != null)
					statement.close();
			}

		} catch (ClassNotFoundException e) {
			LOGGER.info(e.toString());
		} catch (SQLException e) {
			LOGGER.info(e.toString());
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				LOGGER.info(e.toString());
			}
		}
		return reportList;
	}

}