package edu.northeastern.springbootjdbc.daos;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import edu.northeastern.cs5500.Constants;
import edu.northeastern.springbootjdbc.models.Assignment;

/**
 * Assignment Data Access Object
 * 
 * @author sirushti
 *
 */
public class AssignmentDao {

	private static AssignmentDao instance = null;
	static final Logger LOGGER = Logger.getLogger(AssignmentDao.class.getName());

	public static AssignmentDao getInstance() {
		if (instance == null)
			return new AssignmentDao();
		else
			return instance;
	}

	private AssignmentDao() {
	}

	public int createAssignment(Assignment assignment) {
		Connection conn = null;
		PreparedStatement assignmentStatement = null;
		ResultSet rs = null;
		int assignmentID = 0;
		try {
			Class.forName(Constants.JDBC_DRIVER);
			conn = DriverManager.getConnection(Constants.CONNECTION_STRING, Constants.AWS_USERNAME, Constants.AWS_P);

			String assignmentInsert = "insert INTO Assignment (courseID, name, studentID, uploadDate, duedate, metadatamismatch, isChecked, shaID, plagiarismcount, githubLink) "
					+ "VALUES (?,?,?,?,?,?,?,?,?,?)";

			try {
				assignmentStatement = conn.prepareStatement(assignmentInsert, PreparedStatement.RETURN_GENERATED_KEYS);
				assignmentStatement.setInt(1, assignment.getCourseID());
				assignmentStatement.setString(2, assignment.getName());
				assignmentStatement.setInt(3, assignment.getStudentID());
				assignmentStatement.setDate(4, assignment.getUploadDate());
				assignmentStatement.setDate(5, assignment.getDuedate());
				assignmentStatement.setBoolean(6, assignment.getMetadatamismatch());
				assignmentStatement.setBoolean(7, assignment.getIsChecked());
				assignmentStatement.setString(8, assignment.getShaID());
				assignmentStatement.setInt(9, assignment.getPlagiarismCount());
				assignmentStatement.setString(10, assignment.getGithubLink());
				assignmentStatement.executeUpdate();
				try {
					rs = assignmentStatement.getGeneratedKeys();
					rs.next();
					assignmentID = rs.getInt(1);
				} finally {
					if (rs != null)
						rs.close();
				}
			} finally {
				if (assignmentStatement != null)
					assignmentStatement.close();
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
		return assignmentID;
	}

	public int checkAssignment(int aid) {
		int rowsAffected = 0;
		Connection conn = null;
		PreparedStatement assignmentStatement = null;
		try {
			Class.forName(Constants.JDBC_DRIVER);
			conn = DriverManager.getConnection(Constants.CONNECTION_STRING, Constants.AWS_USERNAME, Constants.AWS_P);
			String assignmentUpdate = "UPDATE Assignment SET isChecked=1 where id=?";
			try {
				assignmentStatement = conn.prepareStatement(assignmentUpdate);
				assignmentStatement.setInt(1, aid);
				rowsAffected += assignmentStatement.executeUpdate();
			} finally {
				if (assignmentStatement != null)
					assignmentStatement.close();
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

	public Assignment getAssignmentForCourse(String courseId, int studentId) {
		Connection conn = null;
		PreparedStatement assignmentStatement = null;
		Assignment a = null;
		ResultSet rs = null;
		try {
			Class.forName(Constants.JDBC_DRIVER);
			conn = DriverManager.getConnection(Constants.CONNECTION_STRING, Constants.AWS_USERNAME, Constants.AWS_P);
			try {
				try {
					assignmentStatement = conn
							.prepareStatement("SELECT * from Assignment where courseId = ? and studentId = ?");
					assignmentStatement.setString(1, courseId);
					assignmentStatement.setInt(2, studentId);
					rs = assignmentStatement.executeQuery();
				} finally {
					if (assignmentStatement != null) {
						assignmentStatement.close();
					}
				}

				if (rs.next()) {
					a = new Assignment(rs.getString("name"), rs.getInt("studentid"), rs.getDate("uploaddate"),
							rs.getDate("duedate"), rs.getBoolean("metadatamismatch"), rs.getBoolean("ischecked"),
							rs.getString("shaid"), rs.getInt("plagiarismcount"), rs.getString("githublink"),
							rs.getInt("courseid"));
					a.setId(rs.getInt("id"));
				}
			} finally {
				if (rs != null) {
					rs.close();
				}
				if (assignmentStatement != null) {
					assignmentStatement.close();
				}
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
		return a;
	}

	public int updateSha(String md5Hex) {
		int rowsAffected = 0;
		Connection conn = null;
		PreparedStatement assignmentStatement = null;
		try {
			Class.forName(Constants.JDBC_DRIVER);
			conn = DriverManager.getConnection(Constants.CONNECTION_STRING, Constants.AWS_USERNAME, Constants.AWS_P);
			String assignmentUpdate = "UPDATE Assignment SET shaid=?";
			try {
				assignmentStatement = conn.prepareStatement(assignmentUpdate);
				assignmentStatement.setString(1, md5Hex);
				rowsAffected += assignmentStatement.executeUpdate();
			} finally {
				if (assignmentStatement != null)
					assignmentStatement.close();
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

	public int getAssignmentCount(String hwName) {
		Connection conn = null;
		PreparedStatement assignmentStatement = null;
		ResultSet rs = null;
		int total = 0;
		try {
			Class.forName(Constants.JDBC_DRIVER);
			conn = DriverManager.getConnection(Constants.CONNECTION_STRING, Constants.AWS_USERNAME, Constants.AWS_P);
			String assignmentSql = "SELECT count(*) as total from Assignment where name = ?";
			try {
				try {
					assignmentStatement = conn.prepareStatement(assignmentSql);
					assignmentStatement.setString(1, hwName);
					rs = assignmentStatement.executeQuery();
					if (rs.next()) {
						total = rs.getInt("total");
					}
				} finally {
					if (assignmentStatement != null) {
						assignmentStatement.close();
					}
				}
			} finally {
				if (rs != null) {
					rs.close();
				}
				conn.close();
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
		return total;
	}

	public List<Assignment> getSubmissions(String hwName, int courseid) {
		Connection conn = null;
		PreparedStatement assignmentStatement = null;
		ResultSet rs = null;
		ArrayList<Assignment> assignments = new ArrayList<Assignment>();
		try {
			Class.forName(Constants.JDBC_DRIVER);
			conn = DriverManager.getConnection(Constants.CONNECTION_STRING, Constants.AWS_USERNAME, Constants.AWS_P);
			String assignmentSelect = "select * from Assignment where name = ? and courseid = ?";

			try {
				assignmentStatement = conn.prepareStatement(assignmentSelect);
				assignmentStatement.setString(1, hwName);
				assignmentStatement.setInt(2, courseid);
				try {
					rs = assignmentStatement.executeQuery();
					while (rs.next()) {
						int aid = rs.getInt("id");
						String name = rs.getString("name");
						int studentid = rs.getInt("studentid");
						Date uploaddate = rs.getDate("uploaddate");
						Date duedate = rs.getDate("duedate");
						Boolean metadatamismatch = rs.getBoolean("metadatamismatch");
						Boolean isChecked = rs.getBoolean("isChecked");
						String shaid = rs.getString("shaid");
						int plagiarismcount = rs.getInt("plagiarismcount");
						String githublink = rs.getString("githublink");
						Assignment assignment = new Assignment(name, studentid, uploaddate, duedate, metadatamismatch,
								isChecked, shaid, plagiarismcount, githublink, courseid);
						assignment.setId(aid);
						assignments.add(assignment);
					}
				} finally {
					if (rs != null) {
						rs.close();
					}
				}

			} finally {
				if (assignmentStatement != null)
					assignmentStatement.close();
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
		return assignments;
	}
	
	public List<String> getAvailableAssignments(int courseid) {
		Connection conn = null;
		PreparedStatement assignmentStatement = null;
		ResultSet rs = null;
		ArrayList<String> assignments = new ArrayList<String>();
		try {
			Class.forName(Constants.JDBC_DRIVER);
			conn = DriverManager.getConnection(Constants.CONNECTION_STRING, Constants.AWS_USERNAME, Constants.AWS_P);
			String assignmentSelect = "select distinct name from Assignment where courseid = ?";

			try {
				assignmentStatement = conn.prepareStatement(assignmentSelect);
				assignmentStatement.setInt(1, courseid);
				try {
					rs = assignmentStatement.executeQuery();
					while (rs.next()) {
						String hwName = rs.getString("name");
						assignments.add(hwName);
					}
				} finally {
					if (rs != null) {
						rs.close();
					}
				}
			} finally {
				if (assignmentStatement != null)
					assignmentStatement.close();
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
		return assignments;
	}
}