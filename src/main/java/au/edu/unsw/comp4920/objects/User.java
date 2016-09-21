package au.edu.unsw.comp4920.objects;

import java.sql.ResultSet;
import java.sql.SQLException;

public class User {
	private int userID;
	private String username;
	private String email;
	private String password;
	private String saltHash;
	private String firstName;
	private String middleName;
	private String lastName;
	private String token;
	private int statusID;
	private double budget;
	
	public User(){
		
	}

	public User(int userID, String username, String email, String password, String saltHash, String firstName,
			String middleName, String lastName, String token, int statusID, double budget) {
		super();
		this.userID 	= userID;
		this.username 	= username;
		this.email 		= email;
		this.password 	= password;
		this.saltHash 	= saltHash;
		this.firstName 	= firstName;
		this.middleName = middleName;
		this.lastName 	= lastName;
		this.token 		= token;
		this.statusID 	= statusID;
		this.budget 	= budget;
	}
	
	public User(ResultSet rs) throws SQLException {
		try {
			this.userID 	= rs.getInt("id");
			this.username 	= rs.getString("username");
			this.password 	= rs.getString("email");
			this.saltHash 	= rs.getString("saltHash");
			this.firstName 	= rs.getString("first_name");
			this.middleName = rs.getString("middle_name");
			this.lastName 	= rs.getString("last_name");
			this.token 		= rs.getString("token");
			this.statusID 	= rs.getInt("statusID");
			this.budget 	= rs.getDouble("budget");
		} catch (SQLException e) {
			throw e;
		}
		
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSaltHash() {
		return saltHash;
	}

	public void setSaltHash(String saltHash) {
		this.saltHash = saltHash;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public int getStatusID() {
		return statusID;
	}

	public void setStatusID(int statusID) {
		this.statusID = statusID;
	}

	public double getBudget() {
		return budget;
	}

	public void setBudget(double budget) {
		this.budget = budget;
	}

	@Override
	public String toString() {
		return "User [userID=" + userID + ", username=" + username + ", email=" + email + ", password=" + password
				+ ", saltHash=" + saltHash + ", firstName=" + firstName + ", middleName=" + middleName + ", lastName="
				+ lastName + ", token=" + token + ", statusID=" + statusID + ", budget=" + budget + "]";
	}
}
