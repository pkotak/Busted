
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
import edu.northeastern.springbootjdbc.models.Person;

/**
 * Person Database Interaction Class
 * @author Paarth
 *
 */
public class PersonDao {
	private static PersonDao instance = null;
	private static final Logger LOGGER = Logger.getLogger(PersonDao.class.getName());

	public static PersonDao getInstance() {
		if (instance == null)
			return new PersonDao();
		else
			return instance;
	}

	private PersonDao() {
	}

	/**
	 * Inserts a person into the database
	 * 
	 * @param person
	 *            Person object
	 * @return Number of rows affected in the database
	 */
	public int createPerson(Person person) {

		int rowsAffected = 0;
		Connection conn = null;
		PreparedStatement personStatement = null;
		try {
			Class.forName(Constants.JDBC_DRIVER);
			conn = DriverManager.getConnection(Constants.CONNECTION_STRING, Constants.AWS_USERNAME,
					Constants.AWS_P);

			String personInsert = "insert INTO Person (id, firstname, lastname, email, password, phone, type) "
					+ "VALUES (?,?,?,?,?,?,?)";

			try {
				personStatement = conn.prepareStatement(personInsert);
				personStatement.setInt(1, person.getId());
				personStatement.setString(2, person.getFirstName());
				personStatement.setString(3, person.getLastName());
				personStatement.setString(4, person.getEmail());
				personStatement.setString(5, person.getPassword());
				personStatement.setString(6, person.getPhone());
				personStatement.setString(7, person.getType());
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

	/**
	 * Updates Person details in the database
	 * 
	 * @param personId
	 *            the specific person Id
	 * @param person
	 *            The person object to be inserted
	 * @return Number of rows affected in the database
	 */
	public int updatePerson(int personId, Person person) {
		int rowsAffected = 0;
		Connection conn = null;
		PreparedStatement personStatement = null;
		try {
			Class.forName(Constants.JDBC_DRIVER);
			conn = DriverManager.getConnection(Constants.CONNECTION_STRING, Constants.AWS_USERNAME,
					Constants.AWS_P);
			String personUpdate = "UPDATE Person p "
					+ "SET p.id = ?, p.firstname = ?, p.lastname = ?, p.email = ?,p.password = ?, p.phone = ?, p.type = ? "
					+ "WHERE p.id = ?";
			try {
				personStatement = conn.prepareStatement(personUpdate);
				personStatement.setInt(1, personId);
				personStatement.setString(2, person.getFirstName());
				personStatement.setString(3, person.getLastName());
				personStatement.setString(4, person.getEmail());
				personStatement.setString(5, person.getPassword());
				personStatement.setString(6, person.getPhone());
				personStatement.setString(7, person.getType());
				personStatement.setInt(8, personId);

				rowsAffected += personStatement.executeUpdate();
			} finally {
				if (personStatement != null)
					personStatement.close();
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

	/**
	 * Delete a person from the database
	 * 
	 * @param personId
	 *            the unique identifier of the person
	 * @return Number of rows affected in the database
	 */
	public int deletePerson(String username) {
		int rowsAffected = 0;
		Connection conn = null;
		PreparedStatement personStatement = null;
		try {
			Class.forName(Constants.JDBC_DRIVER);
			conn = DriverManager.getConnection(Constants.CONNECTION_STRING, Constants.AWS_USERNAME,
					Constants.AWS_P);
			Person p = findPersonByUsername(username);
			int personId = p.getId();
			String personDelete = "DELETE FROM Person where id = ?";
			try {
				personStatement = conn.prepareStatement(personDelete);
				personStatement.setInt(1, personId);
				rowsAffected += personStatement.executeUpdate();
			} finally {
				if (personStatement != null)
					personStatement.close();
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

	
	/**
	 * Delete a person from the database
	 * 
	 * @param personId
	 *            the unique identifier of the person
	 * @return Number of rows affected in the database
	 */
	public int deletePerson(int personId) {
		int rowsAffected = 0;
		Connection conn = null;
		PreparedStatement personStatement = null;
		try {
			Class.forName(Constants.JDBC_DRIVER);
			conn = DriverManager.getConnection(Constants.CONNECTION_STRING, Constants.AWS_USERNAME,
					Constants.AWS_P);

			String personDelete = "DELETE FROM Person where id = ?";
			try {
				personStatement = conn.prepareStatement(personDelete);
				personStatement.setInt(1, personId);
				rowsAffected += personStatement.executeUpdate();
			} finally {
				if (personStatement != null)
					personStatement.close();
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
	
	
	/**
	 * Find all the people
	 * 
	 * @return the List of all the People and their details
	 */
	public List<Person> findAllPeople() {
		List<Person> personList = new ArrayList<Person>();
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet results = null;
		try {
			Class.forName(Constants.JDBC_DRIVER);
			conn = DriverManager.getConnection(Constants.CONNECTION_STRING, Constants.AWS_USERNAME,
					Constants.AWS_P);

			String sql = "select * from Person";
			try {
				statement = conn.prepareStatement(sql);
				try {
					results = statement.executeQuery();
					while (results.next()) {
						int id = Integer.parseInt(results.getString("id"));
						String firstName = results.getString(Constants.FIRSTNAME);
						String lastName = results.getString(Constants.LASTNAME);
						String email = results.getString("email");
						String password = results.getString(Constants.AWS_P);
						String phone = results.getString(Constants.PHONE);
						String type = results.getString("type");

						Person person = new Person();
						person.setId(id);
						person.setFirstName(firstName);
						person.setLastName(lastName);
						person.setEmail(email);
						person.setPassword(password);
						person.setPhone(phone);
						person.setType(type);

						personList.add(person);
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
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				LOGGER.info(e.toString());
			}
		}
		return personList;
	}

	/**
	 * Find a person by email
	 * 
	 * @param username
	 *            the user name of the person to be found
	 * @return Person object containing his/her details
	 */
	public Person findPersonByUsername(String username) {

		Person person = null;
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet results = null;
		try {
			Class.forName(Constants.JDBC_DRIVER);
			conn = DriverManager.getConnection(Constants.CONNECTION_STRING, Constants.AWS_USERNAME,
					Constants.AWS_P);

			String sql = "select * from Person where email = ?";
			try {
				statement = conn.prepareStatement(sql);
				statement.setString(1, username);
				try {
					results = statement.executeQuery();

					if (results.next()) {
						int id = Integer.parseInt(results.getString("id"));
						String firstName = results.getString("firstname");
						String lastName = results.getString("lastname");
						String password = results.getString("password");
						String phone = results.getString("phone");
						String type = results.getString("type");

						person = new Person();
						person.setId(id);
						person.setFirstName(firstName);
						person.setLastName(lastName);
						person.setPassword(password);
						person.setEmail(username);
						person.setPhone(phone);
						person.setType(type);
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
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				LOGGER.info(e.toString());
			}
		}
		return person;

	}

	/**
	 * Find a person by id
	 * @param personId - the id of the person to be found
	 * @return Person object containing his/her details
	 */
	public Person findPersonById(int personId) {

		Person person = null;
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet results = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(Constants.CONNECTION_STRING, Constants.AWS_USERNAME,
					Constants.AWS_P);

			String sql = "select * from Person where id = ?";
			try {
				statement = conn.prepareStatement(sql);
				statement.setInt(1, personId);
				try {
					results = statement.executeQuery();

					if (results.next()) {
						int id = Integer.parseInt(results.getString("id"));
						String firstName = results.getString("firstname");
						String lastName = results.getString("lastname");
						String email = results.getString("email");
						String password = results.getString("password");
						String phone = results.getString("phone");
						String type = results.getString("type");

						person = new Person();
						person.setId(id);
						person.setFirstName(firstName);
						person.setLastName(lastName);
						person.setPassword(password);
						person.setEmail(email);
						person.setPhone(phone);
						person.setType(type);

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
		return person;

	}

}
