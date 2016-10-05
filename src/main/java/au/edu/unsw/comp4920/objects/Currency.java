package au.edu.unsw.comp4920.objects;

public class Currency {
	private int currencyID;
	private String shortName;
	private String longName;
	
	public Currency() {
		super();
	}
	
	public Currency(int currencyID, String shortName, String longName) {
		super();
		this.currencyID = currencyID;
		this.shortName = shortName;
		this.longName = longName;
	}

	public int getCurrencyID() {
		return currencyID;
	}

	public void setCurrencyID(int currencyID) {
		this.currencyID = currencyID;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getLongName() {
		return longName;
	}

	public void setLongName(String longName) {
		this.longName = longName;
	}

	@Override
	public String toString() {
		return "Currency [currencyID=" + currencyID + ", shortName=" + shortName + ", longName=" + longName + "]";
	}
}
