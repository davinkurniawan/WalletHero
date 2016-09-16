package au.edu.unsw.comp4920.common;

import au.edu.unsw.comp4920.objects.*;
import edu.unsw.comp9321.exception.DataAccessException;
import edu.unsw.comp9321.exception.ServiceLocatorException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import au.edu.unsw.comp4920.common.DBConnectionFactory;
import au.edu.unsw.comp4920.exception.*;

public class PostgreSQLDAOImpl implements CommonDAO {

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
	public User getUser(String username, String password) {
		// TODO Auto-generated method stub
		return null;
	}

}
