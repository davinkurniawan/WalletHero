package au.edu.unsw.comp4920.objects;

public class User {
	private int personID;
	private String username;
	private String email;
	private String password;
	private String salt_hash;
	private String firstName;
	private String middleName;
	private String lastName;
	private String token;
	private int status_id;
	private double budget;
	
	public User(){
		
	}

	public User(int personID, String username, String email, String password, String salt_hash, String firstName,
			String middleName, String lastName, String token, int status_id, double budget) {
		super();
		this.personID = personID;
		this.username = username;
		this.email = email;
		this.password = password;
		this.salt_hash = salt_hash;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.token = token;
		this.status_id = status_id;
		this.budget = budget;
	}

	public int getPersonID() {
		return personID;
	}

	public void setPersonID(int personID) {
		this.personID = personID;
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

	public String getSalt_hash() {
		return salt_hash;
	}

	public void setSalt_hash(String salt_hash) {
		this.salt_hash = salt_hash;
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

	public int getStatus_id() {
		return status_id;
	}

	public void setStatus_id(int status_id) {
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
		return "User [personID=" + personID + ", username=" + username + ", email=" + email + ", password=" + password
				+ ", salt_hash=" + salt_hash + ", firstName=" + firstName + ", middleName=" + middleName + ", lastName="
				+ lastName + ", token=" + token + ", status_id=" + status_id + ", budget=" + budget + "]";
	}
}
