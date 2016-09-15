package au.edu.unsw.comp4920.common;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import au.edu.unsw.comp4920.exception.ServiceLocatorException;

// DO NOT CHANGE THIS ONE

/**
 * @author Timothy
 *
 */
public class DBConnectionFactory {
	
	static Logger logger = Logger.getLogger(DBConnectionFactory.class.getName());
	private DataSource ds = null;
	private InitialContext ctx;
	
	public DBConnectionFactory() {
		
	}

	public void open() throws ServiceLocatorException, SQLException{
		//TODO
		/*try{
			ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/cs9321");
			logger.info("Database found: " + ds.toString());
		}
		catch(NamingException e){
			logger.severe("Cannot find context, throwing exception " + e.getMessage());
			e.printStackTrace();
			throw new ServiceLocatorException("Cannot find context, throwing exception " + e.getMessage());
		}*/
	} 
	
	public Connection getConnection() throws ServiceLocatorException, SQLException{
		Connection conn = ds.getConnection();
		return conn;
	}
	
	public void close() throws NamingException {
		// destroy connection
		ctx.close();
	}
}
