package au.edu.unsw.comp4920.web;

import java.io.IOException;
import java.util.*;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
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
import au.edu.unsw.comp4920.objects.*;
import au.edu.unsw.comp4920.common.Common;
import au.edu.unsw.comp4920.common.Constants;
import au.edu.unsw.comp4920.common.MailHelper;
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
		String action = request.getParameter(Constants.ACTION);
		String nextPage = "/signup.jsp";
		if (action != null && action.equalsIgnoreCase("update")) {
			User user = new User();
			user.setUsername(request.getParameter("username"));
			user.setEmail(request.getParameter("email"));
			user.setFirstName(request.getParameter("firstname"));
			user.setLastName(request.getParameter("lastname"));
			if(dao.createUser(user)) {
				// Send email here  
				String token = Common.generateToken(user.getUsername() + user.getEmail()+ user.getPassword());
				String content = Constants.SERVER + Constants.ROUTER + Constants.VALIDATE_COMMAND;
				content += "&username" + "=" + user.getUsername() + "&token"+ "=" + token;
				sendMail("support@wallethero.com", user.getEmail(), "Validate Your Email.", content);
				nextPage = "/signup.jsp";
			} else {
				// Let JSP know error happens
				request.setAttribute(Constants.ERROR, true);
				request.setAttribute(Constants.ACTION, true);
				nextPage = "/signin.jsp";
			}
		}
		RequestDispatcher rd = request.getRequestDispatcher(nextPage);
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
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
}
