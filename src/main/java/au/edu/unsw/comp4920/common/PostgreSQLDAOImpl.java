package au.edu.unsw.comp4920.common;

import au.edu.unsw.comp4920.objects.*;
import au.edu.unsw.comp4920.exception.*;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

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
		} else {
			try {
				_factory.open();
				conn = _factory.getConnection();

				PreparedStatement stmt = conn.prepareStatement(
						"INSERT INTO users (username, email, password, salt_hash, first_name, middle_name, "
								+ " last_name, token, status_id, budget) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");

				stmt.setString(1, u.getUsername());
				stmt.setString(2, u.getEmail());
				stmt.setString(3, u.getPassword());
				stmt.setString(4, u.getSaltHash());
				stmt.setString(5, u.getFirstName());
				stmt.setString(6, (u.getMiddleName() != null) ? u.getMiddleName() : "");
				stmt.setString(7, u.getLastName());
				stmt.setString(8, u.getToken());
				stmt.setInt(9, 1); // 1 for Inactive, 2 for Active, 3 for
									// Disabled
				stmt.setDouble(10, 0.0); // Default to $0.0

				int n = stmt.executeUpdate();
				if (n != 1) {
					throw new DataSourceException("Did not insert one row into database");
				}

				stmt.close();
			} catch (SQLException | DataSourceException | ServiceLocatorException e) {
				result = false;
				System.err.println(e.getMessage());
			} finally {
				if (conn != null) {
					try {
						_factory.close();
					} catch (SQLException e) {
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
		} else {
			idQuery = "username = '" + userinfo + "'";
		}

		if (password != null) {
			String salt = getSalt(userinfo);
			if (salt != null) {
				String pwd = null;
				pwd = Common.hashPassword(password, salt);
				passwordQuery = " and password = '" + pwd + "';";
			} else {
				passwordQuery = ";";
			}
		} else {
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
				u = new User(rs);
			}

			statement.close();
		} catch (SQLException | ServiceLocatorException e) {
			System.err.println(e.getMessage());
		} finally {
			if (conn != null) {
				try {
					_factory.close();
				} catch (SQLException e) {
					System.err.println(e.getMessage());
				}
			}
		}

		if (u != null) {
			System.out.println("found user");
		}
		return u;
	}

	@Override
	public User getUser(String sid) {
		Connection conn = null;
		User u = null;

		String query = "SELECT U.* FROM users U INNER JOIN session S ON U.id = S.user_id WHERE S.id='" + sid + "'";
		Statement statement;
		try {
			_factory.open();
			conn = _factory.getConnection();

			statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = statement.executeQuery(query);
			if (rs.next()) {
				// pull all information out of results and put it into userDTO
				u = new User();
				u.setUserID(rs.getInt("id"));
				u.setUsername(rs.getString("username"));
				u.setEmail(rs.getString("email"));
				u.setPassword(rs.getString("password"));
				u.setSaltHash(rs.getString("salt_hash"));
				u.setFirstName(rs.getString("first_name"));
				u.setMiddleName(rs.getString("middle_name"));
				u.setLastName(rs.getString("last_name"));
				u.setToken(rs.getString("token"));
				u.setStatusID(rs.getInt("status_id"));
				u.setBudget(rs.getDouble("budget"));
				// System.out.println("UserDTO successfully created.");
			}

			statement.close();
		} catch (SQLException | ServiceLocatorException e) {
			System.err.println(e.getMessage());
		} finally {
			if (conn != null) {
				try {
					_factory.close();
				} catch (SQLException e) {
					System.err.println(e.getMessage());
				}
			}
		}

		return u;
	}

	@Override
	public boolean updateUserNames(User u) {
		boolean result = true;
		Connection conn = null;

		try {
			_factory.open();
			conn = _factory.getConnection();

			String query = "UPDATE users SET first_name=?, middle_name=?, last_name=? " +
						" WHERE id=?;";
			PreparedStatement stmt = conn.prepareStatement(query);

			stmt.setString	(1, u.getFirstName());
			stmt.setString	(2, (u.getMiddleName() != null) ? u.getMiddleName() : "");
			stmt.setString	(3, u.getLastName());
			stmt.setLong	(4, u.getUserID());

			int n = stmt.executeUpdate();
			if (n != 1) {
				throw new DataSourceException("Did not update one row");
			}

			stmt.close();
		} catch (SQLException | DataSourceException | ServiceLocatorException e) {
			result = false;
			System.err.println(e.getMessage());
		} finally {
			if (conn != null) {
				try {
					_factory.close();
				} catch (SQLException e) {
					result = false;
					System.err.println(e.getMessage());
				}
			}
		}
		
		return result;
	}
	

	@Override
	public int addTransaction(Transaction t) {

		Connection conn = null;
		int transactionID;

		try {
			_factory.open();
			conn = _factory.getConnection();

			PreparedStatement stmt = conn.prepareStatement(
					"INSERT INTO transaction (user_id, date, detail, amount, is_income, recur_id) VALUES (?, ?, ?, ?, ?, ?) RETURNING id;");

			stmt.setInt(1, t.getPersonID());
			stmt.setDate(2, t.getDate());
			stmt.setString(3, t.getDetail());
			stmt.setBigDecimal(4, t.getAmount());
			stmt.setBoolean(5, t.isIncome());
			stmt.setInt(6, t.getRecurrence());

			ResultSet rs = stmt.executeQuery();
			rs.next();
			transactionID = rs.getInt("id");
			stmt.close();
		} catch (SQLException | ServiceLocatorException e) {
			transactionID = -1;
			System.err.println(e.getMessage());
		} finally {
			if (conn != null) {
				try {
					_factory.close();
				} catch (SQLException e) {
					transactionID = -1;
					System.err.println(e.getMessage());
				}
			}
		}
		return transactionID;
	}

	private ArrayList<Transaction> getOneOffTransactions(int personID, Date from, Date to, Boolean isIncome) {
		ArrayList<Transaction> transactions = new ArrayList<Transaction>();
		Connection conn = null;

		try {
			_factory.open();
			conn = _factory.getConnection();

			StringBuilder query = new StringBuilder();
			query.append(
					"SELECT id, user_id, date, detail, amount, is_income, recur_id FROM transaction WHERE user_id = ?");

			if (from != null) {
				query.append(" AND date >= \'" + from + "\'");
			}
			if (to != null) {
				query.append(" AND date <= \'" + to + "\'");
			}
			if (isIncome != null) {
				query.append(" AND is_income = " + isIncome);
			}

			query.append(";");

			System.out.println(query);

			PreparedStatement stmt = conn.prepareStatement(query.toString());
			stmt.setInt(1, personID);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Transaction t = new Transaction();

				t.setTransactionID(rs.getInt("id"));
				t.setPersonID(rs.getInt("user_id"));
				t.setDate(rs.getDate("date"));
				t.setDetail(rs.getString("detail"));
				t.setAmount(rs.getBigDecimal("amount"));
				t.setIsIncome(rs.getBoolean("is_income"));

				int isReccurence = rs.getInt("recur_id");

				if (isReccurence == -1) {
					transactions.add(t);
				}
			}

		} catch (SQLException | ServiceLocatorException e) {
			System.err.println(e.getMessage());
		} finally {
			if (conn != null) {
				try {
					_factory.close();
				} catch (SQLException e) {
					System.err.println(e.getMessage());
				}
			}
		}

		return transactions;
	}

	private ArrayList<Transaction> getRecurringTransactions(int personID, Date from, Date to, Boolean isIncome) {
		ArrayList<Transaction> transactions = new ArrayList<Transaction>();
		Connection conn = null;

		ConcurrentHashMap<String, Long> hashMap = new ConcurrentHashMap<String, Long>();

		long WEEKLY = 24 * 60 * 60 * 1000 * 7;
		long FORTNIGHTLY = WEEKLY * 2;
		long MONTHLY = WEEKLY * 4;
		long QUARTERLY = MONTHLY * 3;
		long HALF_YEARLY = MONTHLY * 6;
		long YEARLY = MONTHLY * 12;

		hashMap.put("weekly", WEEKLY);
		hashMap.put("fortnightly", FORTNIGHTLY);
		hashMap.put("monthly", MONTHLY);
		hashMap.put("quarterly", QUARTERLY);
		hashMap.put("half_yearly", HALF_YEARLY);
		hashMap.put("yearly", YEARLY);

		try {
			_factory.open();
			conn = _factory.getConnection();

			StringBuilder query = new StringBuilder();
			query.append("SELECT transaction_id, type, reccur_num, t.date, t.detail, t.amount, t.is_income "
					+ "FROM recurrence r " + "LEFT JOIN transaction t ON t.id = r.transaction_id "
					+ "WHERE t.user_id = " + personID + ";");

			PreparedStatement stmt = conn.prepareStatement(query.toString());
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				int transactionID = rs.getInt("transaction_id");
				Date initialDate = rs.getDate("date");
				String detail = rs.getString("detail");
				BigDecimal amount = rs.getBigDecimal("amount");
				Boolean income = rs.getBoolean("is_income");

				String reccurenceFreq = rs.getString("type");
				int numberPayments = rs.getInt("reccur_num");

				boolean infinitePayments = false;

				if (numberPayments == -1) {
					infinitePayments = true;
				}

				Date iteratorDate = new Date(initialDate.getTime());

				while ((numberPayments > 0 || infinitePayments) && iteratorDate.before(to)) {
					numberPayments--;

					if (iteratorDate.after(from) || iteratorDate.equals(from)) {

						Transaction t = new Transaction();
						t.setTransactionID(transactionID);
						t.setDate(new java.sql.Date(iteratorDate.getTime()));
						t.setDetail(detail);
						t.setAmount(amount);
						t.setIsIncome(income);

						transactions.add(t);
					}

					long addedTime = hashMap.get(reccurenceFreq);
					iteratorDate = new Date(iteratorDate.getTime() + addedTime);
				}

			}
		} catch (SQLException | ServiceLocatorException e) {
			System.err.println("DODOOD" + e.getMessage());
		} finally {
			if (conn != null) {
				try {
					_factory.close();
				} catch (SQLException e) {
					System.err.println(e.getMessage());
				}
			}
		}

		return transactions;
	}

	public List<Transaction> getTransactions(int personID, Date from, Date to, Boolean isIncome) {
		ArrayList<Transaction> masterTransactionList = new ArrayList<Transaction>();
		ArrayList<Transaction> oneOffTransactionList = this.getOneOffTransactions(personID, from, to, isIncome);
		ArrayList<Transaction> recurringTransactionList = this.getRecurringTransactions(personID, from, to, isIncome);

		masterTransactionList.addAll(oneOffTransactionList);
		masterTransactionList.addAll(recurringTransactionList);

		Collections.sort(masterTransactionList, new Comparator<Transaction>() {
	        @Override
	        public int compare(Transaction t1, Transaction t2) {
				return t1.compareTo(t2);
			}
	    });
		
		return masterTransactionList;
	}

	@Override
	public List<Transaction> getTransactionsByDate(int personID, Date from, Date to) {
		return this.getTransactions(personID, from, to, null);
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
			if (n != 1) {
				throw new DataSourceException("Did not insert one row into database");
			}

			stmt.close();

		} catch (SQLException | DataSourceException | ServiceLocatorException e) {
			System.err.println(e.getMessage());
		} finally {
			if (conn != null) {
				try {
					_factory.close();
				} catch (SQLException e) {
					System.err.println(e.getMessage());
				}
			}
		}
	}

	@Override
	public Session getSession(String sessionId) {
		Session s = null;
		String query = "SELECT * FROM Session WHERE id = '" + sessionId + "';";
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

				DateFormat df = new SimpleDateFormat(Constants.DEFAULT_DATE_FORMAT);
				Date sessionDate = (Date) df.parse(s.getLastAccess());
				Date currentDate = new Date();

				long diff = currentDate.getTime() - sessionDate.getTime();
				long days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
				System.out.println("Days: " + days);
				if (days > 7) {
					s = null;
				}
			}
		} catch (SQLException | ServiceLocatorException | ParseException e) {
			System.err.println(e.getMessage());
		} finally {
			if (conn != null) {
				try {
					_factory.close();
				} catch (SQLException e) {
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
	public void deleteSession(String sessionId) {
		String query = "DELETE FROM session WHERE id = '" + sessionId + "';";
		Statement statement;
		Connection conn = null;

		try {
			_factory.open();
			conn = _factory.getConnection();

			statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			statement.executeUpdate(query);
		} catch (SQLException | ServiceLocatorException e) {
			System.err.println(e.getMessage());
		} finally {
			if (conn != null) {
				try {
					_factory.close();
				} catch (SQLException e) {
					System.err.println(e.getMessage());
				}
			}
		}
	}

	@Override
	public String getToken(User u) {
		String query = "SELECT token FROM users WHERE username = '" + u.getUsername() + "';";
		Connection conn = null;
		Statement statement;
		String token = null;
		try {
			_factory.open();
			conn = _factory.getConnection();
			statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = statement.executeQuery(query);
			if (rs.next()) {
				System.out.println(rs.getString(1));
			}
			token = rs.getString(1);
			System.out.println("DAO: getToken(): " + token);
			statement.close();
		} catch (SQLException | ServiceLocatorException e) {
			System.err.println(e.getMessage());
		} finally {
			if (conn != null) {
				try {
					_factory.close();
				} catch (SQLException e) {
					System.err.println(e.getMessage());
				}
			}
		}

		if (token != null) {
			System.out.println("found user");
		}
		return token;
	}

	@Override
	public void setStatus(User u, int status) {
		System.out.println("Inside setStatus: Now setting status.");
		String query = "UPDATE users SET status_id = ? WHERE username = '" + u.getUsername() + "';";
		Connection conn = null;

		try {
			_factory.open();
			conn = _factory.getConnection();
			PreparedStatement stmt = conn.prepareStatement(query.toString());
			stmt.setInt(1, status);
			stmt.execute();
			System.out.println("Success");
		} catch (SQLException | ServiceLocatorException e) {
			System.err.println(e.getMessage());
		} finally {
			if (conn != null) {
				try {
					_factory.close();
				} catch (SQLException e) {
					System.err.println(e.getMessage());
				}
			}
		}
	}

	@Override
	public void setToken(User u, String token) {
		System.out.println("Inside setToken: Now setting token.");
		String query = "UPDATE users SET token = ? WHERE username = '" + u.getUsername() + "';";
		Connection conn = null;

		try {
			_factory.open();
			conn = _factory.getConnection();
			PreparedStatement stmt = conn.prepareStatement(query.toString());
			stmt.setString(1, token);
			stmt.execute();
			System.out.println("Success");
		} catch (SQLException | ServiceLocatorException e) {
			System.err.println(e.getMessage());
		} finally {
			if (conn != null) {
				try {
					_factory.close();
				} catch (SQLException e) {
					System.err.println(e.getMessage());
				}
			}
		}
	}

	@Override
	public Session getUserSession(String userId, String sessionId) {
		Session s = null;
		String query = "SELECT * FROM Session WHERE id = '" + sessionId + "' AND user_id = '" + userId + "';";
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

				DateFormat df = new SimpleDateFormat(Constants.DEFAULT_DATE_FORMAT);
				Date sessionDate = (Date) df.parse(s.getLastAccess());
				Date currentDate = new Date();

				long diff = currentDate.getTime() - sessionDate.getTime();
				long days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
				System.out.println("Days: " + days);
				if (days > 7) {
					s = null;
				}
			}
		} catch (SQLException | ServiceLocatorException | ParseException e) {
			System.err.println(e.getMessage());
		} finally {
			if (conn != null) {
				try {
					_factory.close();
				} catch (SQLException e) {
					System.err.println(e.getMessage());
				}
			}
		}

		return s;
	}

	@Override
	public User getUser(String userinfo, String firstName, String lastName) {
		User u = getUser(userinfo, null);
		if (u == null) return null;
		if (!u.getFirstName().equalsIgnoreCase(firstName) ||
				!u.getLastName().equalsIgnoreCase(lastName)) {
			return null;
		}
		
		return u;
	}

	@Override
	public void setPassword(User u, String hashedPassword) {
		System.out.println("Inside setPassword: Now resetting password.");
		String query = "UPDATE users SET password = ? WHERE username = '" + u.getUsername() + "';";
		Connection conn = null;

		try {
			_factory.open();
			conn = _factory.getConnection();
			PreparedStatement stmt = conn.prepareStatement(query.toString());
			stmt.setString(1, hashedPassword);
			stmt.execute();
			System.out.println("Success");
		} catch (SQLException | ServiceLocatorException e) {
			System.err.println(e.getMessage());
		} finally {
			if (conn != null) {
				try {
					_factory.close();
				} catch (SQLException e) {
					System.err.println(e.getMessage());
				}
			}
		}
	}

	@Override
	public String getSalt(String userinfo) {
		String query = "SELECT salt_hash FROM users WHERE username = '" + userinfo + "';";
		if (userinfo.contains("@")) {
			query = "SELECT salt_hash FROM users WHERE email = '" + userinfo + "';";
		}
		Connection conn = null;
		Statement statement;
		String salt = null;
		try {
			_factory.open();
			conn = _factory.getConnection();
			statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = statement.executeQuery(query);
			if (rs.next()) {
				System.out.println(rs.getString(1));
			}
			salt = rs.getString(1);
			System.out.println("DAO: getSalt(): " + salt);
			statement.close();
		} catch (SQLException | ServiceLocatorException e) {
			System.err.println(e.getMessage());
		} finally {
			if (conn != null) {
				try {
					_factory.close();
				} catch (SQLException e) {
					System.err.println(e.getMessage());
				}
			}
		}
		if (salt != null) {
			System.out.println("in DAO getSalt(): found user, salt is " + salt);
		}
		return salt;
	}

	@Override
	public boolean addRecurring(Recurrence r) {
		boolean result = true;
		Connection conn = null;

		try {
			_factory.open();
			conn = _factory.getConnection();

			PreparedStatement stmt = conn
					.prepareStatement("INSERT INTO recurrence (transaction_id, type, reccur_num) VALUES (?, ?, ?);");

			stmt.setInt(1, r.getTransactionID());
			stmt.setString(2, r.getRecurrenceFreq());
			stmt.setInt(3, r.getRecurrenceNumber());

			int n = stmt.executeUpdate();
			if (n != 1) {
				throw new DataSourceException("Did not insert one row into database");
			}

		} catch (SQLException | ServiceLocatorException | DataSourceException e) {
			result = false;
			System.err.println(e.getMessage());
		} finally {
			if (conn != null) {
				try {
					_factory.close();
				} catch (SQLException e) {
					result = false;
					System.err.println(e.getMessage());
				}
			}
		}
		return result;
	}
}
