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
	private String detail;
	private BigDecimal amount;
	private String category;
	private boolean isIncome;

	public Transaction() {

	}

	public Transaction(int transactionID, int personID, Date date, String detail, BigDecimal amount, String category,
			boolean isIncome) {
		super();
		this.transactionID = transactionID;
		this.personID = personID;
		this.date = date;
		this.detail = detail;
		this.amount = amount;
		this.category = category;
		this.isIncome = isIncome;
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

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
		this.amount = this.amount.setScale(2, BigDecimal.ROUND_HALF_UP);

	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public boolean isIncome() {
		return isIncome;
	}

	public void setIsIncome(boolean isIncome) {
		this.isIncome = isIncome;
	}

	// Don't delete this, used in viewtransactions.jsp.
	public String getTransactionType() {
		if (this.isIncome()) {
			return "Income";
		} else {
			return "Expense";
		}
	}

	@Override
	public String toString() {
		return "Transaction [transactionID=" + transactionID + ", personID=" + personID + ", date=" + date + ", detail="
				+ detail + ", amount=" + amount + ", category=" + category + ", isIncome=" + isIncome + "]";
	}
}
