package au.edu.unsw.comp4920.objects;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Preference {
	private int preferenceID;
	private int userID;
	private int age;
	private String gender;
	private Currency currency;
	private Occupation occupation;
	
	public Preference() {
		currency = new Currency();
		occupation = new Occupation();
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
		return newP;
	}

	@Override
	public String toString() {
		return "Preference [preferenceID=" + preferenceID + ", userID=" + userID + ", age=" + age + ", gender=" + gender + ", currency="
				+ currency + ", occupation=" + occupation + "]";
	}
}
