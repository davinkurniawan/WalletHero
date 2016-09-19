package au.edu.unsw.comp4920.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

import au.edu.unsw.comp4920.exception.ServiceLocatorException;

// DO NOT CHANGE THIS ONE

/**
 * @author Timothy
 *
 */
public class DBConnectionFactory {
	
	static Logger logger = Logger.getLogger(DBConnectionFactory.class.getName());
	private Connection conn = null;
	
	public DBConnectionFactory() {
		
	}

	public void open() throws ServiceLocatorException, SQLException{
		try{
			Class.forName("org.postgresql.Driver");
			
			conn = DriverManager.getConnection(
					"jdbc:postgresql://127.0.0.1:5432/postgres", "wallethero",
					"w4ll3th3r0");
		}
		catch(ClassNotFoundException e){
			logger.severe("Cannot find class, throwing exception " + e.getMessage());
			e.printStackTrace();
			throw new ServiceLocatorException("Cannot find class, throwing exception " + e.getMessage());
		}
		catch (SQLException e){
			logger.severe("Cannot open DB, throwing exception " + e.getMessage());
			e.printStackTrace();
			throw new ServiceLocatorException("Cannot open DB, throwing exception " + e.getMessage());
		}
	} 
	
	public Connection getConnection() throws ServiceLocatorException, SQLException{
		return conn;
	}
	
	public void close() throws SQLException {
		// destroy connection
		conn.close();
	}
}
