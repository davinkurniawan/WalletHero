package au.edu.unsw.comp4920.web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import au.edu.unsw.comp4920.common.CommonDAO;
import au.edu.unsw.comp4920.common.Constants;
import au.edu.unsw.comp4920.objects.*;

/**
 * @author Natalia
 *
 */
public class EmailUpdateCommand implements Command {

	public EmailUpdateCommand() {
		
	}
	
	public void execute(HttpServletRequest request, HttpServletResponse response, CommonDAO dao) throws ServletException, IOException{
		System.out.println("Inside: EmailUpdateCommand");
		
		String input_token = request.getParameter("token");
		String username = request.getParameter("username");
		String email = request.getParameter("email");
				
		if (input_token != null && username != null && email != null){
			User user = dao.getUser(username, null);
			
			if (user == null) {
				System.err.println("EmailUpdateCommand: User not found in database");
				
				request.setAttribute(Constants.ERROR, 1);
				request.setAttribute(Constants.ERRORMSG, "Invalid User Account!");
			}
			else{
				String user_token = user.getToken();
				
				if (user_token.equals(input_token)) {
					user.setToken("");
					user.setEmail(email);
					dao.updateUserEmail(user);
					
					request.setAttribute(Constants.ERROR, 0);
					request.setAttribute(Constants.ERRORMSG, "Your Email has been updated!");
				} 
				else {
					System.err.println("EmailUpdateCommand: Invalid token");
					request.setAttribute(Constants.ERROR, 1);
					request.setAttribute(Constants.ERRORMSG, "Invalid Token!");
				}
			}
		}
		else{
			System.err.println("EmailUpdateCommand: Invalid input");

			request.setAttribute(Constants.ERROR, 1);
			request.setAttribute(Constants.ERRORMSG, "Missing Information!");
		}
		
		RequestDispatcher rd = request.getRequestDispatcher("/signIn.jsp");
		request.setAttribute(Constants.SIGNIN_COMMAND, "active");
		rd.forward(request, response);
	}	
}