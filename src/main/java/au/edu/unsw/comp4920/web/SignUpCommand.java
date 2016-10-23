package au.edu.unsw.comp4920.web;

import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.UUID;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import au.edu.unsw.comp4920.common.Common;
import au.edu.unsw.comp4920.common.CommonDAO;
import au.edu.unsw.comp4920.common.Constants;
import au.edu.unsw.comp4920.common.MailHelper;
import au.edu.unsw.comp4920.objects.User;

/**
 * 
 *
 */
public class SignUpCommand implements Command {

	public SignUpCommand() {
		
	}
	
	public void execute(HttpServletRequest request, HttpServletResponse response, CommonDAO dao) throws ServletException, IOException {
		System.out.println("Inside: SignUpCommand");
		
		String action = request.getParameter(Constants.ACTION) == null ? null : request.getParameter(Constants.ACTION).toString();
		System.out.println("SignUpCommand: Action is " + action);

		if (action != null && action.equalsIgnoreCase("signUp")) {
		
			if (request.getParameter("username") != null && 
				request.getParameter("password") != null &&
				request.getParameter("repassword") != null &&
				request.getParameter("email") != null &&
				request.getParameter("firstname") != null &&
				request.getParameter("middlename") != null &&
				request.getParameter("lastname") != null) {
			
				if (request.getParameter("username").length() < 6 || request.getParameter("username").length() > 32) {
					System.err.println("SignUpCommand: Username must be between 6 to 32 characters.");
					request.setAttribute(Constants.ERROR, 1);
					request.setAttribute(Constants.ERRORMSG, "Username must be between 6 to 32 characters!");
				} 
				else if (request.getParameter("username").matches(".*[^A-Za-z0-9_.]+.*")) {
					System.err.println("SignUpCommand: Username can only contains alphanumeric characters, underscore, and full stop.");
					request.setAttribute(Constants.ERROR, 1);
					request.setAttribute(Constants.ERRORMSG, "Username can only contains alphanumeric characters, underscore, and full stop!");
				} 
				else if (request.getParameter("password").length() < 6) {
					System.err.println("SignUpCommand: Password less than 6 characters.");
					request.setAttribute(Constants.ERROR, 1);
					request.setAttribute(Constants.ERRORMSG, "Your password must be at least 6 characters long!");
				} 
				else if (!request.getParameter("password").matches(".*[!@#$%^&*()<>\\?,\\./\\-_+=]+.*")) {
					System.err.println("SignUpCommand: Password does not contain special characters.");
					request.setAttribute(Constants.ERROR, 1);
					request.setAttribute(Constants.ERRORMSG, "Your password must contains at least 1 non-alphanumeric character(s)!");
				} 
				else if (request.getParameter("repassword").length() < 6) {
					System.err.println("SignUpCommand: Password less than 6 characters.");
					request.setAttribute(Constants.ERROR, 1);
					request.setAttribute(Constants.ERRORMSG, "Your retyped password must be at least 6 characters long!");
				} 
				else if (!request.getParameter("repassword").matches(".*[!@#$%^&*()<>\\?,\\./-_+=]+.*")) {
					System.err.println("SignUpCommand: Password does not contain special characters.");
					request.setAttribute(Constants.ERROR, 1);
					request.setAttribute(Constants.ERRORMSG, "Your retyped password must contains at least 1 non-alphanumeric character(s)!");
				} 
				else if (!request.getParameter("password").equals(request.getParameter("repassword"))) {
					System.err.println("SignUpCommand: Password and re-password did not match.");
					request.setAttribute(Constants.ERROR, 1);
					request.setAttribute(Constants.ERRORMSG, "Your password did not match the retyped password!");
				} else {
					System.out.println("SignUpCommand: Creating user");
					
					if (dao.getUserDetails(request.getParameter("username")) == null){
	
						User user = new User();
						user.setUsername(request.getParameter("username"));
						user.setEmail(request.getParameter("email"));
						user.setFirstName(request.getParameter("firstname"));
						user.setMiddleName(request.getParameter("middlename"));
						user.setLastName(request.getParameter("lastname"));
						user.setSaltHash(generateSalt());
						user.setPassword(Common.hashPassword(request.getParameter("password"), user.getSaltHash()));
						
						String token = UUID.randomUUID().toString();
						user.setToken(token);
						System.out.println("Token: " + token);
						
						if(dao.createUser(user)) {
							user = dao.getUserDetails(user.getUsername());
							System.out.println("set default user details");
							if (dao.createDefaultUserDetails(user.getUserID())){
							
								System.out.println("sending email");
								
								String content = "Hi " + user.getFirstName() + "," + "<br/><br/>";
								content += "Thanks for signing up with WalletHero! You must follow this link to activate your account:";
								content += "<br/><br/>";
								content += Constants.SERVER + Constants.ROUTER + Constants.VALIDATE_COMMAND;
								content += "&username" + "=" + user.getUsername() + "&token"+ "=" + token;
								content += "<br/><br/>";
								content += "Have fun, and don't hesitate to contact us with your feedback.";
								content += "<br/><br/>";
								content += "WalletHero Team";
								content += "<br/><br/>";
								content += Constants.SERVER;
								
								MailHelper mh = new MailHelper();
								mh.sendEmail(user.getEmail(), "Welcome to WalletHero. Please confirm your account on WalletHero.", content);
		
								// Redirect to appropriate page: public view
								response.sendRedirect(Constants.ROUTER + Constants.SIGNUP_COMMAND + "&success=yes");
								return;
							}
							else{
								//Do a rollback!!!
								dao.deleteUser(user.getUserID());
								
								System.err.println("SignUpCommand: Failed to create user details account!");
								request.setAttribute(Constants.ERROR, 1);
								request.setAttribute(Constants.ERRORMSG, "Failed to create an account for you!");
							}
						}
						else{
							System.err.println("SignUpCommand: Failed to create user account!");
							request.setAttribute(Constants.ERROR, 1);
							request.setAttribute(Constants.ERRORMSG, "Failed to create an account for you!");
						}
					}
					else{
						System.err.println("SignUpCommand: User account exists!");
						request.setAttribute(Constants.ERROR, 1);
						request.setAttribute(Constants.ERRORMSG, "Username has been used before!");
					}
				}
			}
		}
		
		RequestDispatcher rd = request.getRequestDispatcher("/signup.jsp");
		rd.forward(request, response);
	}
	
	private String generateSalt() {
		SecureRandom random = new SecureRandom();
		return new BigInteger(130, random).toString(32);
	}
}
