package au.edu.unsw.comp4920.objects;

import au.edu.unsw.comp4920.common.Constants;

public class User {
	private int personID;
	private String username;
	private String password;
	private String nickName;
	private String firstName;
	private String lastName;
	private String email;
	private String address;
	private String creditCard;
	private String token;
	private String type;
	
	public User() {
		
	}
	
	public User(int personID, 
				String username, 
				String password, 
				String nickName, 
				String firstName, 
				String lastName, 
				String email, 
				String address, 
				String creditCard, 				
				String token,
				String type) {
		super();

		this.personID = personID;
		this.username = username;
		this.password = password;
		this.nickName = nickName;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.address = address;
		this.creditCard = creditCard;
		this.token = token;
		this.type = type;
	}
	
	//TODO
}
