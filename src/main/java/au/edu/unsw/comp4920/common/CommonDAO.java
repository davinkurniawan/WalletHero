package au.edu.unsw.comp4920.common;

import java.util.Date;
import java.util.List;

import au.edu.unsw.comp4920.objects.*;

public interface CommonDAO {	
	public boolean createUser(User u);
	public boolean createDefaultUserDetails(int userID);
	
	public boolean disableUser(int userID);
	public boolean deleteUser(int userID); // Roll back on Sign Up Failure
	public boolean deleteAllUserData(int userID);
	public boolean deleteUserCompletely(int userID); // Delete completely

	public User getUserDetails(String username);
	public User getUser(String userinfo, String password);
	public User getUser(String sid);
	public User getUser(String userinfo, String firstName, String lastName);
	
	public boolean updateUserNames(User u);
	public boolean updateUserEmail(User u);

	public Preference getUserPreference(int uid);
	public Preference getUserPreference(String sid);
	
	public boolean updatePreference(Preference p);
	
	public int addTransaction(Transaction t);
	public boolean addRecurring(Recurrence r);
	public List<Transaction> getTransactionsByDate(int userID, Date from, Date to, boolean showIncomes, boolean showExpenses, int categoryID);
	public List<Transaction> getAllTransactions(int userID);
	public Transaction getTransaction(int transactionID);
	public boolean deleteUserTransaction(int transactionID);
	public boolean deleteRecurrence(int transactionID);
	
	public List<Goal> getGoals(int personID);
	public void createSession(Session session);
	public Session getSession(String sessionID);
	public Session getUserSession(String userID, String sessionID);
	public void deleteSession(String sessionID);
	
	public String getToken(User u);
	public void setToken(User u, String token);
	public void setStatus(User u, int Status);
	
	public void setPassword(User u, String hashedPassword);
	public String getSalt(String userinfo);
	
	public List<Category> getAllCategories();
	public List<Currency> getAllCurrencies();
	public List<Occupation> getAllOccupations();
	
	public boolean addGoal(Goal g);
	public boolean deleteUserGoal(int goalID);
}
