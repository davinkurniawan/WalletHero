package au.edu.unsw.comp4920.common;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import au.edu.unsw.comp4920.objects.*;

public interface CommonDAO {	
	public void closeConnection() throws SQLException;
	
	public boolean createUser(User u);
	public boolean createDefaultUserDetails(int userID);
	
	public boolean disableUser(String username);
	public boolean deleteUser(int userID); // Roll back on Sign Up Failure
	public boolean deleteAllUserData(int userID);
	public boolean deleteUserCompletely(int userID); // Delete completely

	public User getUserDetails(String username);
	public boolean deleteUserDetails(int userID);
	
	public User getUser(String userinfo, String password);
	public User getUser(String sid);
	public User getUser(String userinfo, String firstName, String lastName);
	public List<User> getAllUsers();
	
	public boolean updateUserNames(User u);
	public boolean updateUserEmail(User u);

	public Preference getUserPreference(int uid);
	public Preference getUserPreference(String sid);
	public boolean updatePreference(Preference p);
	
	public int addTransaction(Transaction t);
	public boolean addRecurring(Recurrence r);
	public List<Transaction> getTransactionsByDate(int userID, Date from, Date to, boolean showIncomes, boolean showExpenses, int categoryID, String userPrefferedCurrency);
	public List<Transaction> getAllTransactions(int userID);
	public Transaction getTransaction(int transactionID);
	public boolean updateUserTransaction(Transaction t);
	public boolean deleteUserTransaction(int transactionID);
	public boolean deleteRecurrence(int transactionID);
	
	public BigDecimal getCurrencyExchangeRate(String currencyPair);
	
	public boolean createSession(Session session);
	public Session getSession(String sessionID);
	public Session getUserSession(String userID, String sessionID);
	public boolean deleteSession(String sessionID);
	public boolean deleteAllSession(int userID);
	
	public String getToken(User u);
	public boolean setToken(User u, String token);
	public boolean setStatus(String username, int Status);
	
	public boolean setPassword(User u, String hashedPassword);
	public String getSalt(String userinfo);
	
	public List<Category> getAllCategories();
	public List<Currency> getAllCurrencies();
	public List<Occupation> getAllOccupations();
	
	public boolean addGoal(Goal g);
	public List<Goal> getAllGoals(int userID, String userPreferredCurrency);
	public boolean deleteUserGoal(int goalID, int userID);
	
	public Map<String, BigDecimal> getCurrentBudget(int userID, String userPreferredCurrency);
}
