package au.edu.unsw.comp4920.web;

import java.io.IOException;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import au.edu.unsw.comp4920.common.CommonDAO;
import au.edu.unsw.comp4920.common.Common;
import au.edu.unsw.comp4920.common.Constants;
import au.edu.unsw.comp4920.objects.User;

/**
 * 
 *
 */
public class SignUpCommand implements Command {

	public SignUpCommand() {
		
	}
	
	public void execute(HttpServletRequest request, HttpServletResponse response, CommonDAO dao) throws ServletException, IOException {
		System.out.println("Inside: SignUpCommand");
		
		String action = request.getParameter(Constants.ACTION) == null ? null : request.getParameter(Constants.ACTION).toString();
		System.out.println("SignUpCommand: Action is " + action);

		if (action != null && action.equalsIgnoreCase("signUp")) {
		
			if (request.getParameter("username") != null && 
				request.getParameter("password") != null &&
				request.getParameter("email") != null &&
				request.getParameter("first_name") != null &&
				request.getParameter("middle_name") != null &&
				request.getParameter("last_name") != null) {
			
				System.out.println("SignUpCommand: Creating user");
				
				if (dao.getUserDetails(request.getParameter("username")) == null){
					User user = new User();
					user.setUsername(request.getParameter("username"));
					user.setPassword(request.getParameter("password"));
					user.setEmail(request.getParameter("email"));
					user.setFirstName(request.getParameter("first_name"));
					user.setMiddleName(request.getParameter("middle_name"));
					user.setLastName(request.getParameter("last_name"));
					
					if(dao.createUser(user)) {
						System.out.println("sending email");
						// Send email here  
						String token = Common.generateToken(user.getUsername() + user.getEmail()+ user.getPassword());
						String content = Constants.SERVER + Constants.ROUTER + Constants.VALIDATE_COMMAND;
						content += "&username" + "=" + user.getUsername() + "&token"+ "=" + token;
						sendMail("support@wallethero.com", user.getEmail(), "Validate Your Email.", content);

						// Redirect to appropriate page: public view
						response.sendRedirect(Constants.ROUTER + Constants.SIGNUP_COMMAND + "&success=yes");
						return;
					}
					else{
						System.err.println("SignUpCommand: Failed to create user account!");
					}
				}
				else{
					System.err.println("SignUpCommand: User account exists!");
				}
			}
		}
		
		RequestDispatcher rd = request.getRequestDispatcher("/signup.jsp");
		rd.forward(request, response);
	}	
	
	public static void sendMail(String email_sender, String email_receiver,
			String subject, String content) {
		try {
			// get context and session
			Context initital_context = new InitialContext();
			Context env_context = (Context) initital_context.lookup("java:comp/env");
			Session session = (Session) env_context.lookup("mail/Session");
			
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(email_sender));
			
			InternetAddress to[] = new InternetAddress[1];
			to[0] = new InternetAddress(email_receiver);
			
			message.setRecipients(Message.RecipientType.TO, to);
			message.setSubject(subject);
			message.setContent(content, "text/plain");
			Transport.send(message);
		} 
		catch (NamingException | MessagingException e) {
			e.printStackTrace();
		}
	}
}
