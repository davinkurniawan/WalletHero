package au.edu.unsw.comp4920.common;

import au.edu.unsw.comp4920.objects.*;
import au.edu.unsw.comp4920.exception.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;

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
				u = new User(rs);
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

			stmt.setString(1, u.getEmail());
			stmt.setString(2, u.getToken());
			stmt.setLong(3, u.getUserID());

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
	public Preference getUserPreference(int uid) {
		Connection conn = null;
		Preference p = null;

		try {
			_factory.open();
			conn = _factory.getConnection();

			PreparedStatement stmt = conn.prepareStatement("SELECT D.*, O.name, C.short_name, C.long_name "
					+ "FROM user_detail D " + "INNER JOIN occupation O " + "	ON D.occupation_id = O.id "
					+ "INNER JOIN currency C " + "	ON D.currency_id = C.id " + "WHERE D.user_id = ?");

			stmt.setInt(1, uid);

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				p = new Preference(rs);
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

		return p;
	}

	@Override
	public Preference getUserPreference(String sid) {
		Connection conn = null;
		Preference p = null;

		try {
			_factory.open();
			conn = _factory.getConnection();

			PreparedStatement stmt = conn.prepareStatement(
					"SELECT D.*, O.name, C.short_name, C.long_name " + "FROM  session S " + "INNER JOIN user_detail D "
							+ "	ON D.user_id = S.user_id " + "INNER JOIN occupation O " + "	ON D.occupation_id = O.id "
							+ "INNER JOIN currency C " + "	ON D.currency_id = C.id " + "WHERE S.id = ?");

			stmt.setString(1, sid);

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				p = new Preference(rs);
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

		return p;
	}

	@Override
	public boolean updatePreference(Preference p) {
		boolean result = true;
		Connection conn = null;

		try {
			_factory.open();
			conn = _factory.getConnection();

			String query = "UPDATE user_detail " + "SET currency_id = ?, age = ?, gender = ?, occupation_id = ?, deals=? "
					+ "WHERE id = ? AND user_id = ?;";
			PreparedStatement stmt = conn.prepareStatement(query);

			int index = 1;
			stmt.setInt(index++, p.getCurrencyID());
			stmt.setInt(index++, p.getAge());
			stmt.setString(index++, p.getGender());
			stmt.setInt(index++, p.getOccupationID());
			stmt.setString(index++, p.getDeals());
			stmt.setInt(index++, p.getPreferenceID());
			stmt.setInt(index++, p.getUserID());

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
		int transactionID = -1;

		try {
			_factory.open();
			conn = _factory.getConnection();

			PreparedStatement stmt = conn.prepareStatement(
					"INSERT INTO transaction (user_id, date, detail, amount, is_income, recur_id, category_id, currency_short_name) VALUES (?, ?, ?, ?, ?, ?, ?, ?) RETURNING id;");

			stmt.setInt(1, t.getUserID());
			stmt.setString(2, t.getDate());
			stmt.setString(3, t.getDetail());
			stmt.setBigDecimal(4, t.getAmount());
			stmt.setBoolean(5, t.isIncome());
			stmt.setInt(6, t.getRecurrence());
			stmt.setInt(7, t.getCategoryID());
			stmt.setString(8, t.getCurrency());

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

	private ArrayList<Transaction> getOneOffTransactions(int userID, Date from, Date to, boolean showIncomes,
			boolean showExpenses, int categoryID) {
		ArrayList<Transaction> transactions = new ArrayList<Transaction>();
		Connection conn = null;

		try {
			_factory.open();
			conn = _factory.getConnection();

			StringBuilder query = new StringBuilder();
			query.append(
					"SELECT t.id, user_id, date::DATE, detail, amount, is_income, recur_id, currency_short_name, c.name FROM transaction t "
							+ "LEFT JOIN category c ON c.id = t.category_id " + "WHERE user_id = ?");

			if (categoryID != -1) {
				query.append(" AND category_id = " + categoryID);
			}

			if (showIncomes && !showExpenses) {
				query.append(" AND is_income = 't'");
			}

			if (!showIncomes && showExpenses) {
				query.append(" AND is_income = 'f'");
			}

			query.append(" ORDER BY date DESC, t.id DESC");
			query.append(";");

			PreparedStatement stmt = conn.prepareStatement(query.toString());
			stmt.setInt(1, userID);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Transaction t = new Transaction();

				t.setTransactionID(rs.getInt("id"));
				t.setUserID(rs.getInt("user_id"));
				t.setDate(rs.getString("date"));
				t.setDetail(rs.getString("detail"));
				t.setAmount(rs.getBigDecimal("amount"));
				t.setIsIncome(rs.getBoolean("is_income"));
				t.setCategoryName(rs.getString("name"));
				t.setCurrency(rs.getString("currency_short_name"));

				int isReccurence = rs.getInt("recur_id");

				if (isReccurence == -1) {
					t.setRecurrence(false);

					// This is the format PostgreSQL stores their dates.
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
					try {
						Date found_date = df.parse(t.getDate());

						if ((found_date.after(from) || found_date.equals(from))
								&& (found_date.before(to) || found_date.equals(from))) {
							transactions.add(t);
						}
					} catch (ParseException e) {
						e.printStackTrace();
					}
				} else {
					t.setRecurrence(true);
				}
			}

			stmt.close();
		} catch (SQLException | ServiceLocatorException e) {
			System.err.println("getOneOffTransactions: " + e.getMessage());
		} finally {
			if (conn != null) {
				try {
					_factory.close();
				} catch (SQLException e) {
					System.err.println("getOneOffTransactions: " + e.getMessage());
				}
			}
		}

		return transactions;
	}

	private ArrayList<Transaction> getRecurringTransactions(int userID, Date from, Date to, boolean showIncomes,
			boolean showExpenses, int categoryID) {
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
			query.append(
					"SELECT transaction_id, type, reccur_num, t.date::DATE, t.detail, t.amount, t.is_income, t.currency_short_name, c.name "
							+ "FROM recurrence r " + "LEFT JOIN transaction t ON t.id = r.transaction_id "
							+ "LEFT JOIN category c ON c.id = t.category_id " + "WHERE t.user_id = " + userID);

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
				String currency = rs.getString("currency_short_name");

				boolean infinitePayments = false;

				if (numberPayments == -1) {
					infinitePayments = true;
				}

				Date iteratorDate = new Date(initialDate.getTime());

				while ((numberPayments > 0 || infinitePayments) && iteratorDate.before(to)) {
					numberPayments--;

					if (iteratorDate.after(from) || iteratorDate.equals(from)) {

						// This is the format PostgreSQL stores their dates.
						SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

						Transaction t = new Transaction();
						t.setTransactionID(transactionID);
						t.setDate(df.format(iteratorDate.getTime()));
						t.setDetail(detail);
						t.setAmount(amount);
						t.setIsIncome(income);
						t.setCategoryName(categoryName);
						t.setCurrency(currency);
						t.setRecurrence(true);

						transactions.add(t);
					}

					long addedTime = hashMap.get(reccurenceFreq);
					iteratorDate = new Date(iteratorDate.getTime() + addedTime);
				}
			}

			stmt.close();
		} catch (SQLException | ServiceLocatorException e) {
			System.err.println("getRecurringTransactions: " + e.getMessage());
		} finally {
			if (conn != null) {
				try {
					_factory.close();
				} catch (SQLException e) {
					System.err.println("getRecurringTransactions: " + e.getMessage());
				}
			}
		}

		return transactions;
	}
	
	private List<Transaction> getTransactions(int userID, Date from, Date to, boolean showIncomes, boolean showExpenses,
			int categoryID) {
		ArrayList<Transaction> masterTransactionList = new ArrayList<Transaction>();
		ArrayList<Transaction> oneOffTransactionList = this.getOneOffTransactions(userID, from, to, showIncomes,
				showExpenses, categoryID);
		ArrayList<Transaction> recurringTransactionList = this.getRecurringTransactions(userID, from, to, showIncomes,
				showExpenses, categoryID);

		masterTransactionList.addAll(oneOffTransactionList);
		masterTransactionList.addAll(recurringTransactionList);

		Collections.sort(masterTransactionList, new Comparator<Transaction>() {
			@Override
			public int compare(Transaction t1, Transaction t2) {
				return t2.compareTo(t1);
			}
		});

		return masterTransactionList;
	}

	@Override
	public List<Transaction> getTransactionsByDate(int userID, Date from, Date to, boolean showIncomes,
			boolean showExpenses, int categoryID, String userPrefferedCurrency) {
			
		List<Transaction> transactions = this.getTransactions(userID, from, to, showIncomes, showExpenses, categoryID);
		HashMap<String, BigDecimal> currencyHashmap = new HashMap<String, BigDecimal>();

		for (Transaction t : transactions) {
			String transactionCurrency = t.getCurrency();

			if (userPrefferedCurrency.equals(transactionCurrency)) {
				continue;
			} else if (currencyHashmap.containsKey(userPrefferedCurrency + transactionCurrency)) {
				BigDecimal exchangeRate = currencyHashmap.get(userPrefferedCurrency + transactionCurrency);
				t.setAmount(t.getAmount().divide(exchangeRate, 2, RoundingMode.HALF_UP));
				t.setCurrency(userPrefferedCurrency);
			} else {
				currencyHashmap.put(userPrefferedCurrency + transactionCurrency,
						this.getCurrencyExchangeRate(userPrefferedCurrency + transactionCurrency));
				BigDecimal exchangeRate = currencyHashmap.get(userPrefferedCurrency + transactionCurrency);
				t.setAmount(t.getAmount().divide(exchangeRate, 2, RoundingMode.HALF_UP));
				t.setCurrency(userPrefferedCurrency);
			}
		}
		
		return transactions;
	}
	
	/**
	 * Will retrieve exchange rate online, and cache this rate in the database.
	 */
	private BigDecimal getCurrencyExchangeRateOnline(String currencyPair) {
		CloseableHttpClient client = null;
		BigDecimal rate = null;
		
		try {
			client = HttpClientBuilder.create().build();
			HttpGet req = new HttpGet("https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.xchange%20where%20pair%20%3D%20%22" + currencyPair + "%22&format=json&diagnostics=true&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys&callback=");
			
			HttpResponse resp = client.execute(req);
			HttpEntity entity = resp.getEntity();
			
			BufferedReader br = new BufferedReader(new InputStreamReader(entity.getContent()));
			StringBuilder sb = new StringBuilder();
			String line = "";
			
			while ((line = br.readLine())!= null) {
				sb.append(line + "\n");
			}
			
			JSONObject json = new JSONObject(sb.toString());
			rate = json.getJSONObject("query").getJSONObject("results").getJSONObject("rate").getBigDecimal("Rate");
			
		} catch (Exception e) {
			System.err.println("getCurrencyExchangeRateOnline failure when getting currency: ");
			e.printStackTrace();
		} finally {
			try {
				client.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		// Cache the exchange rate in the database.
		Connection conn = null;

		try {
			_factory.open();
			conn = _factory.getConnection();

			Date date = new Date();
			SimpleDateFormat df = new SimpleDateFormat(Constants.SIMPLE_DEFAULT_DATE_FORMAT);
			String dateString = df.format(date);
			
			PreparedStatement stmt = conn
					.prepareStatement("INSERT INTO currency_pair (pair, rate, date) VALUES (?, ?, ?);");

			stmt.setString(1, currencyPair);
			stmt.setBigDecimal(2, rate);
			stmt.setString(3, dateString);
			stmt.executeUpdate();

			stmt.close();
		} catch (SQLException | ServiceLocatorException e) {
			System.err.println("getCurrencyExchangeRateOnline: " + e.getMessage());
		} finally {
			if (conn != null) {
				try {
					_factory.close();
				} catch (SQLException e) {
					System.err.println("getCurrencyExchangeRateOnline: " + e.getMessage());
				}
			}
		}
		
		return rate;	
	}
	
	private BigDecimal getCurrencyExchangeRateDatabase(String currencyPair) {
		BigDecimal rate = null;
		Connection conn = null;

		try {
			_factory.open();
			conn = _factory.getConnection();

			Date date = new Date();
			SimpleDateFormat df = new SimpleDateFormat(Constants.SIMPLE_DEFAULT_DATE_FORMAT);
			String dateString = df.format(date);
			
			StringBuilder query = new StringBuilder();
			query.append(
					"SELECT rate FROM currency_pair WHERE pair = ? AND date = ?");
			query.append(";");

			PreparedStatement stmt = conn.prepareStatement(query.toString());
			
			stmt.setString(1, currencyPair);
			stmt.setString(2, dateString);
			
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				rate = rs.getBigDecimal("rate");
			}

			stmt.close();
		} catch (SQLException | ServiceLocatorException e) {
			System.err.println("getCurrencyExchangeRateDatabase: " + e.getMessage());
		} finally {
			if (conn != null) {
				try {
					_factory.close();
				} catch (SQLException e) {
					System.err.println("getCurrencyExchangeRateDatabase: " + e.getMessage());
				}
			}
		}
		
		return rate;
	}
	
	private BigDecimal getCurrencyExchangeRate(String currencyPair) {
		BigDecimal rate = getCurrencyExchangeRateDatabase(currencyPair);
		
		if (rate != null) {
			return rate;
		} else {
			return getCurrencyExchangeRateOnline(currencyPair);
		}
	}

	@Override
	public List<Transaction> getAllTransactions(int userID) {
		ArrayList<Transaction> transactions = new ArrayList<Transaction>();
		Connection conn = null;

		try {
			_factory.open();
			conn = _factory.getConnection();

			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM transaction WHERE user_id = ? ORDER BY date DESC, id DESC;");

			stmt.setInt(1, userID);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Transaction t = new Transaction();
				t.setTransactionID(rs.getInt("id"));
				int isReccurence = rs.getInt("recur_id");

				if (isReccurence == 1) {
					t.setRecurrence(true);
				} else {
					t.setRecurrence(false);
				}

				transactions.add(t);
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

		return transactions;
	}

	@Override
	public Transaction getTransaction(int transactionID) {
		Connection conn = null;
		Transaction t = null;

		try {
			_factory.open();
			conn = _factory.getConnection();

			PreparedStatement stmt = conn.prepareStatement("SELECT t.*, c.name FROM transaction t LEFT JOIN category c ON c.id = t.category_id WHERE t.id = ?;");

			stmt.setInt(1, transactionID);

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				t = new Transaction();

				t.setTransactionID(rs.getInt("id"));
				t.setUserID(rs.getInt("user_id"));
				t.setDate(rs.getString("date"));
				t.setDetail(rs.getString("detail"));
				t.setAmount(rs.getBigDecimal("amount"));
				t.setIsIncome(rs.getBoolean("is_income"));
				t.setCategoryID(rs.getInt("category_id"));
				t.setCategoryName(rs.getString("name"));
				t.setCurrency(rs.getString("currency_short_name"));
				
				int isReccurence = rs.getInt("recur_id");

				if (isReccurence == -1) {
					t.setRecurrence(false);
				} else {
					t.setRecurrence(true);
				}
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

		return t;
	}
	
	@Override
	public List<Goal> getAllGoals(int userID) {
		ArrayList<Goal> goalList = new ArrayList<Goal>();
		Connection conn = null;

		try {
			_factory.open();
			conn = _factory.getConnection();

			StringBuilder query = new StringBuilder();
			query.append(
					"SELECT g.id, g.detail, g.goal_amount, g.goal_type, g.frequency, g.category, c.name FROM goal g "
							+ "LEFT JOIN category c ON c.id = g.category " + "WHERE g.user_id = ?");

			query.append(";");

			PreparedStatement stmt = conn.prepareStatement(query.toString());
			stmt.setInt(1, userID);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Goal g = new Goal();
				g.setGoalID(rs.getInt("id"));
				g.setDetail(rs.getString("detail"));
				g.setGoalAmount(rs.getBigDecimal("goal_amount"));
				g.setGoalPeriod(rs.getString("frequency"));
				g.setCategoryString(rs.getString("name"));
				g.setCategory(rs.getInt("category"));

				int goalType = rs.getInt("goal_type");

				if (goalType == Goal.EXPENSE_RESTRICTION_GOAL) {
					g.setExpenseRestrictionGoal();
				} else {
					g.setSavingGoal();
				}

				goalList.add(g);
			}

			stmt.close();
		} catch (SQLException | ServiceLocatorException e) {
			System.err.println("getGoals: " + e.getMessage());
		} finally {
			if (conn != null) {
				try {
					_factory.close();
				} catch (SQLException e) {
					System.err.println("getGoals: " + e.getMessage());
				}
			}
		}

		return goalList;
	}

	@Override
	public boolean createSession(Session session) {
		boolean result = true;
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
	public Session getSession(String sessionID) {
		Session s = null;
		Connection conn = null;

		try {
			_factory.open();
			conn = _factory.getConnection();

			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Session WHERE id = ?;");

			stmt.setString(1, sessionID);
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
	public boolean deleteSession(String sessionID) {
		boolean result = true;
		Connection conn = null;

		try {
			_factory.open();
			conn = _factory.getConnection();

			PreparedStatement stmt = conn.prepareStatement("DELETE FROM session WHERE id = ?;");

			stmt.setString(1, sessionID);
			int n = stmt.executeUpdate();

			if (n != 1) {
				throw new DataSourceException("Did not delete one row from database");
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
	public boolean setStatus(String username, int status) {
		System.out.println("Inside setStatus: Now setting status.");
		boolean result = true;
		Connection conn = null;

		try {
			_factory.open();
			conn = _factory.getConnection();

			PreparedStatement stmt = conn.prepareStatement("UPDATE users SET status_id = ? WHERE username = ?;");
			stmt.setInt(1, status);
			stmt.setString(2, username);

			int n = stmt.executeUpdate();

			if (n != 1) {
				throw new DataSourceException("Did not update one row into database");
			}

			System.out.println("Success");
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
	public boolean setToken(User u, String token) {
		System.out.println("Inside setToken: Now setting token.");
		boolean result = true;
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
	public Session getUserSession(String userID, String sessionID) {
		Session s = null;
		Connection conn = null;

		try {
			_factory.open();
			conn = _factory.getConnection();

			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Session WHERE id = ? AND user_id = ?;");
			stmt.setString(1, sessionID);
			stmt.setInt(2, Integer.parseInt(userID));

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
	public boolean setPassword(User u, String hashedPassword) {
		System.out.println("Inside setPassword: Now resetting password.");
		boolean result = true;
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

			PreparedStatement stmt = conn.prepareStatement("SELECT id, name FROM category ORDER BY name ASC;");
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Category c = new Category();
				c.setCategoryID(rs.getInt("id"));
				c.setCategory(rs.getString("name"));

				categories.add(c);
			}

		} catch (SQLException | ServiceLocatorException e) {
			System.err.println("getCategories(): " + e.getMessage());
		} finally {
			if (conn != null) {
				try {
					_factory.close();
				} catch (SQLException e) {
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

			PreparedStatement stmt = conn
					.prepareStatement("SELECT id, short_name, long_name FROM currency ORDER BY long_name ASC;");
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Currency c = new Currency();
				c.setCurrencyID(rs.getInt("id"));
				c.setShortName(rs.getString("short_name"));
				c.setLongName(rs.getString("long_name"));

				currency.add(c);
			}

		} catch (SQLException | ServiceLocatorException e) {
			System.err.println("getAllCurrencies(): " + e.getMessage());
		} finally {
			if (conn != null) {
				try {
					_factory.close();
				} catch (SQLException e) {
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

			PreparedStatement stmt = conn.prepareStatement("SELECT id, name FROM occupation ORDER BY name ASC;");
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Occupation o = new Occupation();
				o.setOccupationID(rs.getInt("id"));
				o.setName(rs.getString("name"));

				occupation.add(o);
			}

		} catch (SQLException | ServiceLocatorException e) {
			System.err.println("getAllOccupation(): " + e.getMessage());
		} finally {
			if (conn != null) {
				try {
					_factory.close();
				} catch (SQLException e) {
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

			PreparedStatement stmt = conn
					.prepareStatement("INSERT INTO user_detail (user_id, currency_id, age, gender, occupation_id) "
							+ " VALUES (?, 3, 0, 'O', 2);");
			// currency_id = 3 (AUD)
			// age = 0
			// gender = O = Others
			// occupation_id = 2 (Others)

			stmt.setInt(1, userID);

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

		return result;
	}

	@Override
	public boolean deleteUser(int userID) {
		boolean result = true;
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
	public boolean disableUser(String username) {
		return this.setStatus(username, 3); // 3 is the status for disabled.
	}

	@Override
	public boolean deleteAllUserData(int userID) {
		boolean result = false;
		int count = 0;

		List<Transaction> transactions = this.getAllTransactions(userID);
		List<Goal> goals = this.getAllGoals(userID);

		while (!result && count < 3) { // Fail check up to 3 times
			for (Transaction t : transactions) {
				if (t.isRecurrence()) {
					result = this.deleteRecurrence(t.getTransactionID());
				}
			}

			for (Transaction t : transactions) {
				result = this.deleteUserTransaction(t.getTransactionID());
			}

			for (Goal g : goals) {
				result = this.deleteUserGoal(g.getGoalID(), userID);
			}

			count++;
		}

		return result;
	}

	@Override
	public boolean deleteUserTransaction(int transactionID) {
		boolean result = true;
		Connection conn = null;

		try {
			_factory.open();
			conn = _factory.getConnection();

			PreparedStatement stmt = conn.prepareStatement("DELETE FROM transaction WHERE id = ?;");

			stmt.setInt(1, transactionID);
			int n = stmt.executeUpdate();

			if (n != 1) {
				throw new DataSourceException("Did not delete user's transaction!");
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
	public boolean deleteRecurrence(int transactionID) {
		boolean result = true;
		Connection conn = null;

		try {
			_factory.open();
			conn = _factory.getConnection();

			PreparedStatement stmt = conn.prepareStatement("DELETE FROM recurrence WHERE transaction_id = ?;");

			stmt.setInt(1, transactionID);
			int n = stmt.executeUpdate();

			if (n != 1) {
				throw new DataSourceException("Did not delete all of user's recurrences!");
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
	public boolean deleteUserGoal(int goalID, int userID){
		boolean result = true;
		Connection conn = null;

		try {
			_factory.open();
			conn = _factory.getConnection();

			PreparedStatement stmt = conn.prepareStatement("DELETE FROM goal WHERE id = ? AND user_id = ?;");

			stmt.setInt(1, goalID);
			stmt.setInt(2, userID);
			int n = stmt.executeUpdate();

			if (n != 1) {
				throw new DataSourceException("Did not delete user's goal!");
			}
			
			stmt.close();
		} 
		catch (SQLException | ServiceLocatorException | DataSourceException e) {
			result = false;
			System.err.println(e.getMessage());
		} 
		finally {
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
	public boolean deleteUserCompletely(int userID) {
		boolean result = true;
		
		// Delete all of the User's Data.
		result = this.deleteAllUserData(userID);
		
		// Delete All Session.
		result = this.deleteAllSession(userID);
		
		// Delete User's Details.
		result = this.deleteUserDetails(userID);
		
		// Delete User.
		result = this.deleteUser(userID);
				
		return result;
	}

	@Override
	public boolean addGoal(Goal g) {
		boolean result = true;
		Connection conn = null;

		try {
			_factory.open();
			conn = _factory.getConnection();

			PreparedStatement stmt = conn.prepareStatement(
					"INSERT INTO goal (user_id, detail, goal_amount, goal_type, category, frequency) VALUES (?, ?, ?, ?, ?, ?);");

			stmt.setInt(1, g.getUserID());
			stmt.setString(2, g.getDetail());
			stmt.setBigDecimal(3, g.getGoalAmount());
			stmt.setInt(4, g.getGoalType());
			stmt.setInt(5, g.getCategory());
			stmt.setString(6, g.getGoalPeriod());
			
			int n = stmt.executeUpdate();

			if (n != 1) {
				throw new DataSourceException("Did not insert a new user's goal!");
			}

			stmt.close();
		} 
		catch (SQLException | ServiceLocatorException | DataSourceException e) {
			System.err.println(e.getMessage());
			result = false;
		} 
		finally {
			if (conn != null) {
				try {
					_factory.close();
				} 
				catch (SQLException e) {
					System.err.println(e.getMessage());
					result = false;
				}
			}
		}

		return result;
	}
	
	@Override
	public boolean deleteAllSession(int userID) {
		boolean result = true;
		Connection conn = null;

		try {
			_factory.open();
			conn = _factory.getConnection();

			PreparedStatement stmt = conn.prepareStatement("DELETE FROM session WHERE user_id = ?;");

			stmt.setInt(1, userID);
			int n = stmt.executeUpdate();

			if (n < 0) {
				throw new DataSourceException("Did not delete all of the user's session!");
			}
			
			stmt.close();
		} 
		catch (SQLException | ServiceLocatorException | DataSourceException e) {
			result = false;
			System.err.println(e.getMessage());
		} 
		finally {
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
	public boolean updateUserTransaction(Transaction t) {
		boolean result = true;
		Connection conn = null;

		try {
			_factory.open();
			conn = _factory.getConnection();
			
			String query = "UPDATE transaction SET date = ?, detail = ?, amount = ?, is_income = ?, category_id = ?, currency_short_name = ? WHERE id = ?;";
			PreparedStatement stmt = conn.prepareStatement(query);

			stmt.setString(1, t.getDate());
			stmt.setString(2, t.getDetail());
			stmt.setBigDecimal(3, t.getAmount());
			stmt.setBoolean(4, t.isIncome());
			stmt.setInt(5, t.getCategoryID());
			stmt.setString(6, t.getCurrency());
			stmt.setInt(7, t.getTransactionID());

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
	public boolean deleteUserDetails(int userID) {
		boolean result = true;
		Connection conn = null;

		try {
			_factory.open();
			conn = _factory.getConnection();

			PreparedStatement stmt = conn.prepareStatement("DELETE FROM user_detail WHERE user_id = ?;");

			stmt.setInt(1, userID);
			int n = stmt.executeUpdate();

			if (n < 0) {
				throw new DataSourceException("Did not delete all of the user's details!");
			}
			
			stmt.close();
		} 
		catch (SQLException | ServiceLocatorException | DataSourceException e) {
			result = false;
			System.err.println(e.getMessage());
		} 
		finally {
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
	public Map<String, BigDecimal> getCurrentBudget(int userID, String userPreferredCurrency) {
		BigDecimal totalBudget = new BigDecimal(0.0);
		BigDecimal totalExpense = new BigDecimal(0.0);
		BigDecimal totalIncome = new BigDecimal(0.0);
		
		Map<String, BigDecimal> result = new HashMap<String, BigDecimal>();
		
		Date epochTime = new Date(0L);
		Date currentTime = new Date();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		
		System.err.println("Epoch Time: " + sdf.format(epochTime));
		System.err.println("Current Time: " + sdf.format(currentTime));
		
		//List<Transaction> getTransactionsByDate(int userID, Date from, Date to, boolean showIncomes,
		// boolean showExpenses, int categoryID, String userPrefferedCurrency) 
		List<Transaction> allTransactions = this.getTransactionsByDate(userID, epochTime, currentTime, true, true, -1, userPreferredCurrency);

		for (Transaction t : allTransactions){
			
			if (t.isIncome()) {
				System.err.println("Income Amount: " + userPreferredCurrency + " " + t.getAmount());
				totalBudget = totalBudget.add(t.getAmount());
				totalIncome = totalIncome.add(t.getAmount());
			}
			else if (!t.isIncome()) {
				System.err.println("Expense Amount: " + userPreferredCurrency + " " + t.getAmount());
				totalBudget = totalBudget.subtract(t.getAmount());
				totalExpense = totalExpense.add(t.getAmount());
			}
		}
		
		result.put("totalIncome", totalIncome);
		result.put("totalExpense", totalExpense);
		result.put("totalBudget", totalBudget);
		
		System.err.println("Total Current Budget to Date: " + userPreferredCurrency + " " + totalBudget);
				
		return result;
	}
}
