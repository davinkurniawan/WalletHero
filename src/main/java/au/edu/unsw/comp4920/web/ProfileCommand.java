package au.edu.unsw.comp4920.web;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
		
		Commands action = commands(request.getParameter("action"));
		if (action != null) {
			switch (action) {
				case PROFILE:
					String email 		= request.getParameter("email");
					String firstname 	= request.getParameter("firstname");
					String middlename 	= request.getParameter("middlename");
					String lastname 	= request.getParameter("lastname");
					
					if ( email == null || firstname == null || lastname == null) {
						request.setAttribute("null_values_exist", true);
					}
					
					User updatedUser = new User(user);
					if (firstname != null) 
						updatedUser.setFirstName(firstname);
					if (middlename != null) 
						user.setMiddleName(middlename);
					if (updatedUser != null) 
						user.setLastName(lastname);
					
					if (dao.updateUserNames(user)) {
						user = updatedUser;					
					} else 
						request.setAttribute("update_profile_fail", true);
					
					if (email != null && !email.equals(user.getEmail())) {
						sendChangeEmail(email, user, dao);
						request.setAttribute("sent_email_confirmation", true);
					}
					
					break;
					
				case PASSWORD:
					
				case PREFERENCE:
					
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
		content += "Please confirm the new email for your WalletHero account";
		content += "by clicking on the link below.<br/>";
        // Change command to update email not validate
		content += Constants.SERVER + Constants.ROUTER + Constants.VALIDATE_COMMAND;
		content += "&username" + "=" + user.getUsername() + "&token"+ "=" + token;
		content += "&email" + "=" + email;
		content += "<br/><br/>";
		content += "Regards,<br/>";
		content += "WalletHero Support Team";
		
		
		MailHelper mh = new MailHelper();
		mh.sendEmail(email, "WalletHero Password Recovery", content);
	}
}
