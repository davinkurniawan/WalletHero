package au.edu.unsw.comp4920.web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import au.edu.unsw.comp4920.common.CommonDAO;

/**
 * @author Timothy
 *
 */
public class SignOutCommand implements Command {

	public SignOutCommand() {
		
	}
	
	public void execute(HttpServletRequest request, HttpServletResponse response, CommonDAO dao) throws ServletException, IOException{
		System.out.println("Inside: SignOutCommand");

		HttpSession session = request.getSession(false);
		if (session != null) {
			dao.deleteSession(request.getSession().getId());
			session.invalidate();
			System.out.println("Session invalidated.");
		}
		
		RequestDispatcher rd = request.getRequestDispatcher("/index.jsp");
		rd.forward(request, response);
	}	
}
