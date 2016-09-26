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
	private boolean recurrence = false;

	private Date date;
	private String detail;
	private BigDecimal amount;
	private int categoryID;
	private String categoryName;

	private boolean isIncome;

	public Transaction() {

	}

	// public Transaction(int transactionID, int personID, boolean recurrence,
	// Date date, String detail, BigDecimal amount,
	// int category, boolean isIncome) {
	// super();
	// this.transactionID = transactionID;
	// this.personID = personID;
	// this.date = date;
	// this.detail = detail;
	// this.amount = amount;
	// this.category = category;
	// this.isIncome = isIncome;
	// this.recurrence = recurrence;
	// }

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

	public boolean isRecurrence() {
		return recurrence;
	}

	public void setRecurrence(boolean recurrence) {
		this.recurrence = recurrence;
	}

	@Override
	public String toString() {
		return "Transaction [transactionID=" + transactionID + ", personID=" + personID + ", recurrence=" + recurrence
				+ ", date=" + date + ", detail=" + detail + ", amount=" + amount + ", categoryID=" + categoryID
				+ ", categoryName=" + categoryName + ", isIncome=" + isIncome + "]";
	}

	public int getRecurrence() {
		if (recurrence) {
			return 1;
		} else {
			return -1;
		}
	}

	public int compareTo(Transaction t) {
		return this.getDate().compareTo(t.getDate());
	}

	public int getCategoryID() {
		return categoryID;
	}

	public void setCategoryID(int categoryID) {
		this.categoryID = categoryID;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
}
