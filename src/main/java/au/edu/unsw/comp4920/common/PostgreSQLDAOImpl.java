package au.edu.unsw.comp4920.common;

import au.edu.unsw.comp4920.objects.*;
import au.edu.unsw.comp4920.exception.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Pattern;

import javax.naming.NamingException;

import au.edu.unsw.comp4920.common.DBConnectionFactory;

public class PostgreSQLDAOImpl implements CommonDAO {

	private DBConnectionFactory services;
	Connection con;
	
	public PostgreSQLDAOImpl() {
		services = new DBConnectionFactory();
		try {
			services.open();
			con = services.getConnection();
		} catch (ServiceLocatorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/*
	 * public User getUserDetails(String username) { // TODO Auto-generated
	 * method stub return null; }
	 * 
	 * public User getUserDetails(int uid) { // TODO Auto-generated method stub
	 * return null; }
	 */
	
	@Override
	public boolean createUser(User u) {
		
		// first test if user already exists
		/*if (userExists(user.getUsername()) || emailExists(user.getEmail())) {
			System.out.println("Not creating user because username and/or email already exists!");
			return false;
		} else {
			System.out.println("creating user = " + user.getUsername());
		}

		Connection con = null;
		try {
			con = services.createConnection();
			PreparedStatement stmt = con.prepareStatement(
					"insert into people (username, password, email, nickname, first_name, last_name, birth_year, "
							+ "address, account_activated, ban) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

			stmt.setString(1, user.getUsername());
			stmt.setString(2, user.getPassword());
			stmt.setString(3, user.getEmail());
			stmt.setString(4, user.getNickname());
			stmt.setString(5, user.getFirstName());
			stmt.setString(6, user.getLastName());
			stmt.setLong(7, user.getBirthYear());
			stmt.setString(8, user.getAddressString());
			stmt.setBoolean(9, false);
			stmt.setBoolean(10, false);

			int n = stmt.executeUpdate();
			if (n != 1)
				throw new DataAccessException("Did not insert one row into database");
			stmt.close();

		} catch (ServiceLocatorException e) {
			throw new DataAccessException("Unable to retrieve connection; " + e.getMessage(), e);
		} catch (SQLException e) {
			throw new DataAccessException("Unable to execute query; " + e.getMessage(), e);
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}

		insertBuyersAndSellers(user);
		return true;*/
		return false;
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
		
		String query = "select * from user where " + idQuery
				+ passwordQuery;
		Statement statement;
		try {
			statement = con.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = statement.executeQuery(query);
			if (rs.next()) {
				//pull all information out of results and put it into userDTO
				u = new User();
				u.setPersonID(rs.getInt("id"));
				u.setUsername(rs.getString("username"));
				u.setPassword(rs.getString("email"));
				u.setSalt(rs.getString("salt_hash"));
				u.setFirstName(rs.getString("first_name"));
				u.setMiddleName(rs.getString("middle_name"));
				u.setLastName(rs.getString("last_name"));
				u.setToken(rs.getString("token"));
				u.setStatus_id(rs.getString("status_id"));
				u.setBudget(rs.getDouble("budget"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
				services.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return u;
	}

}
