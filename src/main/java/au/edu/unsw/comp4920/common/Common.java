package au.edu.unsw.comp4920.common;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Common {
	public static void main(String[] args) { 
		// Test your fucntions/methods here
	}
	
	public static Date convertStringtoDate(String date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.DEFAULT_DATE_FORMAT);
		Date out = null;
		try {
			out = dateFormat.parse(date);
		} 
		catch (ParseException e) {
			System.out.println(e.getMessage());
		}
		
		return out;
	}
	
	public static String generateToken(String toEncrypt) {
		MessageDigest messageDigest;
		String encryptedString = toEncrypt;
		try {
			messageDigest = MessageDigest.getInstance("SHA-384");
			messageDigest.update(toEncrypt.getBytes());
			encryptedString = new String(messageDigest.digest());
		} 
		catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return encryptedString;
	}
}
