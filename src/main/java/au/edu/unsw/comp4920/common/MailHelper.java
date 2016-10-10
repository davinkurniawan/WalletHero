package au.edu.unsw.comp4920.common;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.PasswordAuthentication;

public class MailHelper {
	private final String username = "thewallethero";
	private final String password = "w4ll3th3r0";
    private final String from = "thewallethero@gmail.com";

	public void sendEmail(String email, String subject, String content) {
		Properties props = new Properties();		
	    props.put("mail.smtp.host", "smtp.gmail.com");
	    props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
	    
		Session session = Session.getDefaultInstance(props,
			new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username, password);
				}
			}
		);
	    
		try {
			Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from, "WalletHero Team"));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
			message.setSubject(subject);
			message.setContent(content, "text/html");
			
			Transport.send(message);
			System.out.println("Email sent successfully");
		} 
		catch (MessagingException | UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}
}
