package au.edu.unsw.comp4920.common;

import au.edu.unsw.comp4920.objects.*;

public interface TransactionDAO {
	public boolean addTransaction(Transaction t);
	public Transaction getTransaction(int transactionID, int personID);
}
