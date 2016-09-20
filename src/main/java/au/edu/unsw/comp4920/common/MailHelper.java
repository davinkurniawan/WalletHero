package au.edu.unsw.comp4920.common;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailHelper {
	public void sendEmail(String email, String subject, String content) {
		Properties props = new Properties();
		final String username = "my.website.user.validator";
		final String password = "comp9321";
	    props.put("mail.smtp.starttls.enable", true); // added this line
	    props.put("mail.smtp.host", "smtp.gmail.com");
	    props.put("mail.smtp.user", username);
	    props.put("mail.smtp.password", password);
	    props.put("mail.smtp.port", "587");
	    props.put("mail.smtp.auth", true);
        Session session = Session.getDefaultInstance(props, null);

		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username, "WalletHero Support"));
			message.addRecipient(Message.RecipientType.TO,
			                   new InternetAddress(email, "User"));
            message.setSubject(subject);
            message.setText(content);
            
            Transport transport = session.getTransport("smtp");
            transport.connect("smtp.gmail.com", username, password);
            System.out.println("Transport: "+transport.toString());
            transport.sendMessage(message, message.getAllRecipients());

            System.out.println("Email sent successfully");
		} catch (MessagingException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
}
