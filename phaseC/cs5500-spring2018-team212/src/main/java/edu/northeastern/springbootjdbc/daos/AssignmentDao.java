package edu.northeastern.springbootjdbc.daos;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

	public Assignment findAssignmentById(int id) {
		Connection conn = null;
		PreparedStatement assignmentStatement = null;
		Assignment a = null;
		ResultSet rs = null;
		try {
			Class.forName(Constants.JDBC_DRIVER);
			conn = DriverManager.getConnection(Constants.CONNECTION_STRING, Constants.AWS_USERNAME, Constants.AWS_P);
			try {
				try {
					assignmentStatement = conn.prepareStatement("SELECT * from Assignment where id = ?");


					assignmentStatement.setInt(1, id);
					rs = assignmentStatement.executeQuery();
					if (rs.next()) {
						a = new Assignment(rs.getString("name"), rs.getInt("studentid"), rs.getDate("uploaddate"),
								rs.getDate("duedate"), rs.getBoolean("metadatamismatch"), rs.getBoolean("ischecked"),
								rs.getString("shaid"), rs.getInt("plagiarismcount"), rs.getString("githublink"),
								rs.getInt("courseid"));
						a.setId(rs.getInt("id"));
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

	public int updateSha(String md5Hex, int aid) {
		int rowsAffected = 0;
		Connection conn = null;
		PreparedStatement assignmentStatement = null;
		try {
			Class.forName(Constants.JDBC_DRIVER);
			conn = DriverManager.getConnection(Constants.CONNECTION_STRING, Constants.AWS_USERNAME, Constants.AWS_P);
			String assignmentUpdate = "UPDATE Assignment SET shaid=? where id=?";
			try {
				assignmentStatement = conn.prepareStatement(assignmentUpdate);
				assignmentStatement.setString(1, md5Hex);
				assignmentStatement.setInt(2, aid);
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

	public List<Assignment> getSubmissions(String hwName, int courseid, int studentid) {
		Connection conn = null;
		PreparedStatement assignmentStatement = null;
		ResultSet rs = null;
		ArrayList<Assignment> assignments = new ArrayList<Assignment>();
		try {
			Class.forName(Constants.JDBC_DRIVER);
			conn = DriverManager.getConnection(Constants.CONNECTION_STRING, Constants.AWS_USERNAME, Constants.AWS_P);
			String assignmentSelect = "select * from Assignment where name = ? and courseid = ? and studentid = ?";

			try {
				assignmentStatement = conn.prepareStatement(assignmentSelect);
				assignmentStatement.setString(1, hwName);
				assignmentStatement.setInt(2, courseid);
				assignmentStatement.setInt(3, studentid);
				try {
					rs = assignmentStatement.executeQuery();
					while (rs.next()) {
						int aid = rs.getInt("id");
						String name = rs.getString("name");
						int studentid1 = rs.getInt("studentid");
						Date uploaddate = rs.getDate("uploaddate");
						Date duedate = rs.getDate("duedate");
						Boolean metadatamismatch = rs.getBoolean("metadatamismatch");
						Boolean isChecked = rs.getBoolean("isChecked");
						String shaid = rs.getString("shaid");
						int plagiarismcount = rs.getInt("plagiarismcount");
						String githublink = rs.getString("githublink");
						Assignment assignment = new Assignment(name, studentid1, uploaddate, duedate, metadatamismatch,
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

	public List<Assignment> getAvailableAssignments(int courseid, int profid) {
		Connection conn = null;
		PreparedStatement assignmentStatement = null;
		ResultSet rs = null;
		ArrayList<Assignment> assignments = new ArrayList<Assignment>();
		try {
			Class.forName(Constants.JDBC_DRIVER);
			conn = DriverManager.getConnection(Constants.CONNECTION_STRING, Constants.AWS_USERNAME, Constants.AWS_P);
			String assignmentSelect = "select distinct name, id, duedate from Assignment where courseid = ? and shaid = ?";

			try {
				assignmentStatement = conn.prepareStatement(assignmentSelect);
				assignmentStatement.setInt(1, courseid);
				assignmentStatement.setString(2, "prof");
				try {
					rs = assignmentStatement.executeQuery();
					while (rs.next()) {
						Assignment assignment = new Assignment(rs.getString("name"), 0, null,
								Date.valueOf(rs.getString("duedate")), false, false, "", 0, "", 0);
						assignment.setId(rs.getInt("id"));
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


	/**
	 * method for deleting existing course
	 * @param courseId - id of course to be deleted
	 * @return value indicating whether the course was deleted.
	 */
	public int deleteAssignment (int assignmentId) {
		int rowsaffected = 0;
		Connection conn = null;
		PreparedStatement courseStatement = null;
		try {
			Class.forName(Constants.JDBC_DRIVER);
			conn = DriverManager.getConnection(Constants.CONNECTION_STRING, Constants.AWS_USERNAME,
					Constants.AWS_P);

			String coursedelete = "DELETE FROM Assignment where id = ?";
			try {
				courseStatement = conn.prepareStatement(coursedelete);
				courseStatement.setInt(1, assignmentId);
				rowsaffected += courseStatement.executeUpdate();
			} finally {
				if (courseStatement != null)
					courseStatement.close();
			} 
		}catch (ClassNotFoundException e) {
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
		return rowsaffected;
	}


	public List<String> getPreviousSubmission(String hwname, int studentID, int courseID) {
		List<String> listOfID = new ArrayList<String>();
		Connection conn = null;
		PreparedStatement assignmentStatement = null;
		ResultSet rs = null;

		try {
			Class.forName(Constants.JDBC_DRIVER);
			conn = DriverManager.getConnection(Constants.CONNECTION_STRING, Constants.AWS_USERNAME, Constants.AWS_P);
			String assignmentSelect = "select id from Assignment where name=? and studentid=? and courseid=?";
			try {
				assignmentStatement = conn.prepareStatement(assignmentSelect);
				assignmentStatement.setString(1, hwname);
				assignmentStatement.setInt(2, studentID);
				assignmentStatement.setInt(3, courseID);
				try {
					rs = assignmentStatement.executeQuery();
					while (rs.next()) {
						int aid = rs.getInt("id");
						listOfID.add(String.valueOf(aid));
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

			} catch (NullPointerException e) {
				LOGGER.info(e.toString());
			}
		}

		return listOfID;
	}



	public int updateAssignment(int courseID, Assignment assignment) {
		int rowsAffected = 0;
		Connection conn = null;
		PreparedStatement courseStatement = null;
		try {
			Class.forName(Constants.JDBC_DRIVER);
			conn = DriverManager.getConnection(Constants.CONNECTION_STRING, Constants.AWS_USERNAME,
					Constants.AWS_P);

			String courseUpdate = "UPDATE Assignment c "
					+ "SET c.name = ?, c.duedate = ? WHERE c.id = ?";
			try {
				courseStatement = conn.prepareStatement(courseUpdate);
				courseStatement.setString(1, assignment.getName());
				courseStatement.setDate(2, assignment.getDuedate());
				courseStatement.setInt(3, assignment.getId());
				rowsAffected += courseStatement.executeUpdate();
			} finally {
				if (courseStatement != null)
					courseStatement.close();
			}
		}
		catch (NullPointerException e) {
			LOGGER.info(e.toString());
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


	public Map<Integer, String> getInfoforAssignment(int assignmentID) {
		Connection conn = null;
		PreparedStatement assignmentStatement = null;
		Map<Integer, String> hmap = new HashMap<Integer, String>(); 
		ResultSet rs = null;
		try {
			Class.forName(Constants.JDBC_DRIVER);
			conn = DriverManager.getConnection(Constants.CONNECTION_STRING, Constants.AWS_USERNAME, Constants.AWS_P);
			try {
				try {
					assignmentStatement = conn
							.prepareStatement("select * from Assignment where id = ?");
					assignmentStatement.setInt(1, assignmentID);
					rs = assignmentStatement.executeQuery();
					if (rs.next()) {
						String name = rs.getString("name");
						int cid = rs.getInt("courseid");
						hmap.put(1, name);
						hmap.put(2, String.valueOf(cid));
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
		return hmap;
	}
}
