package au.edu.unsw.comp4920.web;

import java.io.IOException;
import java.util.UUID;

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

/**
 * @author Timothy, Natalia
 *
 */
public class ProfileCommand implements Command {
	
	private static enum Commands {PROFILE, PASSWORD, PREFERENCE};
	
	private static Commands commands (String s) {
		if (s == null) return null;
		if (s.equalsIgnoreCase("update_profile"    )) return Commands.PROFILE;
		if (s.equalsIgnoreCase("update_password"   )) return Commands.PASSWORD;
		if (s.equalsIgnoreCase("update_preferences")) return Commands.PREFERENCE;
		return null;
	}

	public ProfileCommand() {
		
	}
	
	public void execute(HttpServletRequest request, HttpServletResponse response, CommonDAO dao) throws ServletException, IOException{
		System.out.println("Inside: ProfileCommand");
		
		HttpSession session = request.getSession();
        String sid = session.getAttribute(Constants.SID).toString();
        String actionString = request.getParameter(Constants.ACTION) == null ? null : request.getParameter(Constants.ACTION).toString();
        System.out.println("SignInCommand: Action is " + actionString);
		
		User user = dao.getUser(sid);
		User updatedUser;
		
		Commands action = commands(request.getParameter("action"));
		if (action != null) {
			switch (action) {
				case PROFILE:
					String email 		= request.getParameter("email");
					String firstname 	= request.getParameter("firstname");
					String middlename 	= request.getParameter("middlename");
					String lastname 	= request.getParameter("lastname");
					
					if ( email == null || firstname == null || lastname == null) {
						request.setAttribute(Constants.ERROR, 1);
						request.setAttribute(Constants.ERRORMSG, "Values must not be empty!");
					}
					
					boolean same = true;
					
					updatedUser = new User(user);
					if (firstname != null && !firstname.equals(user.getFirstName())) {
						updatedUser.setFirstName(firstname);
						same = false;
					}
					if (middlename != null && !middlename.equals(user.getMiddleName())) {
						user.setMiddleName(middlename);
						same = false;
					}
					if (updatedUser != null && !lastname.equals(user.getLastName())) {
						user.setLastName(lastname);
						same = false;
					}
					
					if (!same) {
						if (dao.updateUserNames(user)) {
							user = updatedUser;			
							request.setAttribute(Constants.ERROR, 0);
							request.setAttribute(Constants.ERRORMSG, "Your name has been updated!");		
						} else {
							System.err.println("ProfileCommand: User names update failed.");
							request.setAttribute(Constants.ERROR, 1);
							request.setAttribute(Constants.ERRORMSG, "Failed to update names!");
						}
					} else {
						System.out.println("ProfileCommand: All names are the same, user not updated");
					}
					
					if (!email.equals(user.getEmail())) {
						if (email != null) {
							sendChangeEmail(email, user, dao);
							System.out.println("ProfileCommand: email update confirmation sent to " + email);
							request.setAttribute(Constants.ERROR, 0);
							request.setAttribute(Constants.ERRORMSG, "Confirm the email to change emails!");
						}
					} 
					else {
						System.out.println("ProfileCommand: Email is the same, email not sent");
					}
					
					break;
					
				case PASSWORD:
										
					if (!request.getParameter("password").equals(request.getParameter("repassword"))) {
						System.err.println("ProfileCommand: Password and re-password did not match.");
						request.setAttribute(Constants.ERROR, 1);
						request.setAttribute(Constants.ERRORMSG, "Your password did not match the retyped password!");
					}
					else if (request.getParameter("password").length() < 6) {
						System.err.println("ProfileCommand: Password less than 6 characters.");
						request.setAttribute(Constants.ERROR, 1);
						request.setAttribute(Constants.ERRORMSG, "Your password must be at least 6 characters long!");
					} 
					else if (!request.getParameter("password").matches(".*[!@#$%^&*()<>?,./-_+=]+.*")) {
						System.err.println("ProfileCommand: Password does not contain special characters.");
						request.setAttribute(Constants.ERROR, 1);
						request.setAttribute(Constants.ERRORMSG, "Your password must contains at least 1 non-alphanumeric character(s)!");
					} 
					else if (request.getParameter("repassword").length() < 6) {
						System.err.println("ProfileCommand: Password less than 6 characters.");
						request.setAttribute(Constants.ERROR, 1);
						request.setAttribute(Constants.ERRORMSG, "Your retyped password must be at least 6 characters long!");
					} 
					else if (!request.getParameter("repassword").matches(".*[!@#$%^&*()<>?,./-_+=]+.*")) {
						System.err.println("ProfileCommand: Password does not contain special characters.");
						request.setAttribute(Constants.ERROR, 1);
						request.setAttribute(Constants.ERRORMSG, "Your retyped password must contains at least 1 non-alphanumeric character(s)!");
					}
					else {
						String hashed = Common.hashPassword(request.getParameter("password"), user.getSaltHash());
						dao.setPassword(user, hashed);
						request.setAttribute(Constants.ERROR, 0);
						request.setAttribute(Constants.ERRORMSG, "Your password has been updated!");
					}
					
					break;
					
				case PREFERENCE:
					break;
					
			}
		}
		
        request.setAttribute(Constants.USER, user);
		
		RequestDispatcher rd = request.getRequestDispatcher("/profile.jsp");
		rd.forward(request, response);
	}	
	
	private void sendChangeEmail(String email, User user, CommonDAO dao) {
		String token = UUID.randomUUID().toString();
		dao.setToken(user, token);
		System.out.println("Token: " + token);
		
		System.out.println("sending email to " + email);
		
		// Send email here 
		String content = "Hi " + user.getFirstName() + "," + "<br/><br/>";
		content += "Please confirm the new email for your WalletHero account ";
		content += "by clicking on the link below.<br/>";
		content += Constants.SERVER + Constants.ROUTER + Constants.EMAILUPDATE_COMMAND;
		content += "&username" + "=" + user.getUsername() + "&token"+ "=" + token;
		content += "&email" + "=" + email;
		content += "<br/><br/>";
		content += "Regards,<br/>";
		content += "WalletHero Support Team";
		
		
		MailHelper mh = new MailHelper();
		mh.sendEmail(email, "WalletHero Change Email", content);
	}
}
