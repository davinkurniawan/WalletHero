package au.edu.unsw.comp4920.web;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
				request.getParameter("first_name") != null &&
				request.getParameter("middle_name") != null &&
				request.getParameter("last_name") != null) {
			
				if (request.getParameter("password").length() < 6) {
					System.err.println("SignUpCommand: Password less than 6 characters.");
					request.setAttribute(Constants.ERROR, 1);
					request.setAttribute(Constants.ERRORMSG, "Your password must be at least 6 characters long.");
				} else if (!request.getParameter("password").matches(".*[!@#$%^&*()<>?,./]+.*")) {
					System.err.println("SignUpCommand: Password does not contain special characters.");
					request.setAttribute(Constants.ERROR, 1);
					request.setAttribute(Constants.ERRORMSG, "Your password must contains at least 1 non-alphanumeric character(s).");
				} else if (!request.getParameter("password").equals(request.getParameter("repassword"))) {
					System.err.println("SignUpCommand: Password and re-password did not match.");
					request.setAttribute(Constants.ERROR, 1);
					request.setAttribute(Constants.ERRORMSG, "Your password did not match the re-typed password.");
				} else {
					System.out.println("SignUpCommand: Creating user");
					
					if (dao.getUserDetails(request.getParameter("username")) == null){
	
						User user = new User();
						user.setUsername(request.getParameter("username"));
						user.setPassword(request.getParameter("password"));
						user.setEmail(request.getParameter("email"));
						user.setFirstName(request.getParameter("first_name"));
						user.setMiddleName(request.getParameter("middle_name"));
						user.setLastName(request.getParameter("last_name"));
						
						String token = UUID.randomUUID().toString();
						user.setToken(token);
						System.out.println("Token: " + token);
						
						if(dao.createUser(user)) {
							System.out.println("sending email");
							// Send email here 
							
							String content = "Hi " + user.getFirstName() + "," + "<br/><br/>";
							content += "Please confirm your email using the following link below" + "<br/>";
							content += Constants.SERVER + Constants.ROUTER + Constants.VALIDATE_COMMAND;
							content += "&username" + "=" + user.getUsername() + "&token"+ "=" + token;
							content += "<br/><br/>";
							content += "Regards,<br/>";
							content += "Code4Food Team";
							
							MailHelper mh = new MailHelper();
							mh.sendEmail(user.getEmail(), "Welcome to WalletHero. Please validate Your Email Account.", content);
	
							// Redirect to appropriate page: public view
							response.sendRedirect(Constants.ROUTER + Constants.SIGNUP_COMMAND + "&success=yes");
							return;
						}
						else{
							System.err.println("SignUpCommand: Failed to create user account!");
							request.setAttribute(Constants.ERROR, 1);
							request.setAttribute(Constants.ERRORMSG, "Failed to create user account!");
						}
					}
					else{
						System.err.println("SignUpCommand: User account exists!");
						request.setAttribute(Constants.ERROR, 1);
						request.setAttribute(Constants.ERRORMSG, "User account exists!");
					}
				}
			}
		}
		
		RequestDispatcher rd = request.getRequestDispatcher("/signup.jsp");
		rd.forward(request, response);
	}	
}
