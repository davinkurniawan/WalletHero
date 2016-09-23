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

public class ForgotPasswordCommand implements Command {
	
	public ForgotPasswordCommand() {

	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response, CommonDAO dao) throws ServletException, IOException {
		System.out.println("Inside: ForgotPasswordCommand");

		String action = request.getParameter(Constants.ACTION) == null ? null : request.getParameter(Constants.ACTION).toString();
		System.out.println("ForgotPasswordCommand: Action is " + action);
		
		if (action != null && action.equalsIgnoreCase("recovery")) {
			
			String userinfo = request.getParameter("username");
			String firstname = request.getParameter("firstname");
			String lastname = request.getParameter("lastname");
			User user = null;
			
			if (userinfo != null && firstname != null && lastname != null) {
				user = dao.getUser(userinfo, firstname, lastname);
				
				if (user != null) {
					if (user.getStatusID() != 2) {
						System.err.println("ForgotPasswordCommand: User account is not activated.");
						request.setAttribute(Constants.ERROR, 1);
						request.setAttribute(Constants.ERRORMSG, "User account is not yet activated!");
					}
					
					String token = UUID.randomUUID().toString();
					dao.setToken(user, token);
					System.out.println("Token: " + token);
					
					System.out.println("sending email to " + user.getEmail());
					// Send email here 
					
					String content = "Hi " + user.getFirstName() + "," + "<br/><br/>";
					content += "You recently requested to reset your password for your WalletHero account. ";
					content += "Click the link below to reset it.<br/>";
					content += Constants.SERVER + Constants.ROUTER + Constants.RESETPASSWORD_COMMAND;
					content += "&username" + "=" + user.getUsername() + "&token"+ "=" + token;
					content += "<br/><br/>";
					content += "If you did not request a password reset, please ignore this email.";
					content += "<br/><br/>";
					content += "Regards,<br/>";
					content += "WalletHero Team";
					
					MailHelper mh = new MailHelper();
					mh.sendEmail(user.getEmail(), "WalletHero Password Recovery", content);
	
					// Redirect to appropriate page
					response.sendRedirect(Constants.ROUTER + Constants.FORGOTPASSWORD_COMMAND + "&success=yes");
					return;
				} else {
					System.err.println("ForgotPasswordCommand: Invalid credentials.");
					request.setAttribute(Constants.ERROR, 1);
					request.setAttribute(Constants.ERRORMSG, "Sorry, no matching user with supplied credentials!");
				}
			}
		}
		
		// Display forgot password page
		RequestDispatcher rd = request.getRequestDispatcher("/forgotpassword.jsp");
		rd.forward(request, response);
	}
}
