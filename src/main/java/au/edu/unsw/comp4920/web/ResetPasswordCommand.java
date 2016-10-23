package au.edu.unsw.comp4920.web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import au.edu.unsw.comp4920.common.Common;
import au.edu.unsw.comp4920.common.CommonDAO;
import au.edu.unsw.comp4920.common.Constants;
import au.edu.unsw.comp4920.common.MailHelper;
import au.edu.unsw.comp4920.objects.User;
import au.edu.unsw.comp4920.web.Command;

public class ResetPasswordCommand implements Command {

	public ResetPasswordCommand() {

	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response, CommonDAO dao) throws ServletException, IOException {
		System.out.println("Inside: ResetPasswordCommand");
		
		String action = request.getParameter(Constants.ACTION) == null ? null : request.getParameter(Constants.ACTION).toString();
		System.out.println("ResetPasswordCommand: Action is " + action);
		
		String input_token = request.getParameter(Constants.TOKEN);
		String username = request.getParameter(Constants.USERNAME);	
		String password = request.getParameter("password");	
		
		if (input_token != null && username != null){
			User user = dao.getUserDetails(username);
			
			if (user == null) {
				System.err.println("ResetPasswordCommand: User not found in database");
				
				request.setAttribute(Constants.ERROR, 2);
				request.setAttribute(Constants.ERRORMSG, "Invalid User Account!");
			}
			else{
				if (user.getStatusID() != 2){
					System.err.println("ResetPasswordCommand: Invalid Status ID");
					
					request.setAttribute(Constants.ERROR, 1);
					request.setAttribute(Constants.ERRORMSG, "Your Account is not in a valid State!");
				}
				else{
					if (action != null && action.equalsIgnoreCase("reset")) {	
						String user_token = dao.getToken(user);
						
						if (password != null){
							if (password.length() < 6) {
								System.err.println("ResetPasswordCommand: Password less than 6 characters.");
								request.setAttribute(Constants.ERROR, 1);
								request.setAttribute(Constants.ERRORMSG, "Your password must be at least 6 characters long!");
							} 
							else if (!password.matches(".*[!@#$%^&*()<>\\?,\\./\\-_+=]+.*")) {
								System.err.println("ResetPasswordCommand: Password does not contain special characters.");
								request.setAttribute(Constants.ERROR, 1);
								request.setAttribute(Constants.ERRORMSG, "Your password must contains at least 1 non-alphanumeric character(s)!");
							} 
							else if (user_token.equals(input_token)) {
								HttpSession session = request.getSession(false);
								if (session != null){
									session.invalidate();
								}
								
								String newPassword = request.getParameter("password");
								String hashedPassword = Common.hashPassword(newPassword, user.getSaltHash());
								System.out.println("hashed password is " + hashedPassword);
								dao.setPassword(user, hashedPassword);
								dao.setToken(user, "");
								System.out.println("ResetPasswordCommand: Password reset successful");
								
								System.out.println("sending email to " + user.getEmail());
								
								String content = "Hi " + user.getFirstName() + "," + "<br/><br/>";
								content += "You have successfully resetted your password for your WalletHero account.";
								content += "<br/><br/>";
								content += "Have fun, and don't hesitate to contact us with your feedback.";
								content += "<br/><br/>";
								content += "WalletHero Team";
								content += "<br/><br/>";
								content += Constants.SERVER;
								
								MailHelper mh = new MailHelper();
								mh.sendEmail(user.getEmail(), "WalletHero - Successful Reset Password", content);
								
								response.sendRedirect(Constants.ROUTER + Constants.RESETPASSWORD_COMMAND + "&success=yes");
								return;
							} 
							else {
								System.err.println("ResetPasswordCommand: Invalid token");
								request.setAttribute(Constants.ERROR, 1);
								request.setAttribute(Constants.ERRORMSG, "Token is invalid!");
							}
						}
						else{
							System.err.println("ResetPasswordCommand: Invalid input");

							request.setAttribute(Constants.ERROR, 1);
							request.setAttribute(Constants.ERRORMSG, "Missing Required Information!");
						}
					}
				}	
			}			
		}
		else if (action != null){
			System.err.println("ResetPasswordCommand: Invalid input");
			request.setAttribute(Constants.ERROR, 2);
			request.setAttribute(Constants.ERRORMSG, "Missing Required Information!");
		}

		RequestDispatcher rd = request.getRequestDispatcher("/resetpassword.jsp");
		rd.forward(request, response);
	}
}
