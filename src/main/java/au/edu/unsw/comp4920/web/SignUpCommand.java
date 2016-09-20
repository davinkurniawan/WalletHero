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

		if (action != null && action.equalsIgnoreCase("create")) {
		
			if (request.getParameter("username") != null && 
				request.getParameter("password") != null &&
				request.getParameter("email") != null &&
				request.getParameter("first_name") != null &&
				request.getParameter("middle_name") != null &&
				request.getParameter("last_name") != null) {
			
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
					}
				}
				else{
					System.err.println("SignUpCommand: User account exists!");
				}
			}
		}
		
		RequestDispatcher rd = request.getRequestDispatcher("/signup.jsp");
		rd.forward(request, response);
	}	
}
