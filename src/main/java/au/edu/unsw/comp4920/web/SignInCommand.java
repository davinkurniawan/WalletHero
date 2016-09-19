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
	
	public void execute(HttpServletRequest request, HttpServletResponse response, CommonDAO dao) throws ServletException, IOException{

		String nextPage = "/signin.jsp";
		System.out.println("Inside: SignInCommand");
		String id = request.getParameter("id");
		String password = request.getParameter("password");
		User u = dao.getUser(id, password);
		if (u == null) {
			request.setAttribute(Constants.ERROR, 1);
			request.setAttribute(Constants.ERRORMSG, "Login failed. Incorrect ID or password.");
		} else if (u.getStatus_id() == 2) {
			request.setAttribute(Constants.ERROR, 1);
			request.setAttribute(Constants.ERRORMSG, "Login failed. Please activate your account.");
		} else {
			HttpSession session = request.getSession(true);
			session.setAttribute(Constants.PERSONID, u.getPersonID());
			session.setAttribute(Constants.SID, session.getId());
				
			Session s = new Session();
			s.setSessionId(session.getId());
			s.setUserId((Integer)session.getAttribute(Constants.PERSONID));
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			Date date = new Date();
			s.setLastAccess(df.format(date));
				
			dao.createSession(s);
			nextPage = "/home.jsp";
		}
		RequestDispatcher rd = request.getRequestDispatcher(nextPage);
		rd.forward(request, response);
	}	
}
