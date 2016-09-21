package au.edu.unsw.comp4920.common;

import java.util.Date;
import java.util.List;

import au.edu.unsw.comp4920.objects.*;

public interface CommonDAO {	
	public User getUserDetails(String username);
	
	public boolean createUser(User u);
	public User getUser(String userinfo, String password);
	public User getUser(String sid);
	
	public boolean addTransaction(Transaction t);
	public List<Transaction> getAllTransactions(int personID);
	public List<Transaction> getAllExpenses(int personID);
	public List<Transaction> getAllIncomes(int personID);
	public List<Transaction> getTransactionsByDate(int personID, Date from, Date to);
	
	public void createSession(Session session);
	public Session getSession(String sessionId);
	public void deleteSession(String sessionId);
	
	public String getToken(User u);
	public void setToken(User u, String token);
	public void setStatus(User u, int Status);
}
