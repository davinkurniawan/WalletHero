package au.edu.unsw.comp4920.common;

import au.edu.unsw.comp4920.objects.*;
import au.edu.unsw.comp4920.exception.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
	
	@Override
	public boolean createUser(User u) {
		// Check if the user name or email already exist
		if (getUser(u.getUsername(), null) != null || getUser(u.getEmail(), null) != null) {
			System.out.println("Not creating user because username and/or email already exists!");
			return false;
		} else {
			System.out.println("creating user = " + u.getUsername());
		}

		Connection con = null;
		try {
			con = services.getConnection();
			PreparedStatement stmt = con.prepareStatement(
					"insert into user (username, email, password, salt_hash, first_name,"
							+ " last_name, token, status_id, budget) values (?, ?, ?, ?, ?, ?, ?, ?, ?)");

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

		} catch (ServiceLocatorException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (DataSourceException e) {
			// TODO Auto-generated catch block
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
