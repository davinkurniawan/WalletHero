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
 * @author Timothy
 *
 */
public class EmailValidationCommand implements Command {

	public EmailValidationCommand() {
		
	}
	
	public void execute(HttpServletRequest request, HttpServletResponse response, CommonDAO dao) throws ServletException, IOException{
		System.out.println("Inside: EmailValidationCommand");
		
		String input_token = request.getParameter(Constants.TOKEN);
		String username = request.getParameter(Constants.USERNAME);
				
		if (input_token != null && username != null){
			User user = dao.getUserDetails(username);
			
			if (user == null) {
				System.err.println("EmailValidationCommand: User not found in database");
				
				request.setAttribute(Constants.ERROR, 1);
				request.setAttribute(Constants.ERRORMSG, "Invalid User Account!");
			}
			else{
				String user_token = user.getToken();
				
				if (user.getStatusID() == 2){
					System.err.println("EmailValidationCommand: Invalid Status ID");
					
					request.setAttribute(Constants.ERROR, 0);
					request.setAttribute(Constants.ERRORMSG, "Your Account has already been Activated!");
				}
				else if (user.getStatusID() == 3){
					System.err.println("EmailValidationCommand: Invalid Status ID");
					
					request.setAttribute(Constants.ERROR, 0);
					request.setAttribute(Constants.ERRORMSG, "Your Account is Disabled!");
				}
				else if (user_token.equals(input_token)) {
					dao.setStatus(user, 2);
					dao.setToken(user, "");
					
					request.setAttribute(Constants.ERROR, 0);
					request.setAttribute(Constants.ERRORMSG, "Your Email has been Validated!");
				} 
				else {
					System.err.println("EmailValidationCommand: Invalid token");
					
					request.setAttribute(Constants.ERROR, 1);
					request.setAttribute(Constants.ERRORMSG, "Invalid Token!");
				}
			}
		}
		else{
			System.err.println("EmailValidationCommand: Invalid input");

			request.setAttribute(Constants.ERROR, 1);
			request.setAttribute(Constants.ERRORMSG, "Missing Required Information!");
		}
		
		RequestDispatcher rd = request.getRequestDispatcher("/emailvalidation.jsp");
		rd.forward(request, response);
	}	
}
