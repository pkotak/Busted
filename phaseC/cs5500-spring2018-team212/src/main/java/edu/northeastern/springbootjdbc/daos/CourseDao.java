package edu.northeastern.springbootjdbc.daos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.mysql.jdbc.Statement;

import edu.northeastern.cs5500.Constants;
import edu.northeastern.springbootjdbc.models.Course;

/**
 * Course Database Interaction Class
 * @author abhiruchi
 *
 */
public class CourseDao {
	private static CourseDao instance = null;
	static final Logger LOGGER = Logger.getLogger(CourseDao.class.getName());

	public static CourseDao getInstance() {
		if (instance == null)
			return new CourseDao();
		else
			return instance;
	}

	private CourseDao() {
	}

	/**
	 * method for adding new course
	 * @param course - object of the Course to be created
	 * @return - value indicating whether the course was created.
	 */
	public int createCourse(Course course) {
		Connection conn = null;
		int courseID = 0;
		ResultSet rs = null;
		PreparedStatement courseStatement = null;
		try {
			Class.forName(Constants.JDBC_DRIVER);
			conn = DriverManager.getConnection(Constants.CONNECTION_STRING, Constants.AWS_USERNAME,
					Constants.AWS_P);

			String courseInsert = "insert INTO Course (id, name, semester, code) " + "VALUES (?,?,?,?)";
			try {
				courseStatement = conn.prepareStatement
						(courseInsert, Statement.RETURN_GENERATED_KEYS);
				courseStatement.setInt(1, course.getId());
				courseStatement.setString(2, course.getName());
				courseStatement.setString(3, course.getSemester());
				courseStatement.setString(4, course.getCode());
				courseStatement.executeUpdate();
				try {
					rs = courseStatement.getGeneratedKeys();
					rs.next();
					courseID = rs.getInt(1);
				} finally {
					if (rs != null)
						rs.close();
				}
				
			} finally {
				if (courseStatement != null)
					courseStatement.close();
				
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
		return courseID;
	}

	/**
	 * method for updating existing course
	 * @param courseID - id of course to be updated
	 * @param course - updated value of the course.
	 * @return - value indicating whether the course was updated.
	 */
	public int updateCourse(int courseID, Course course) {
		int rowsAffected = 0;
		Connection conn = null;
		PreparedStatement courseStatement = null;
		try {
			Class.forName(Constants.JDBC_DRIVER);
			conn = DriverManager.getConnection(Constants.CONNECTION_STRING, Constants.AWS_USERNAME,
					Constants.AWS_P);

			String courseUpdate = "UPDATE Course c "
			+ "SET c.id = ?, c.name = ?, c.semester = ?, c.code = ?" + "WHERE c.id = ?";
			try {
				courseStatement = conn.prepareStatement(courseUpdate);
				courseStatement.setInt(1, courseID);
				courseStatement.setString(2, course.getName());
				courseStatement.setString(3, course.getSemester());
				courseStatement.setString(4, course.getCode());
				courseStatement.setInt(5, courseID);
				rowsAffected += courseStatement.executeUpdate();
			} finally {
				if (courseStatement != null)
					courseStatement.close();
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
		return rowsAffected;
	}

	/**
	 * method for deleting existing course
	 * @param courseId - id of course to be deleted
	 * @return value indicating whether the course was deleted.
	 */
	public int deleteCourse(int courseId) {
		int rowsaffected = 0;
		Connection conn = null;
		PreparedStatement courseStatement = null;
		try {
			Class.forName(Constants.JDBC_DRIVER);
			conn = DriverManager.getConnection(Constants.CONNECTION_STRING, Constants.AWS_USERNAME,
					Constants.AWS_P);

			String coursedelete = "DELETE FROM Course where id = ?";
			try {
				courseStatement = conn.prepareStatement(coursedelete);
				courseStatement.setInt(1, courseId);
				rowsaffected += courseStatement.executeUpdate();
			} finally {
				if (courseStatement != null)
					courseStatement.close();
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

		return rowsaffected;
	}

	/**
	 *  method to find all the courses in the database.
	 * @return list of the courses.
	 */
	public List<Course> findAllCourses() {
		List<Course> courseList = new ArrayList<Course>();
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet results = null;
		try {
			Class.forName(Constants.JDBC_DRIVER);
			conn = DriverManager.getConnection(Constants.CONNECTION_STRING, Constants.AWS_USERNAME,
					Constants.AWS_P);

			String sql = "select * from Course";
			try {
				statement = conn.prepareStatement(sql);
				try {
					results = statement.executeQuery();
					while (results.next()) {
						int id = Integer.parseInt(results.getString("id"));
						String name = results.getString("name");
						String semester = results.getString("semester");
						String code = results.getString("code");
						
						Course course = new Course();
						course.setId(id);
						course.setName(name);
						course.setSemester(semester);
						course.setCode(code);
						
						courseList.add(course);	
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
		return courseList;
	}

	/**
	 * @param courseid - id of the course to be found.
	 * @return the course object associated with the given id.
	 */
	public Course findCoursebyID(int courseid) {
		Course course = null;
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet results = null;
		try {
			Class.forName(Constants.JDBC_DRIVER);
			conn = DriverManager.getConnection(Constants.CONNECTION_STRING, Constants.AWS_USERNAME,
					Constants.AWS_P);

			String sql = "select * from Course WHERE id = ?";
			try {
				statement = conn.prepareStatement(sql);
				statement.setInt(1, courseid);
				try {
					results = statement.executeQuery();

					if (results.next()) {
						int id = Integer.parseInt(results.getString("id"));
						String name = results.getString("name");
						String semester = results.getString("semester");
						String code = results.getString("code");
						course = new Course();
						course.setId(id);
						course.setName(name);
						course.setSemester(semester);
						course.setCode(code);
			
					}
				} finally {
					if (results != null) {
						results.close();
					}
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
				if(conn != null)
					conn.close();
			} catch (SQLException e) {
				LOGGER.info(e.toString());
			}
		}
		return course;
	}

	/**
	 * method for extracting all ids for given course code
	 * 
	 * @param courseid - code of the course to be found.
	 * @return the id of all the courses associated with the given code.
	 */
	public List<Integer> getCourseIDbyCode(String coursecode) {
		List<Integer> courseIDList = new ArrayList<Integer>();
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet results = null;
		try {
			Class.forName(Constants.JDBC_DRIVER);
			conn = DriverManager.getConnection(Constants.CONNECTION_STRING, Constants.AWS_USERNAME,
					Constants.AWS_P);

			String sql = "select id from Course WHERE code = ?";
			try {
				statement = conn.prepareStatement(sql);
				statement.setString(1, coursecode);
				try {
					results = statement.executeQuery();

					while (results.next()) {
						int id = results.getInt("id");
						courseIDList.add(id);				
					}
				} finally {
					if (results != null) {
						results.close();
					}
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
				if(conn != null)
					conn.close();
			} catch (SQLException e) {
				LOGGER.info(e.toString());
			}
		}
		return courseIDList;
	}

	/**
	 * method for getting id for given course code and its semesters
	 * 
	 * @param code
	 * @param semester
	 * @return course id
	 */
	public int getCourseID(String code, String semester) {
		int courseid = 0;
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet results = null;
		try {
			Class.forName(Constants.JDBC_DRIVER);
			conn = DriverManager.getConnection(Constants.CONNECTION_STRING, Constants.AWS_USERNAME,
					Constants.AWS_P);

			String sql = "select id from Course WHERE code = ? and semester = ?";
			try {
				statement = conn.prepareStatement(sql);
				statement.setString(1, code);
				statement.setString(2, semester);
				try {
					results = statement.executeQuery();

					if (results.next()) {
						courseid = results.getInt("id");
			
					}
				} finally {
					if (results != null) {
						results.close();
					}
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
				if(conn != null)
					conn.close();
			} catch (SQLException e) {
				LOGGER.info(e.toString());
			}
		}
		return courseid;
	}
}