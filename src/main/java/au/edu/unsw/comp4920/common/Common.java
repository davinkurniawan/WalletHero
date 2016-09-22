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
	
	/* Code on hashing referenced from: 
	 * http://howtodoinjava.com/security/how-to-generate-secure-password-hash-md5-sha-pbkdf2-bcrypt-examples/ 
	 */
	public static String hashPassword(String password, String salt) {
		String pwd = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt.getBytes());
            byte[] bytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            pwd = sb.toString();
        } 
        catch (NoSuchAlgorithmException e) 
        {
            e.printStackTrace();
        }
        return pwd;
	}
}
