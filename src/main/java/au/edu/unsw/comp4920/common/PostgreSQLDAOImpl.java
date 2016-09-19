package au.edu.unsw.comp4920.common;

import au.edu.unsw.comp4920.objects.*;
import au.edu.unsw.comp4920.exception.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.Date;

import au.edu.unsw.comp4920.common.DBConnectionFactory;

public class PostgreSQLDAOImpl implements CommonDAO {
	private DBConnectionFactory _factory;
	static Logger logger = Logger.getLogger(DBConnectionFactory.class.getName());

	public PostgreSQLDAOImpl() throws ServiceLocatorException, SQLException {
		_factory = new DBConnectionFactory();
		logger.info("Connection opened");
	}

	@Override
	public boolean createUser(User u) {
		boolean result = true;
		Connection conn = null;
		
		// Check if the user name or email already exist
		if (getUser(u.getUsername(), null) != null || getUser(u.getEmail(), null) != null) {
			System.out.println("Not creating user because username and/or email already exists!");
			result = false;
		} 
		else {
			try {
				_factory.open();
				conn = _factory.getConnection();
				
				PreparedStatement stmt = conn
						.prepareStatement("INSERT INTO Users (username, email, password, salt_hash, first_name, middle_name, "
								+ " last_name, token, status_id, budget) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
	
				stmt.setString(1, u.getUsername());
				stmt.setString(2, u.getEmail());
				stmt.setString(3, u.getPassword());
				stmt.setString(4, "lol"); //TODO change this later - salt_hash
				stmt.setString(5, u.getFirstName());
				stmt.setString(6, (u.getMiddleName() != null)?u.getMiddleName() : "");
				stmt.setString(7, u.getLastName());
				stmt.setString(8, "lol"); //TODO change this later
				stmt.setInt(9, 1); //1 for Inactive, 2 for Active, 3 for Disabled
				stmt.setDouble(10, 0.0); //Default to $0.0
	
				int n = stmt.executeUpdate();
				if (n != 1){
					throw new DataSourceException("Did not insert one row into database");
				}
				
				stmt.close();
			} 
			catch (SQLException | DataSourceException | ServiceLocatorException e) {
				result = false;
				System.err.println(e.getMessage());
			} 
			finally {
				if (conn != null) {
					try {
						_factory.close();
					} 
					catch (SQLException e) {
						result = false;
						System.err.println(e.getMessage());
					}
				}
			}
		}
		
		return result;
	}

	@Override
	public User getUser(String userinfo, String password) {
		Connection conn = null;
		User u = null;
		String idQuery;
		String passwordQuery;

		if (userinfo.contains("@")) {
			idQuery = "email = '" + userinfo + "'";
		} 
		else {
			idQuery = "username = '" + userinfo + "'";
		}

		if (password != null) {
			passwordQuery = " and password = '" + password + "';";
		} 
		else {
			passwordQuery = ";";
		}

		String query = "SELECT * FROM users WHERE " + idQuery + passwordQuery;
		Statement statement;
		try {
			_factory.open();
			conn = _factory.getConnection();
			
			statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = statement.executeQuery(query);
			if (rs.next()) {
				// pull all information out of results and put it into userDTO
				u = new User();
				u.setPersonID(rs.getInt("id"));
				u.setUsername(rs.getString("username"));
				u.setPassword(rs.getString("email"));
				u.setSalt_hash(rs.getString("salt_hash"));
				u.setFirstName(rs.getString("first_name"));
				u.setMiddleName(rs.getString("middle_name"));
				u.setLastName(rs.getString("last_name"));
				u.setToken(rs.getString("token"));
				u.setStatus_id(rs.getInt("status_id"));
				u.setBudget(rs.getDouble("budget"));
				//System.out.println("UserDTO successfully created.");
			}
			
			statement.close();
		} 
		catch (SQLException | ServiceLocatorException e) {
			System.err.println(e.getMessage());
		} 
		finally {
			if (conn != null) {
				try {
					_factory.close();
				} 
				catch (SQLException e) {
					System.err.println(e.getMessage());
				}
			}
		}
		
		if(u != null) {
			System.out.println("found user");
		}
		return u;
	}

	@Override
	public boolean addTransaction(Transaction t) {
		boolean result = true;
		Connection conn = null;
		
		try {
			_factory.open();
			conn = _factory.getConnection();

			PreparedStatement stmt = conn.prepareStatement(
					"INSERT INTO Transaction (user_id, date, detail, amount, is_income) VALUES (?, ?, ?, ?, ?);");

			stmt.setInt(1, t.getPersonID());
			stmt.setDate(2, t.getDate());
			stmt.setString(3, t.getDetail());
			stmt.setDouble(4, t.getAmount());
			stmt.setBoolean(5, t.isIncome());

			int n = stmt.executeUpdate();
			if (n != 1){
				throw new DataSourceException("Did not insert one row into database");
			}
			
			stmt.close();
		} 
		catch (SQLException | DataSourceException | ServiceLocatorException e) {
			result = false;
			System.err.println(e.getMessage());
		} 
		finally {
			if (conn != null) {
				try {
					_factory.close();
				} 
				catch (SQLException e) {
					result = false;
					System.err.println(e.getMessage());
				}
			}
		}
		
		return result;
	}

	public List<Transaction> getTransactions(int personID, Date from, Date to, Boolean isIncome) {
		ArrayList<Transaction> transactions = new ArrayList<Transaction>();
		Connection conn = null;
		
		try {
			_factory.open();
			conn = _factory.getConnection();
			
			StringBuilder query = new StringBuilder();
			query.append("SELECT id, user_id, date, detail, amount, is_income FROM transaction WHERE user_id = ?");

			if (from != null) {
				query.append(" AND date >= " + from);
			}
			if (to != null) {
				query.append(" AND date <= " + to);
			}
			if (isIncome != null) {
				query.append(" AND is_income = " + isIncome);
			}

			query.append(";");
			
			PreparedStatement stmt = conn.prepareStatement(query.toString());
			stmt.setInt(1, personID);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Transaction t = new Transaction();

				t.setTransactionID(rs.getInt("id"));
				t.setPersonID(rs.getInt("user_id"));
				t.setDate(rs.getDate("date"));
				t.setDetail(rs.getString("detail"));
				t.setAmount(rs.getDouble("amount"));
				t.setIsIncome(rs.getBoolean("is_income"));

				transactions.add(t);
			}
		} 
		catch (SQLException | ServiceLocatorException e) {
			System.err.println(e.getMessage());
		} 
		finally {
			if (conn != null) {
				try {
					_factory.close();
				} 
				catch (SQLException e) {
					System.err.println(e.getMessage());
				}
			}
		}

		return transactions;
	}

	@Override
	public List<Transaction> getAllTransactions(int personID) {
		return this.getTransactions(personID, null, null, null);
	}

	@Override
	public List<Transaction> getAllIncomes(int personID) {
		return this.getTransactions(personID, null, null, true);
	}

	@Override
	public List<Transaction> getAllExpenses(int personID) {
		return this.getTransactions(personID, null, null, false);
	}

	@Override
	public void createSession(Session session) {
		Connection conn = null;
		
		try {
			_factory.open();
			conn = _factory.getConnection();

			PreparedStatement stmt = conn
					.prepareStatement("INSERT INTO Session (id, user_id, last_access) VALUES (?, ?, ?);");

			stmt.setString(1, session.getSessionId());
			stmt.setInt(2, session.getUserId());
			stmt.setString(3, session.getLastAccess());

			int n = stmt.executeUpdate();
			if (n != 1){
				throw new DataSourceException("Did not insert one row into database");
			}
			
			stmt.close();

		} 
		catch (SQLException | DataSourceException | ServiceLocatorException e) {
			System.err.println(e.getMessage());
		} 
		finally {
			if (conn != null) {
				try {
					_factory.close();
				} 
				catch (SQLException e) {
					System.err.println(e.getMessage());
				}
			}
		}
	}

	@Override
	public Session getSession(String sessionId) {
		Session s = null;
		String query = "SELECT * FROM user WHERE id = '" + sessionId + "';";
		Statement statement;
		Connection conn = null;
		
		try {
			_factory.open();
			conn = _factory.getConnection();
			
			statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = statement.executeQuery(query);
			if (rs.next()) {
				s = new Session();
				s.setSessionId(rs.getString("id"));
				s.setUserId(rs.getInt("user_id"));
				s.setLastAccess(rs.getString("last_access"));
			}
		} 
		catch (SQLException | ServiceLocatorException e) {
			System.err.println(e.getMessage());
		} 
		finally {
			if (conn != null) {
				try {
					_factory.close();
				} 
				catch (SQLException e) {
					System.err.println(e.getMessage());
				}
			}
		}
		
		return s;
	}

	@Override
	public User getUserDetails(String username) {
		return getUser(username, null);
	}

	@Override
	public User getUserDetails(int uid) {
		// TODO Auto-generated method stub
		return null;
	}
}
