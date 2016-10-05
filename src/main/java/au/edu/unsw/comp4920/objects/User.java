package au.edu.unsw.comp4920.objects;

import java.sql.ResultSet;
import java.sql.SQLException;

public class User {
	private int userID;
	private String username;
	private String email;
	private String password;
	private String salt_hash;
	private String first_name;
	private String middle_name;
	private String last_name;
	private String token;
	private int status_id;
	private double budget;
	
	public User(){
		super();
	}

	public User(int userID, String username, String email, String password, String salt_hash, String first_name,
			String middle_name, String last_name, String token, int status_id, double budget) {
		super();
		this.userID 	 = userID;
		this.username 	 = username;
		this.email 		 = email;
		this.password 	 = password;
		this.salt_hash 	 = salt_hash;
		this.first_name  = first_name;
		this.middle_name = middle_name;
		this.last_name 	 = last_name;
		this.token 		 = token;
		this.status_id 	 = status_id;
		this.budget 	 = budget;
	}
	
	public User(ResultSet rs) throws SQLException {
		try {
			this.userID 	 = rs.getInt("id");
			this.username 	 = rs.getString("username");
			this.email		 = rs.getString("email");
			this.password 	 = rs.getString("password");
			this.salt_hash 	 = rs.getString("salt_hash");
			this.first_name  = rs.getString("first_name");
			this.middle_name = rs.getString("middle_name");
			this.last_name 	 = rs.getString("last_name");
			this.token 		 = rs.getString("token");
			this.status_id 	 = rs.getInt("status_id");
			this.budget 	 = rs.getDouble("budget");
		} 
		catch (SQLException e) {
			throw e;
		}	
	}
	
	public User(User u) {		
		super();
		this.userID 	 = u.getUserID();
		this.username 	 = u.getUsername();
		this.email 		 = u.getEmail();
		this.password 	 = u.getPassword();
		this.salt_hash 	 = u.getSaltHash();
		this.first_name  = u.getFirstName();
		this.middle_name = u.getMiddleName();
		this.last_name 	 = u.getLastName();
		this.token 		 = u.getToken();
		this.status_id 	 = u.getStatusID();
		this.budget 	 = u.getBudget();
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
		return salt_hash;
	}

	public void setSaltHash(String salt_hash) {
		this.salt_hash = salt_hash;
	}

	public String getFirstName() {
		return first_name;
	}

	public void setFirstName(String first_name) {
		this.first_name = first_name;
	}

	public String getMiddleName() {
		return middle_name;
	}

	public void setMiddleName(String middle_name) {
		this.middle_name = middle_name;
	}

	public String getLastName() {
		return last_name;
	}

	public void setLastName(String last_name) {
		this.last_name = last_name;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public int getStatusID() {
		return status_id;
	}

	public void setStatusID(int status_id) {
		this.status_id = status_id;
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
				+ ", salt_hash=" + salt_hash + ", first_name=" + first_name + ", middle_name=" + middle_name + ", last_name="
				+ last_name + ", token=" + token + ", status_id=" + status_id + ", budget=" + budget + "]";
	}
}
