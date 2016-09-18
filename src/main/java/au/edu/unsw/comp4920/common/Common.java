package au.edu.unsw.comp4920.common;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Common {
	public static void main(String[] args) { 
		// Test your fucntions/methods here
	}
	
	public static String generateToken(String toEncrypt) {
		MessageDigest messageDigest;
		String encryptedString = toEncrypt;
		try {
			messageDigest = MessageDigest.getInstance("SHA-384");
			messageDigest.update(toEncrypt.getBytes());
			encryptedString = new String(messageDigest.digest());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return encryptedString;
	}
	//TODO
}
