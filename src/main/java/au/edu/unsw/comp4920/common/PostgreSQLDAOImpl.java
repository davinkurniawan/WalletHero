package au.edu.unsw.comp4920.common;

import au.edu.unsw.comp4920.objects.*;
import au.edu.unsw.comp4920.exception.*;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
		if (getUserDetails(u.getUsername()) != null || getUserDetails(u.getEmail()) != null) {
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
				stmt.setInt(9, 1); // 1 for Inactive, 2 for Active, 3 for Disabled
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
		String pwd = null;
		String idQuery = "";
		String passwordQuery = "";

		if (userinfo.contains("@")) {
			idQuery = "email = ?";
		} else {
			idQuery = "username = ?";
		}

		if (password != null) {
			String salt = getSalt(userinfo);

			if (salt != null) {
				pwd = Common.hashPassword(password, salt);
				passwordQuery = " and password = ?;";
			} else {
				passwordQuery = ";";
			}
		} else {
			passwordQuery = ";";
		}

		try {
			_factory.open();
			conn = _factory.getConnection();

			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE " + idQuery + passwordQuery);

			stmt.setString(1, userinfo);
			if (pwd != null) {
				stmt.setString(2, pwd);
			}

			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				// pull all information out of results and put it into userDTO
				u = new User(rs);
			}

			stmt.close();
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

		try {
			_factory.open();
			conn = _factory.getConnection();

			PreparedStatement stmt = conn.prepareStatement(
					"SELECT U.* FROM users U INNER JOIN session S ON U.id = S.user_id WHERE S.id = ?");

			stmt.setString(1, sid);

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
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

			stmt.close();
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

			String query = "UPDATE users SET first_name = ?, middle_name = ?, last_name = ? WHERE id = ?;";
			PreparedStatement stmt = conn.prepareStatement(query);

			stmt.setString(1, u.getFirstName());
			stmt.setString(2, (u.getMiddleName() != null) ? u.getMiddleName() : "");
			stmt.setString(3, u.getLastName());
			stmt.setLong(4, u.getUserID());

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
	public boolean updateUserEmail(User u) {
		boolean result = true;
		Connection conn = null;

		try {
			_factory.open();
			conn = _factory.getConnection();

			String query = "UPDATE users SET email = ?, token = ? WHERE id = ?;";
			PreparedStatement stmt = conn.prepareStatement(query);

			stmt.setString	(1, u.getEmail());
			stmt.setString	(2, u.getToken());
			stmt.setLong	(3, u.getUserID());

			int n = stmt.executeUpdate();
			
			if (n != 1) {
				throw new DataSourceException("Did not update one row");
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

	@Override
	public int addTransaction(Transaction t) {
		Connection conn = null;
		int transactionID;

		try {
			_factory.open();
			conn = _factory.getConnection();

			PreparedStatement stmt = conn.prepareStatement(
					"INSERT INTO transaction (user_id, date, detail, amount, is_income, recur_id, category_id) VALUES (?, ?, ?, ?, ?, ?, ?) RETURNING id;");
						
			stmt.setInt(1, t.getPersonID());
			stmt.setString(2, t.getDate());
			stmt.setString(3, t.getDetail());
			stmt.setBigDecimal(4, t.getAmount());
			stmt.setBoolean(5, t.isIncome());
			stmt.setInt(6, t.getRecurrence());
			stmt.setInt(7, t.getCategoryID());

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

	private ArrayList<Transaction> getOneOffTransactions(int personID, Date from, Date to, boolean showIncomes, boolean showExpenses, int categoryID) {
		ArrayList<Transaction> transactions = new ArrayList<Transaction>();
		Connection conn = null;

		try {
			_factory.open();
			conn = _factory.getConnection();

			StringBuilder query = new StringBuilder();
			query.append(
					"SELECT t.id, user_id, date, detail, amount, is_income, recur_id, c.name FROM transaction t "
					+ "LEFT JOIN category c ON c.id = t.category_id "
					+ "WHERE user_id = ?");
			
			if (categoryID != -1) {
				query.append(" AND category_id = " + categoryID);
			}
			
			if (showIncomes && !showExpenses) {
				query.append(" AND is_income = 't'");
			}
			
			if (!showIncomes && showExpenses) {
				query.append(" AND is_income = 'f'");
			}

			query.append(";");

			PreparedStatement stmt = conn.prepareStatement(query.toString());
			stmt.setInt(1, personID);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Transaction t = new Transaction();

				t.setTransactionID(rs.getInt("id"));
				t.setPersonID(rs.getInt("user_id"));
				t.setDate(rs.getString("date"));
				t.setDetail(rs.getString("detail"));
				t.setAmount(rs.getBigDecimal("amount"));
				t.setIsIncome(rs.getBoolean("is_income"));
				t.setCategoryName(rs.getString("name"));

				int isReccurence = rs.getInt("recur_id"); 

				if (isReccurence == -1) {
					SimpleDateFormat df = new SimpleDateFormat("dd MMMM yyyy");
					try {
						Date found_date = df.parse(t.getDate());
						
						if (found_date.after(from) && found_date.before(to)){
							transactions.add(t);
						}
					} 
					catch (ParseException e) {
						e.printStackTrace();
					}
				}
			}

			stmt.close();
		} catch (SQLException | ServiceLocatorException e) {
			System.err.println("getOneOffTransactions: "+ e.getMessage());
		} finally {
			if (conn != null) {
				try {
					_factory.close();
				} catch (SQLException e) {
					System.err.println("getOneOffTransactions: "+ e.getMessage());
				}
			}
		}

		return transactions;
	}

	private ArrayList<Transaction> getRecurringTransactions(int personID, Date from, Date to, boolean showIncomes, boolean showExpenses, int categoryID) {
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
			query.append("SELECT transaction_id, type, reccur_num, t.date, t.detail, t.amount, t.is_income, c.name "
					+ "FROM recurrence r " + "LEFT JOIN transaction t ON t.id = r.transaction_id "
					+ "LEFT JOIN category c ON c.id = t.category_id "
					+ "WHERE t.user_id = " + personID);
			
			if (categoryID != -1) {
				query.append(" AND category_id = " + categoryID);
			}
			
			if (showIncomes && !showExpenses) {
				query.append(" AND is_income = 't'");
			}
			
			if (!showIncomes && showExpenses) {
				query.append(" AND is_income = 'f'");
			}
			
			query.append(";");
			
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
				String categoryName = rs.getString("name");

				boolean infinitePayments = false;

				if (numberPayments == -1) {
					infinitePayments = true;
				}

				Date iteratorDate = new Date(initialDate.getTime());

				while ((numberPayments > 0 || infinitePayments) && iteratorDate.before(to)) {
					numberPayments--;

					if (iteratorDate.after(from) || iteratorDate.equals(from)) {
						
						SimpleDateFormat df = new SimpleDateFormat("dd MMMM yyyy");
						
						Transaction t = new Transaction();
						t.setTransactionID(transactionID);
						t.setDate(df.format(iteratorDate.getTime()));
						t.setDetail(detail);
						t.setAmount(amount);
						t.setIsIncome(income);
						t.setCategoryName(categoryName);

						transactions.add(t);
					}

					long addedTime = hashMap.get(reccurenceFreq);
					iteratorDate = new Date(iteratorDate.getTime() + addedTime);
				}
			}

			stmt.close();
		} catch (SQLException | ServiceLocatorException e) {
			System.err.println("getRecurringTransactions: "+ e.getMessage());
		} finally {
			if (conn != null) {
				try {
					_factory.close();
				} catch (SQLException e) {
					System.err.println("getRecurringTransactions: "+ e.getMessage());
				}
			}
		}

		return transactions;
	}

	public List<Transaction> getTransactions(int personID, Date from, Date to, boolean showIncomes, boolean showExpenses, int categoryID) {
		ArrayList<Transaction> masterTransactionList = new ArrayList<Transaction>();
		ArrayList<Transaction> oneOffTransactionList = this.getOneOffTransactions(personID, from, to, showIncomes, showExpenses, categoryID);
		ArrayList<Transaction> recurringTransactionList = this.getRecurringTransactions(personID, from, to, showIncomes, showExpenses, categoryID);

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
	public List<Transaction> getTransactionsByDate(int personID, Date from, Date to, boolean showIncomes, boolean showExpenses, int categoryID) {
		return this.getTransactions(personID, from, to, showIncomes, showExpenses, categoryID);
	}

	@Override
	public void createSession(Session session) {
		Connection conn = null;

		try {
			_factory.open();
			conn = _factory.getConnection();

			PreparedStatement stmt = conn
					.prepareStatement("INSERT INTO Session (id, user_id, last_access) VALUES (?, ?, ?);");

			stmt.setString(1, session.getSessionID());
			stmt.setInt(2, session.getUserID());
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
		Connection conn = null;

		try {
			_factory.open();
			conn = _factory.getConnection();

			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Session WHERE id = ?;");

			stmt.setString(1, sessionId);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				s = new Session();
				s.setSessionID(rs.getString("id"));
				s.setUserID(rs.getInt("user_id"));
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

			stmt.close();
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
		Connection conn = null;

		try {
			_factory.open();
			conn = _factory.getConnection();

			PreparedStatement stmt = conn.prepareStatement("DELETE FROM session WHERE id = ?;");

			stmt.setString(1, sessionId);
			int n = stmt.executeUpdate();

			if (n != 1) {
				throw new DataSourceException("Did not delete one row from database");
			}

			stmt.close();
		} catch (SQLException | ServiceLocatorException | DataSourceException e) {
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
		Connection conn = null;
		String token = null;

		try {
			_factory.open();
			conn = _factory.getConnection();

			PreparedStatement stmt = conn.prepareStatement("SELECT token FROM users WHERE username = ?;");
			stmt.setString(1, u.getUsername());
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				token = rs.getString(1);
			}

			System.out.println("DAO: getToken(): " + token);
			stmt.close();
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
			System.out.println("found token");
		}

		return token;
	}

	@Override
	public void setStatus(User u, int status) {
		System.out.println("Inside setStatus: Now setting status.");
		Connection conn = null;

		try {
			_factory.open();
			conn = _factory.getConnection();

			PreparedStatement stmt = conn.prepareStatement("UPDATE users SET status_id = ? WHERE username = ?;");
			stmt.setInt(1, status);
			stmt.setString(2, u.getUsername());

			int n = stmt.executeUpdate();

			if (n != 1) {
				throw new DataSourceException("Did not update one row into database");
			}

			System.out.println("Success");
			stmt.close();
		} catch (SQLException | ServiceLocatorException | DataSourceException e) {
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
		Connection conn = null;

		try {
			_factory.open();
			conn = _factory.getConnection();

			PreparedStatement stmt = conn.prepareStatement("UPDATE users SET token = ? WHERE username = ?;");
			stmt.setString(1, token);
			stmt.setString(2, u.getUsername());

			int n = stmt.executeUpdate();

			if (n != 1) {
				throw new DataSourceException("Did not update one row into database");
			}

			System.out.println("Success");
			stmt.close();
		} catch (SQLException | ServiceLocatorException | DataSourceException e) {
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
		Connection conn = null;

		try {
			_factory.open();
			conn = _factory.getConnection();

			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Session WHERE id = ? AND user_id = ?;");
			stmt.setString(1, sessionId);
			stmt.setInt(2, Integer.parseInt(userId));

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				s = new Session();
				s.setSessionID(rs.getString("id"));
				s.setUserID(rs.getInt("user_id"));
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

			stmt.close();
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

		if (u == null) {
			return null;
		}

		if (!u.getFirstName().equalsIgnoreCase(firstName) || !u.getLastName().equalsIgnoreCase(lastName)) {
			return null;
		}

		return u;
	}

	@Override
	public void setPassword(User u, String hashedPassword) {
		System.out.println("Inside setPassword: Now resetting password.");
		Connection conn = null;

		try {
			_factory.open();
			conn = _factory.getConnection();

			PreparedStatement stmt = conn.prepareStatement("UPDATE users SET password = ? WHERE username = ?;");
			stmt.setString(1, hashedPassword);
			stmt.setString(2, u.getUsername());

			int n = stmt.executeUpdate();

			if (n != 1) {
				throw new DataSourceException("Did not update one row into database");
			}

			System.out.println("Success");
			stmt.close();
		} catch (SQLException | ServiceLocatorException | DataSourceException e) {
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
		Connection conn = null;
		String salt = null;

		String query = "SELECT salt_hash FROM users WHERE username = ?;";
		if (userinfo.contains("@")) {
			query = "SELECT salt_hash FROM users WHERE email = ?;";
		}

		try {
			_factory.open();
			conn = _factory.getConnection();

			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, userinfo);

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				salt = rs.getString(1);
			}

			System.out.println("DAO: getSalt(): " + salt);
			stmt.close();
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

			stmt.close();
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

	@Override
	public List<Category> getAllCategories() {
		ArrayList<Category> categories = new ArrayList<Category>();

		Connection conn = null;

		try {
			_factory.open();
			conn = _factory.getConnection();

			PreparedStatement stmt = conn.prepareStatement("SELECT id, name FROM category;");
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Category c = new Category();
				c.setCategoryID(rs.getInt("id"));
				c.setCategory(rs.getString("name"));
				
				categories.add(c);
			}

		} 
		catch (SQLException | ServiceLocatorException e) {
			System.err.println("getCategories(): " + e.getMessage());
		} 
		finally {
			if (conn != null) {
				try {
					_factory.close();
				} 
				catch (SQLException e) {
					System.err.println("getCategories(): " + e.getMessage());
				}
			}
		}

		return categories;
	}

	@Override
	public List<Currency> getAllCurrencies() {
		ArrayList<Currency> currency = new ArrayList<Currency>();

		Connection conn = null;

		try {
			_factory.open();
			conn = _factory.getConnection();

			PreparedStatement stmt = conn.prepareStatement("SELECT id, short_name, long_name FROM currency;");
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Currency c = new Currency();
				c.setCurrencyID(rs.getInt("id"));
				c.setShortName(rs.getString("short_name"));
				c.setLongName(rs.getString("long_name"));
				
				currency.add(c);
			}

		} 
		catch (SQLException | ServiceLocatorException e) {
			System.err.println("getAllCurrencies(): " + e.getMessage());
		} 
		finally {
			if (conn != null) {
				try {
					_factory.close();
				} 
				catch (SQLException e) {
					System.err.println("getAllCurrencies(): " + e.getMessage());
				}
			}
		}

		return currency;
	}

	@Override
	public List<Occupation> getAllOccupations() {
		ArrayList<Occupation> occupation = new ArrayList<Occupation>();

		Connection conn = null;

		try {
			_factory.open();
			conn = _factory.getConnection();

			PreparedStatement stmt = conn.prepareStatement("SELECT id, name FROM occupation;");
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Occupation o = new Occupation();
				o.setOccupationID(rs.getInt("id"));
				o.setName(rs.getString("name"));
				
				occupation.add(o);
			}

		} 
		catch (SQLException | ServiceLocatorException e) {
			System.err.println("getAllOccupation(): " + e.getMessage());
		} 
		finally {
			if (conn != null) {
				try {
					_factory.close();
				} 
				catch (SQLException e) {
					System.err.println("getAllOccupation(): " + e.getMessage());
				}
			}
		}

		return occupation;
	}

	@Override
	public boolean createDefaultUserDetails(int userID) {
		boolean result = true;
		Connection conn = null;

		try {
			_factory.open();
			conn = _factory.getConnection();

			PreparedStatement stmt = conn.prepareStatement(
					"INSERT INTO user_detail (user_id, currency_id, age, gender, occupation_id) "
							+ " VALUES (?, 3, 0, 'U', 2);");
			// currency_id = 3 (AUD)
			// age = 0
			// gender = U = Unknown
			// occupation_id = 2 (Others)
			
			stmt.setInt(1, userID);

			int n = stmt.executeUpdate();

			if (n != 1) {
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

	@Override
	public void deleteUser(int userID) {
		Connection conn = null;

		try {
			_factory.open();
			conn = _factory.getConnection();

			PreparedStatement stmt = conn.prepareStatement("DELETE FROM users WHERE id = ?;");

			stmt.setInt(1, userID);
			int n = stmt.executeUpdate();

			if (n != 1) {
				throw new DataSourceException("Did not delete one row from database");
			}
			
			stmt.close();
		} 
		catch (SQLException | ServiceLocatorException | DataSourceException e) {
			System.err.println(e.getMessage());
		} 
		finally {
			if (conn != null) {
				try {
					_factory.close();
				} catch (SQLException e) {
					System.err.println(e.getMessage());
				}
			}
		}
	} 
}
