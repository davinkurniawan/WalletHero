package au.edu.unsw.comp4920.web;

import java.io.IOException;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import au.edu.unsw.comp4920.common.CommonDAO;
import au.edu.unsw.comp4920.common.Constants;
import au.edu.unsw.comp4920.common.MailHelper;

public class HelpCommand implements Command {
	
	private static enum Commands {EMAIL };
	private static Commands commands (String s) {
		if (s == null) return null;
		if (s.equalsIgnoreCase("email_help" )) 	return Commands.EMAIL;

		return null;
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response, CommonDAO dao) throws ServletException, IOException {

        String actionString = request.getParameter(Constants.ACTION) == null ? null : request.getParameter(Constants.ACTION).toString();
        System.out.println("HelpCommand: Action is " + actionString);
		
		Commands action = commands(request.getParameter("action"));
		if (action != null) {
			switch (action) {
				case EMAIL:
					String email	= request.getParameter("email");
					String message 	= request.getParameter("message");
					sendSupportEmail(email, message, dao);
					request.setAttribute("success", "yes");;
					break;
			}
		}
		
		RequestDispatcher rd = request.getRequestDispatcher("/help.jsp");
		rd.forward(request, response);
	}




	private void sendSupportEmail(String useremail, String message, CommonDAO dao) {
		String email = "nataliadjohari@gmail.com";
		Date d = new Date();
		
		// Send email here 
		String content = "Hi " + "<br/><br/>";
		content += "A message regarding wallethero has been received. ";
		content += "The message is as follows:";
		content += "<br/><br/>";
		content += "Sent by: " + useremail + " at " + d.toString();
		content += "<br/><br/>";
		content += message;
		content += "<br/><br/>";
		content += "Remember to reply";
		content += "<br/><br/>";
		content += "WalletHero Server";
		content += "<br/><br/>";
		content += Constants.SERVER;
		
		MailHelper mh = new MailHelper();
		mh.sendEmail(email, "WalletHero - Support Email", content);
	}
}

