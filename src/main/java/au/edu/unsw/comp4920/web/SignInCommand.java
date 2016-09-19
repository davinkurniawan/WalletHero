package au.edu.unsw.comp4920.web;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import au.edu.unsw.comp4920.common.CommonDAO;
import au.edu.unsw.comp4920.common.Constants;
import au.edu.unsw.comp4920.objects.*;

public class SignInCommand implements Command {

	public SignInCommand() {

	}

	public void execute(HttpServletRequest request, HttpServletResponse response, CommonDAO dao)
			throws ServletException, IOException {
		System.out.println("Inside: SignInCommand");

		String username = request.getParameter("username");
		String password = request.getParameter("password");
		User user = null;
		System.out.println("Logging in ... " + "username = " + username + " password= " + password);

		if (username != null && password != null) {
			user = dao.getUser(username, password);
	
			// if user is not found in the database (returned null), then
			// either username/email and/or password is wrong
			if (user == null) {
				System.out.println("SignInCommand: User not found in database");
	
				request.setAttribute(Constants.ERROR, 1);
				request.setAttribute(Constants.ERRORMSG, "Invalid credentials.");
				
			} else if (user.getStatus_id() == 2) {
				System.out.println("SignInCommand: Active user");
	
				// TODO implement the hash
	
				HttpSession session = request.getSession(true);
				session.setAttribute(Constants.PERSONID, user.getPersonID());
				session.setAttribute(Constants.SID, session.getId());
	
				Session s = new Session();
				s.setSessionId(session.getId());
				s.setUserId((Integer) session.getAttribute(Constants.PERSONID));
				DateFormat df = new SimpleDateFormat(Constants.DEFAULT_DATE_FORMAT);
				Date date = new Date();
				s.setLastAccess(df.format(date));
	
				dao.createSession(s);
	
				System.out.println("Login successful.");
				response.sendRedirect(Constants.ROUTER + Constants.PUBLIC_COMMAND);
				return;
		
			} else if (user.getStatus_id() == 1) {
				System.out.println("SignInCommand: Not Actived user");
	
				request.setAttribute(Constants.ERROR, 1);
				request.setAttribute(Constants.ERRORMSG, "Login failed. Please activate your account.");
				
			} else if (user.getStatus_id() == 3) {
				System.out.println("SignInCommand: Disabled user");
	
				request.setAttribute(Constants.ERROR, 1);
				request.setAttribute(Constants.ERRORMSG, "Login failed. Your account has been disabled.");
			}
		}

		// Display log in page
		RequestDispatcher rd = request.getRequestDispatcher("/signin.jsp");
		rd.forward(request, response);
	}
}
