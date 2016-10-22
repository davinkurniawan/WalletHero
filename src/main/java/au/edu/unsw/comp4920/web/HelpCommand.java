package au.edu.unsw.comp4920.web;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import au.edu.unsw.comp4920.common.CommonDAO;
import au.edu.unsw.comp4920.common.Constants;
import au.edu.unsw.comp4920.common.MailHelper;
import au.edu.unsw.comp4920.objects.User;

public class HelpCommand implements Command {
	
	private static enum Commands {EMAIL };
	
	public HelpCommand(){
		
	}
	
	private static Commands commands (String s) {
		if (s == null) return null;
		if (s.equalsIgnoreCase("email_help" )) 	return Commands.EMAIL;

		return null;
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response, CommonDAO dao) throws ServletException, IOException {
		System.out.println("Inside: HelpCommand");

        String actionString = request.getParameter(Constants.ACTION) == null ? null : request.getParameter(Constants.ACTION).toString();
        System.out.println("HelpCommand: Action is " + actionString);
				
		HttpSession session = request.getSession();
		User user = null;
		
        if (session.getAttribute(Constants.SID) != null){
            String sid = session.getAttribute(Constants.SID).toString();
        	user = dao.getUser(sid);
        	
        	if (user != null){
        		request.setAttribute(Constants.EMAIL, user.getEmail());;
        	}
        }
        
		Commands action = commands(request.getParameter("action"));
		if (action != null) {
			switch (action) {
				case EMAIL:
					String email	= request.getParameter("email");
					String message 	= request.getParameter("message");
					sendSupportEmail(email, message, user, dao);
					
					response.sendRedirect(Constants.ROUTER + Constants.HELP_COMMAND + "&success=yes");
					return;					
			}
		}
		
		RequestDispatcher rd = request.getRequestDispatcher("/help.jsp");
		rd.forward(request, response);
	}

	private void sendSupportEmail(String useremail, String message, User user, CommonDAO dao) {
		Date current = new Date();
		SimpleDateFormat df = new SimpleDateFormat(Constants.SIMPLE_DEFAULT_DATE_FORMAT);
		
		String content = "";
		
		if (user != null){
			content += "Hi " + user.getFirstName() + "," + "<br/><br/>";
		}
		else{
			content += "Hi there, " + "<br/><br/>";
		}
		
		content += "This is a WalletHero notification email that we have received your message.";
		content += "<br/><br/>";
		content += "The message is as follows:";
		content += "<br/><br/>";
		content += "<b>" + message + "</b>";
		content += "<br/><br/>";	
		content += "The message was made by: " + useremail + " on " + df.format(current) + ".";		
		content += "<br/><br/>";
		content += "Have fun, and don't hesitate to contact us with your feedback.";
		content += "<br/><br/>";
		content += "WalletHero Team";
		content += "<br/><br/>";
		content += Constants.SERVER;

		MailHelper mh = new MailHelper();
		mh.sendEmail(useremail, "WalletHero - Support Notification", content);
	}
}

