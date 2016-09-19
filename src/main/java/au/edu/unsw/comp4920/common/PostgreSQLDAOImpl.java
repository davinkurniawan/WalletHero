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
import java.util.Date;

import javax.naming.NamingException;

import au.edu.unsw.comp4920.common.DBConnectionFactory;

public class PostgreSQLDAOImpl implements CommonDAO {

	private DBConnectionFactory services;
	Connection con;

	public PostgreSQLDAOImpl() {
		services = new DBConnectionFactory();
		System.out.println("constructing postgres");
		try {
			System.out.println("getting connection");
			services.open();
			con = services.getConnection();
		} catch (ServiceLocatorException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean createUser(User u) {
		// Check if the user name or email already exist
		if (getUser(u.getUsername(), null) != null || getUser(u.getEmail(), null) != null) {
			System.out.println("Not creating user because username and/or email already exists!");
			return false;
		} else {
			System.out.println("creating user = " + u.getUsername());
		}

		try {
			PreparedStatement stmt = con
					.prepareStatement("insert into user (username, email, password, salt_hash, first_name,"
							+ " last_name, token, status_id, budget) values (?, ?, ?, ?, ?, ?, ?, ?, ?);");

			stmt.setString(1, u.getUsername());
			stmt.setString(2, u.getEmail());
			stmt.setString(3, u.getPassword());
			stmt.setString(4, "lol");
			stmt.setString(5, u.getFirstName());
			stmt.setString(6, u.getLastName());
			stmt.setString(7, "inactivated");
			stmt.setInt(8, 0);

			int n = stmt.executeUpdate();
			if (n != 1)
				throw new DataSourceException("Did not insert one row into database");
			stmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (DataSourceException e) {
			e.printStackTrace();
		} finally {
			if (con != null) {
				try {
					con.close();
					services.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				} catch (NamingException e) {
					e.printStackTrace();
				}
			}
		}

		return true;
	}

	@Override
	public User getUser(String userinfo, String password) {

		User u = null;
		String idQuery;
		String passwordQuery;

		if (userinfo.contains("@")) {
			idQuery = "email = " + userinfo;
		} else {
			idQuery = "username = " + userinfo;
		}

		if (password != null) {
			passwordQuery = " and password = " + password + ";";
		} else {
			passwordQuery = ";";
		}

		String query = "select * from user where " + idQuery + passwordQuery;
		Statement statement;
		try {
			statement = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = statement.executeQuery(query);
			if (rs.next()) {
				// pull all information out of results and put it into userDTO
				u = new User();
				u.setPersonID(rs.getInt("id"));
				u.setUsername(rs.getString("username"));
				u.setPassword(rs.getString("email"));
				u.setSalt(rs.getString("salt_hash"));
				u.setFirstName(rs.getString("first_name"));
				u.setMiddleName(rs.getString("middle_name"));
				u.setLastName(rs.getString("last_name"));
				u.setToken(rs.getString("token"));
				u.setStatus_id(rs.getInt("status_id"));
				u.setBudget(rs.getDouble("budget"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
				services.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (NamingException e) {
				e.printStackTrace();
			}
		}
		return u;
	}

	@Override
	public boolean addTransaction(Transaction t) {
		Connection con = null;
		try {
			con = services.getConnection();

			PreparedStatement stmt = con.prepareStatement(
					"INSERT INTO transaction (user_id, date, detail, amount, is_income) VALUES (?, ?, ?, ?, ?);");

			stmt.setInt(1, t.getPersonID());
			stmt.setDate(2, t.getDate());
			stmt.setString(3, t.getDetails());
			stmt.setBigDecimal(4, t.getAmount());
			stmt.setBoolean(5, t.isIncome());

			int n = stmt.executeUpdate();
			if (n != 1)
				throw new DataSourceException("Did not insert one row into database");
			stmt.close();

		} catch (ServiceLocatorException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (DataSourceException e) {
			e.printStackTrace();
		} finally {
			if (con != null) {
				try {
					con.close();
					services.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				} catch (NamingException e) {
					e.printStackTrace();
				}
			}
		}
		return true;
	}

	public List<Transaction> getTransactions(int personID, Date from, Date to, Boolean isIncome) {
		ArrayList<Transaction> transactions = new ArrayList<Transaction>();

		try {
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
			
			PreparedStatement stmt = con.prepareStatement(query.toString());
			stmt.setInt(1, personID);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Transaction t = new Transaction();

				t.setTransactionID(rs.getInt("id"));
				t.setPersonID(rs.getInt("user_id"));
				t.setDate(rs.getDate("date"));
				t.setDetails(rs.getString("detail"));
				t.setAmount(rs.getBigDecimal("amount"));
				t.setIsIncome(rs.getBoolean("is_income"));

				transactions.add(t);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
				services.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (NamingException e) {
				e.printStackTrace();
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
		try {
			PreparedStatement stmt = con
					.prepareStatement("insert into session (id, user_id, last_access) values (?, ?, ?);");

			stmt.setString(1, session.getSessionId());
			stmt.setInt(2, session.getUserId());
			stmt.setString(3, session.getLastAccess());

			int n = stmt.executeUpdate();
			if (n != 1)
				throw new DataSourceException("Did not insert one row into database");
			stmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (DataSourceException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
				services.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	@Override
	public Session getSession(String sessionId) {
		Session s = null;
		String query = "select * from user where id = '" + sessionId + "';";
		Statement statement;
		try {
			statement = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = statement.executeQuery(query);
			if (rs.next()) {
				s = new Session();
				s.setSessionId(rs.getString("id"));
				s.setUserId(rs.getInt("user_id"));
				s.setLastAccess(rs.getString("last_access"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
				services.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (NamingException e) {
				e.printStackTrace();
			}
		}
		return s;
	}

}
