package au.edu.unsw.comp4920.objects;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Preference {
	private int preferenceID;
	private int userID;
	private int age;
	private String gender;
	private Currency currency;
	private Occupation occupation;
	private String deals;
	private boolean get_deals_email;
	
	public Preference() {
		super();
		this.currency = new Currency();
		this.occupation = new Occupation();
	}
	
	public Preference(int preferenceID, int userID, int age, String gender, Currency currency, Occupation occupation, String deals, boolean get_deals_email) {
		super();	
		this.preferenceID = preferenceID;
		this.userID = userID;
		this.age = age;
		this.gender = gender;
		this.currency = currency;
		this.occupation = occupation;
		this.deals = deals;
		this.get_deals_email = get_deals_email;
	}

	public Preference(ResultSet rs) throws SQLException {
		preferenceID = rs.getInt("id");
		userID = rs.getInt("user_id");
		age = rs.getInt("age");
		gender = rs.getString("gender");
		
		currency = new Currency();
		currency.setCurrencyID(rs.getInt("currency_id"));
		currency.setShortName(rs.getString("short_name"));
		currency.setLongName(rs.getString("long_name"));
		
		occupation = new Occupation();
		occupation.setOccupationID(rs.getInt("occupation_id"));
		occupation.setName(rs.getString("name"));
		
		deals = rs.getString("deals");
		get_deals_email = rs.getBoolean("get_deals_email");
	}
	
	public int getPreferenceID() {
		return preferenceID;
	}

	public void setPreferenceID(int preferenceID) {
		this.preferenceID = preferenceID;
	}

	public int getUserID() {
		return userID;
	}
	
	public void setUserID(int userID) {
		this.userID = userID;
	}
	
	public int getAge() {
		return age;
	}
	
	public void setAge(int age) {
		this.age = age;
	}
	
	public String getGender() {
		return gender;
	}
	
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public Currency getCurrency() {
		return currency;
	}
	
	public void setCurrency(Currency currency) {
		this.currency = currency;
	}
	
	public int getCurrencyID() {
		return currency.getCurrencyID();
	}
	
	public void setCurrencyID(int currencyID) {
		currency.setCurrencyID(currencyID);
	}
	
	public Occupation getOccupation() {
		return occupation;
	}
	
	public void setOccupation(Occupation occupation) {
		this.occupation = occupation;
	}
	
	public int getOccupationID() {
		return occupation.getOccupationID();
	}
	
	public void setOccupationID(int occupationID) {
		occupation.setOccupationID(occupationID);
	}
	
	public boolean isGet_deals_email() {
		return get_deals_email;
	}

	public void setGet_deals_email(boolean get_deals_email) {
		this.get_deals_email = get_deals_email;
	}

	public Preference clone() {
		Preference newP = new Preference();
		newP.setPreferenceID(preferenceID);
		newP.setUserID(userID);
		newP.setGender(gender);
		newP.setAge(age);
		Currency c = new Currency(currency.getCurrencyID(), currency.getShortName(), currency.getLongName());
		newP.setCurrency(c);
		Occupation o = new Occupation(occupation.getOccupationID(), occupation.getName());
		newP.setOccupation(o);
		newP.setDeals(deals);;
		newP.setGet_deals_email(get_deals_email);
		return newP;
	}

	public String getDeals() {
		return deals;
	}
	
	public String[] getDealsList() {
		if (deals == null || deals.length() == 0){
			return new String[0];
		}
		
		return deals.split(",");
	}
	
	public ArrayList<String> getDealsArrayList() {
		ArrayList<String> result = new ArrayList<String>();

		if (deals != null && deals.length() != 0){
			String[] thisdeals = deals.split(",");
			
			for (String d: thisdeals) {
				if (!d.isEmpty())
					result.add(d);
			}
		}
		
		return result;
	}

	public void setDeals(String deals) {
		this.deals = deals;
	}
	
	public void setDeals(String[] deals) {
		String results = "";
		for (int i = 0; i < deals.length; i++) {
			results += deals[i] + ",";
		}
		this.deals = results;
	}
	
	public void setDeals(ArrayList<String> deals) {
		String results = "";
		for (int i = 0; i < deals.size(); i++) {
			results += deals.get(i) + ",";
		}
		this.deals = results;
	}

	@Override
	public String toString() {
		return "Preference [preferenceID=" + preferenceID + ", userID=" + userID + ", age=" + age + ", gender=" + gender
				+ ", currency=" + currency + ", occupation=" + occupation + ", deals=" + deals + ", get_deals_email="
				+ get_deals_email + "]";
	}
}
