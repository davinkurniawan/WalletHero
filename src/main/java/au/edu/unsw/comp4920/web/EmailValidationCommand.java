package au.edu.unsw.comp4920.web;

import java.io.IOException;
import java.util.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import au.edu.unsw.comp4920.common.CommonDAO;
import au.edu.unsw.comp4920.common.Constants;
import au.edu.unsw.comp4920.objects.*;

/**
 * @author Timothy
 *
 */
public class EmailValidationCommand implements Command {

	public EmailValidationCommand() {
		
	}
	
	public void execute(HttpServletRequest request, HttpServletResponse response, CommonDAO dao) throws ServletException, IOException{
		System.out.println("Inside: EmailValidationCommand");
		String input_token = request.getParameter("token");
		User user = dao.getUser(request.getParameter("username"), null);
		String nextPage = "/index.jsp";
		if (user == null) {
			System.out.println("EmailValidationCommand: User not found in database");
			
			request.setAttribute(Constants.ERROR, 1);
			request.setAttribute(Constants.ERRORMSG, "User does not exist.");
		} else {
			String user_token = dao.getToken(user);
			if (user_token.equals(input_token)) {
				dao.setStatus(user);
			} else {
				System.out.println("EmailValidationCommand: Invalid token");
				
				request.setAttribute(Constants.ERROR, 1);
				request.setAttribute(Constants.ERRORMSG, "Invalid token.");
			}
		}
		RequestDispatcher rd = request.getRequestDispatcher(nextPage);
		rd.forward(request, response);
	}	
}
