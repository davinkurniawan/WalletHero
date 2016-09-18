package au.edu.unsw.comp4920.web;

import java.io.IOException;
import java.util.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import au.edu.unsw.comp4920.common.CommonDAO;
import au.edu.unsw.comp4920.objects.*;
import au.edu.unsw.comp4920.common.Common;
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
		String action = request.getParameter(Constants.ACTION);
		if (action != null && action.equalsIgnoreCase("update")) {
			User user = new User();
			user.setUsername(request.getParameter("username"));
			user.setEmail(request.getParameter("email"));
			user.setFirstName(request.getParameter("firstname"));
			user.setLastName(request.getParameter("lastname"));
			if (dao.createUser(user)) {
				// Send email here  
				String token = Common.generateToken(request.getParameter("username") + request.getParameter("email") + request.getParameter("password"));
				MailHelper mail = new MailHelper();
				String content = Constants.SERVER + Constants.ROUTER + Constants.VALIDATE_COMMAND;
				content += "&username" + "=" + user.getUsername() + "&token" + "=" + token;
				mail.sendEmailValidation(user.getEmail(), "Validate your email account", content, user.getUsername());
				
				// Redirect to appropriate page: public view
				response.sendRedirect(Constants.ROUTER + Constants.SIGNUP_COMMAND + "&success=yes");
				return;
			} else {
				// Let JSP know error happens
				request.setAttribute(Constants.ERROR, true);
				request.setAttribute(Constants.ACTION, true);
				request.setAttribute(Constants.ERRORMSG, "User already exist.");
			}
		}
		RequestDispatcher rd = request.getRequestDispatcher("/signup.jsp");
		rd.forward(request, response);
	}	
}
