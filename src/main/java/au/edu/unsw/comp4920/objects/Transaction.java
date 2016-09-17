package au.edu.unsw.comp4920.objects;

import java.math.BigDecimal;
import java.sql.Date;

/**
 * Basic transaction implementation, no support for recurring incomes and
 * expenses nor categories.
 * 
 * @author samt1995
 *
 */
public class Transaction {
	private int transactionID;
	private int personID;
	private Date date;
	private String details;
	private BigDecimal amount;
	private Boolean isIncome;

	public Transaction() {
	}

	public int getTransactionID() {
		return transactionID;
	}

	public void setTransactionID(int transactionID) {
		this.transactionID = transactionID;
	}

	public int getPersonID() {
		return personID;
	}

	public void setPersonID(int personID) {
		this.personID = personID;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public boolean isIncome() {
		return isIncome;
	}

	public void setIsIncome(boolean isIncome) {
		this.isIncome = isIncome;
	}

	public void setExpense() {
		this.isIncome = false;
	}

	public void setIncome() {
		this.isIncome = true;
	}

	@Override
	public String toString() {
		return "Transaction [transactionID=" + transactionID + ", personID=" + personID + ", date=" + date
				+ ", details=" + details + ", amount=" + amount + ", isIncome=" + isIncome + "]";
	}

}
