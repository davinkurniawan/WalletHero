package au.edu.unsw.comp4920.objects;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Basic transaction implementation, no support for recurring incomes and
 * expenses nor categories.
 * 
 * @author samt1995
 *
 */
public class Transaction {
	private int transactionID;
	private int userID;
	private boolean recurrence = false;
	private String date;
	private String detail;
	private BigDecimal amount;
	private int categoryID;
	private String categoryName;
	private boolean isIncome;
	private String currency;

	public Transaction() {
		super();
	}

	public Transaction(int transactionID, int userID, boolean recurrence, String date, String detail, BigDecimal amount,
			int categoryID, String categoryName, boolean isIncome) {
		super();
		this.transactionID = transactionID;
		this.userID = userID;
		this.recurrence = recurrence;
		this.date = date;
		this.detail = detail;
		this.amount = amount;
		this.categoryID = categoryID;
		this.categoryName = categoryName;
		this.isIncome = isIncome;
	}

	public int getTransactionID() {
		return transactionID;
	}

	public void setTransactionID(int transactionID) {
		this.transactionID = transactionID;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
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
		return (this.isIncome()) ? "Income" : "Expense";
	}

	public boolean isRecurrence() {
		return recurrence;
	}

	public void setRecurrence(boolean recurrence) {
		this.recurrence = recurrence;
	}

	public int getRecurrence() {
		return (this.recurrence) ? 1 : -1;
	}
	
	public String getRecurrenceType() {
		return (this.getRecurrence() == 1) ? "Yes" : "No";
	}

	public int compareTo(Transaction t) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			if (sdf.parse(this.getDate()).before(sdf.parse(t.getDate()))) {
				return -1;
			} else if (sdf.parse(this.getDate()).equals(sdf.parse(t.getDate()))) {
				return (this.getTransactionID() < t.getTransactionID()) ? -1 : 1; // Order
																					// by
																					// Insertion
																					// rather
																					// than
																					// date.
			} else {
				return 1;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return 1;
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

	@Override
	public String toString() {
		return "Transaction [transactionID=" + transactionID + ", userID=" + userID + ", recurrence=" + recurrence
				+ ", date=" + date + ", detail=" + detail + ", amount=" + amount + ", categoryID=" + categoryID
				+ ", categoryName=" + categoryName + ", isIncome=" + isIncome + ", currency=" + currency + "]";
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getCurrency() {
		return this.currency;
	}
}
