package au.edu.unsw.comp4920.web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import au.edu.unsw.comp4920.common.CommonDAO;
import au.edu.unsw.comp4920.common.Constants;
import au.edu.unsw.comp4920.objects.User;

/**
 * @author Timothy
 *
 */
public class ProfileCommand implements Command {

	public ProfileCommand() {
		
	}
	
	public void execute(HttpServletRequest request, HttpServletResponse response, CommonDAO dao) throws ServletException, IOException{
		System.out.println("Inside: ProfileCommand");
		
		String action = request.getParameter(Constants.ACTION) == null ? null : request.getParameter(Constants.ACTION).toString();
		System.out.println("SignInCommand: Action is " + action);

		if (action != null && action.equalsIgnoreCase("update_profile")) {
			//TODO stuff
		}
		else if (action != null && action.equalsIgnoreCase("update_password")) {
			//TODO stuff
		}
		else if (action != null && action.equalsIgnoreCase("update_preferences")) {
			//TODO stuff
		}
		else{
			HttpSession session = request.getSession();
			String sid = session.getAttribute(Constants.SID).toString();
			
			User user = dao.getUser(sid);
			session.setAttribute("user", user);

			request.setAttribute(Constants.USER, user);
		}
		
		RequestDispatcher rd = request.getRequestDispatcher("/profile.jsp");
		rd.forward(request, response);
	}	
}
