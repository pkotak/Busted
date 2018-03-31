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
import edu.northeastern.springbootjdbc.models.Course;
import edu.northeastern.springbootjdbc.models.Person;
import edu.northeastern.springbootjdbc.models.RoleType;

/**
 * CourseRole Database Interaction Class
 * @author abhiruchi
 *
 */
public class CourseRoleDao {

	private static CourseRoleDao instance = null;
	private static final Logger LOGGER = Logger.getLogger(CourseRoleDao.class.getName());
	private static PersonDao pdao = PersonDao.getInstance();
	private static CourseDao cdao = CourseDao.getInstance();

	public static CourseRoleDao getInstance() {
		if (instance == null)
			return new CourseRoleDao();
		else
			return instance;
	}

	private CourseRoleDao() {
	}

	/**
	 * method for finding the course/s for a person.
	 * 
	 * @param person - object representation of the given person.
	 * @return the list of courses for the given person.
	 */
	public List<Course> getCourseForPerson(int personid) {
		List<Course> clist = new ArrayList<Course>();
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet results = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(Constants.CONNECTION_STRING, Constants.AWS_USERNAME,
					Constants.AWS_P);

			String sql = "SELECT courseid FROM CourseRole as cr where cr.personid = ?";
			try {
				statement = conn.prepareStatement(sql);
				statement.setInt(1, personid);
				try {
					results = statement.executeQuery();
					while (results.next()) {
						clist.add(cdao.findCoursebyID(results.getInt("courseid")));
					}
				} finally {
					if (results != null)
						results.close();
				}

			}finally {
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


		return clist;
	}
	
	/**
	 * method to find Students/TAs/Professors for a course.
	 * 
	 * @param courseID - id of the given course
	 * @param role - the associated role with the course
	 * @return - the persons having the given role for the given course.
	 */
	public List<Person> getPersonsForCourse(int courseID, String role) {
		List <Person> personList  = new ArrayList<Person>();
		Connection conn = null;
		java.sql.PreparedStatement statement = null;
		ResultSet results = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(Constants.CONNECTION_STRING, Constants.AWS_USERNAME,
					Constants.AWS_P);

			String sqlquery = "SELECT personid FROM CourseRole as cr where cr.courseid = ? and cr.roletype = ?";
			try {
				statement = conn.prepareStatement(sqlquery);
				statement.setInt(1, courseID);
				statement.setString(2, role);
				try {
					results = statement.executeQuery();

					while (results.next()) {
						personList.add(pdao.findPersonById(results.getInt("personid")));
					}
					
				} finally {
					if (results != null)
						results.close();
				}

			}finally {
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
		return personList;
	}

	/**
	 * method for Professor to add a TA for a course.
	 * 
	 * @param courseID - id for the given course. 
	 * @param idForTA - id for the given TA.
	 * @return
	 */
	public int addTA(int courseID, int idForTA) {
		int rowsAffected = 0;
		Connection conn = null;
		PreparedStatement personStatement = null;
		try {
			Class.forName(Constants.JDBC_DRIVER);
			conn = DriverManager.getConnection(Constants.CONNECTION_STRING, Constants.AWS_USERNAME,
					Constants.AWS_P);

			String personInsert = "insert INTO CourseRole (personid, roletype, courseid) VALUES (?,?,?)";

			try {
				personStatement = conn.prepareStatement(personInsert);
				personStatement.setInt(1, idForTA);
				personStatement.setString(2, RoleType.TA.name());
				personStatement.setInt(3, courseID);
				rowsAffected += personStatement.executeUpdate();
			} finally {
				if (personStatement != null) {
					personStatement.close();
				}
			}

		} catch (ClassNotFoundException e) {
			LOGGER.info(e.toString());
		} catch (SQLException e) {
			LOGGER.info(e.toString());
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				LOGGER.info(e.toString());
			}
		}
		return rowsAffected;
	}
	
	
	public int joinCourseAsStudent(int courseID, int personID) {
		int rowsAffected = 0;
		Connection conn = null;
		PreparedStatement personStatement = null;
		try {
			Class.forName(Constants.JDBC_DRIVER);
			conn = DriverManager.getConnection(Constants.CONNECTION_STRING, Constants.AWS_USERNAME,
					Constants.AWS_P);

			String personInsert = "insert INTO CourseRole (personid, roletype, courseid) "
					+ "VALUES (?,?,?)";

			try {
				personStatement = conn.prepareStatement(personInsert);
				personStatement.setInt(1, personID);
				personStatement.setString(2, RoleType.STUDENT.name());
				personStatement.setInt(3, courseID);
				rowsAffected += personStatement.executeUpdate();
			} finally {
				if (personStatement != null) {
					personStatement.close();
				}
			}

		} catch (ClassNotFoundException e) {
			LOGGER.info(e.toString());
		} catch (SQLException e) {
			LOGGER.info(e.toString());
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				LOGGER.info(e.toString());
			}
		}
		return rowsAffected;
	}

	public int mapProfessor(int courseID, int personID) {
		int rowsAffected = 0;
		Connection conn = null;
		PreparedStatement personStatement = null;
		try {
			Class.forName(Constants.JDBC_DRIVER);
			conn = DriverManager.getConnection(Constants.CONNECTION_STRING, Constants.AWS_USERNAME,
					Constants.AWS_P);

			String personInsert = "insert INTO CourseRole (personid, roletype, courseid) "
					+ "VALUES (?,?,?)";

			try {
				personStatement = conn.prepareStatement(personInsert);
				personStatement.setInt(1, personID);
				personStatement.setString(2, RoleType.PROFESSOR.name());
				personStatement.setInt(3, courseID);
				rowsAffected += personStatement.executeUpdate();
			} finally {
				if (personStatement != null) {
					personStatement.close();
				}
			}

		} catch (ClassNotFoundException e) {
			LOGGER.info(e.toString());
		} catch (SQLException e) {
			LOGGER.info(e.toString());
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				LOGGER.info(e.toString());
			}
		}
		return rowsAffected;
	}
	
	public int unmapProfessor(int courseID, int personID) {
		int rowsAffected = 0;
		Connection conn = null;
		PreparedStatement coursestmt = null;
		try {
			Class.forName(Constants.JDBC_DRIVER);
			conn = DriverManager.getConnection(Constants.CONNECTION_STRING, Constants.AWS_USERNAME,
					Constants.AWS_P);

			String rowdelete = "delete FROM CourseRole where courseid = ? and personid = ?";

			try {
				coursestmt = conn.prepareStatement(rowdelete);
				coursestmt.setInt(1, courseID);
				coursestmt.setInt(2, personID);
				rowsAffected += coursestmt.executeUpdate();
			} finally {
				if (coursestmt != null) {
					coursestmt.close();
				}
			}

		} catch (ClassNotFoundException e) {
			LOGGER.info(e.toString());
		} catch (SQLException e) {
			LOGGER.info(e.toString());
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				LOGGER.info(e.toString());
			}
		}
		return rowsAffected;
	}
	
}
