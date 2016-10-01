package au.edu.unsw.comp4920.objects;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Preference {
	private int id;
	private int userId;
	private int age;
	private String gender;
	private Currency currency;
	private Occupation occupation;
	
	public Preference() {
		currency = new Currency();
		occupation = new Occupation();
	}
	
	public Preference(ResultSet rs) throws SQLException {
		id = rs.getInt("id");
		userId = rs.getInt("user_id");
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
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
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
	public int getCurrencyId() {
		return currency.getCurrencyID();
	}
	public void setCurrencyId(int id) {
		currency.setCurrencyID(id);
	}
	public Occupation getOccupation() {
		return occupation;
	}
	public void setOccupation(Occupation occupation) {
		this.occupation = occupation;
	}
	public int getOccupationId() {
		return occupation.getOccupationID();
	}
	public void setOccupationId(int id) {
		occupation.setOccupationID(id);
	}
	public Preference clone() {
		Preference newP = new Preference();
		newP.setId(id);
		newP.setUserId(userId);
		newP.setGender(gender);
		newP.setAge(age);
		Currency c = new Currency(currency.getCurrencyID(), currency.getShortName(), currency.getLongName());
		newP.setCurrency(c);
		Occupation o = new Occupation(occupation.getOccupationID(), occupation.getName());
		newP.setOccupation(o);
		return newP;
	}
	
}
