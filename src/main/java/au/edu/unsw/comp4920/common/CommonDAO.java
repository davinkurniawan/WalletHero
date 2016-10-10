package au.edu.unsw.comp4920.common;

import java.util.Date;
import java.util.List;

import au.edu.unsw.comp4920.objects.*;

public interface CommonDAO {	
	public boolean createUser(User u);
	public boolean createDefaultUserDetails(int userID);
	
	public boolean disableUser(int userID);
	public boolean deleteUser(int userID);
	public boolean deleteAllUserData(int userID);
	public boolean deleteUserCompletely(int userID);

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
	public List<Transaction> getTransactionsByDate(int personID, Date from, Date to, boolean showIncomes, boolean showExpenses, int categoryID);
	
	public boolean addGoal(Goal g);
	public List<Goal> getGoals(int personID);
	
	public void createSession(Session session);
	public Session getSession(String sessionId);
	public Session getUserSession(String userId, String sessionId);
	public void deleteSession(String sessionId);
	
	public String getToken(User u);
	public void setToken(User u, String token);
	public void setStatus(User u, int Status);
	
	public void setPassword(User u, String hashedPassword);
	public String getSalt(String userinfo);
	
	public List<Category> getAllCategories();
	public List<Currency> getAllCurrencies();
	public List<Occupation> getAllOccupations();
}
