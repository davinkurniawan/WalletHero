package au.edu.unsw.comp4920.objects;

/**
 * Basic transaction implementation, no support for recurring incomes and
 * expenses nor categories.
 * 
 * @author samt1995
 *
 */
public class Recurrence {
	private int transactionID;
	private String recurrenceFreq;
	private int recurrenceNumber;

	public Recurrence() {
		super();
	}
	
	public Recurrence(int transactionID, String recurrenceFreq, int recurrenceNumber) {
		super();
		this.transactionID = transactionID;
		this.recurrenceFreq = recurrenceFreq;
		this.recurrenceNumber = recurrenceNumber;
	}

	public int getTransactionID() {
		return transactionID;
	}

	public void setTransactionID(int transactionID) {
		this.transactionID = transactionID;
	}

	public String getRecurrenceFreq() {
		return recurrenceFreq;
	}

	public void setRecurrenceFreq(String recurrenceFreq) {
		this.recurrenceFreq = recurrenceFreq;
	}

	public int getRecurrenceNumber() {
		return recurrenceNumber;
	}

	public void setRecurrenceNumber(int recurrenceNumber) {
		this.recurrenceNumber = recurrenceNumber;
	}

	@Override
	public String toString() {
		return "Recurrence [transactionID=" + transactionID + ", recurrenceFreq=" + recurrenceFreq
				+ ", recurrenceNumber=" + recurrenceNumber + "]";
	}
}
