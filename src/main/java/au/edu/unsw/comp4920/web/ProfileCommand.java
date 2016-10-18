package au.edu.unsw.comp4920.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;

import au.edu.unsw.comp4920.common.Common;
import au.edu.unsw.comp4920.common.CommonDAO;
import au.edu.unsw.comp4920.common.Constants;
import au.edu.unsw.comp4920.common.MailHelper;
import au.edu.unsw.comp4920.objects.DealsCategory;
import au.edu.unsw.comp4920.objects.Preference;
import au.edu.unsw.comp4920.objects.User;

/**
 * @author Timothy, Natalia
 *
 */
public class ProfileCommand implements Command {
	
	private static enum Commands {PROFILE, PASSWORD, PREFERENCE, DELETE_DATA, DELETE_ACCOUNT, DEALS};
	
	private static Commands commands (String s) {
		if (s == null) return null;
		if (s.equalsIgnoreCase("update_profile"    )) 	return Commands.PROFILE;
		if (s.equalsIgnoreCase("update_password"   )) 	return Commands.PASSWORD;
		if (s.equalsIgnoreCase("update_preferences")) 	return Commands.PREFERENCE;
		if (s.equalsIgnoreCase("delete_user_data"  )) 	return Commands.DELETE_DATA;
		if (s.equalsIgnoreCase("delete_account"	   )) 	return Commands.DELETE_ACCOUNT;
		if (s.equalsIgnoreCase("update_deals"	   )) 	return Commands.DEALS;

		return null;
	}

	public ProfileCommand() {
		
	}
	
	public void execute(HttpServletRequest request, HttpServletResponse response, CommonDAO dao) throws ServletException, IOException{
		System.out.println("Inside: ProfileCommand");
		
		HttpSession session = request.getSession();
        String sid = session.getAttribute(Constants.SID).toString();
        String actionString = request.getParameter(Constants.ACTION) == null ? null : request.getParameter(Constants.ACTION).toString();
        System.out.println("ProfileCommand: Action is " + actionString);
		
		User user = dao.getUser(sid);
		User updatedUser = null;
		
		Preference preference = dao.getUserPreference(sid);
		Preference updatedPreference = null;
		
		Commands action = commands(request.getParameter("action"));
		if (action != null) {
			switch (action) {
				case PROFILE:					
					if ( request.getParameter("email") != null && request.getParameter("firstname") != null && request.getParameter("lastname") != null) {
						String email 		= request.getParameter("email");
						String firstname 	= request.getParameter("firstname");
						String middlename 	= request.getParameter("middlename");
						String lastname 	= request.getParameter("lastname");
												
						boolean same = true;
						
						updatedUser = new User(user);
						if (firstname != null && !firstname.equals(user.getFirstName())) {
							updatedUser.setFirstName(firstname);
							same = false;
						}
						if (middlename != null && !middlename.equals(user.getMiddleName())) {
							updatedUser.setMiddleName(middlename);
							same = false;
						}
						if (lastname != null && !lastname.equals(user.getLastName())) {
							updatedUser.setLastName(lastname);
							same = false;
						}
						
						if (!same) {
							if (dao.updateUserNames(updatedUser)) {
								user = updatedUser;			
								request.setAttribute(Constants.ERROR, 0);
								request.setAttribute(Constants.ERRORMSG, "Your detail has been updated!");		
							} 
							else {
								System.err.println("ProfileCommand: User names update failed.");
								request.setAttribute(Constants.ERROR, 1);
								request.setAttribute(Constants.ERRORMSG, "Failed to update name(s)!");
							}
						} 
						else {
							System.out.println("ProfileCommand: All names are the same, user not updated");
						}
						
						if (email != null && !email.equals(user.getEmail())) {
							if (dao.getUser(email, null) == null){
								sendChangeEmail(email, user, dao);
								System.out.println("ProfileCommand: email update confirmation sent to " + email);
								request.setAttribute(Constants.ERROR, 0);
								request.setAttribute(Constants.ERRORMSG, "We have sent an email to your account. Plase confirm the email to to update your email!");
							}
							else{
								System.err.println("ProfileCommand: Email has been before.");
								request.setAttribute(Constants.ERROR, 1);
								request.setAttribute(Constants.ERRORMSG, "The email you entered has been used by other user!");
							}
						} 
						else {
							System.out.println("ProfileCommand: Email is the same, email not sent");
						}
					}
					else{
						System.err.println("ProfileCommand: Missing information.");
						request.setAttribute(Constants.ERROR, 1);
						request.setAttribute(Constants.ERRORMSG, "Missing required information!");
					}
					
					break;
					
				case PASSWORD:
							
					if ( request.getParameter("password") != null && request.getParameter("repassword") != null) {

						if (!request.getParameter("password").equals(request.getParameter("repassword"))) {
							System.err.println("ProfileCommand: Password and re-password did not match.");
							request.setAttribute(Constants.ERROR, 1);
							request.setAttribute(Constants.ERRORMSG, "Your password did not match the retyped password!");
						}
						else if (request.getParameter("password").length() < 6) {
							System.err.println("ProfileCommand: Password less than 6 characters.");
							request.setAttribute(Constants.ERROR, 1);
							request.setAttribute(Constants.ERRORMSG, "Your password must be at least 6 characters long!");
						} 
						else if (!request.getParameter("password").matches(".*[!@#$%^&*()<>\\?,\\./-_+=]+.*")) {
							System.err.println("ProfileCommand: Password does not contain special characters.");
							request.setAttribute(Constants.ERROR, 1);
							request.setAttribute(Constants.ERRORMSG, "Your password must contains at least 1 non-alphanumeric character(s)!");
						} 
						else if (request.getParameter("repassword").length() < 6) {
							System.err.println("ProfileCommand: Password less than 6 characters.");
							request.setAttribute(Constants.ERROR, 1);
							request.setAttribute(Constants.ERRORMSG, "Your retyped password must be at least 6 characters long!");
						} 
						else if (!request.getParameter("repassword").matches(".*[!@#$%^&*()<>\\?,\\./-_+=]+.*")) {
							System.err.println("ProfileCommand: Password does not contain special characters.");
							request.setAttribute(Constants.ERROR, 1);
							request.setAttribute(Constants.ERRORMSG, "Your retyped password must contains at least 1 non-alphanumeric character(s)!");
						}
						else {
							String hashed = Common.hashPassword(request.getParameter("password"), user.getSaltHash());
							
							if (!hashed.equals(user.getPassword())){
								dao.setPassword(user, hashed);
								request.setAttribute(Constants.ERROR, 0);
								request.setAttribute(Constants.ERRORMSG, "Your password has been updated!");
							}
							else{
								System.err.println("ProfileCommand: Password same as previous.");
								request.setAttribute(Constants.ERROR, 1);
								request.setAttribute(Constants.ERRORMSG, "Your password is the same as your last password!");
							}
						}
					}
					else{
						System.err.println("ProfileCommand: Missing information.");
						request.setAttribute(Constants.ERROR, 1);
						request.setAttribute(Constants.ERRORMSG, "Missing Required Information!");
					}
						
					break;
					
				case PREFERENCE:
					
					if ( request.getParameter("age") != null && request.getParameter("gender") != null 
							&& request.getParameter("currency") != null && request.getParameter("occupation") != null) {

						String age = request.getParameter("age");
						String gender = request.getParameter("gender");
						String currency = request.getParameter("currency");
						String occupation = request.getParameter("occupation");
						boolean update = false;
						boolean shouldProceed = false;
						updatedPreference = preference.clone();
						
						System.out.println("Currency: " + currency);
						System.out.println("Occupation: " + occupation);
						System.out.println("Gender: " + gender);
						System.out.println("Age: " + age);
						
						if (age.matches("^[0-9]+$")) {
							int ageNum = Integer.parseInt(age);
							
							if (ageNum > 0){						
								if (ageNum != updatedPreference.getAge()) {
									updatedPreference.setAge(ageNum);
									update = true;
									shouldProceed = true;
								} 
								else {
									System.out.println("ProfileCommand: Age same as previous.");
									shouldProceed = true;
								}
							}
							else{
								System.err.println("ProfileCommand: Invalid age.");
								request.setAttribute(Constants.ERROR, 1);
								request.setAttribute(Constants.ERRORMSG, "Invalid age entered!");
								update = false;
								shouldProceed = false;
							}
						} 
						else {
							System.err.println("ProfileCommand: Age must be a number.");
							request.setAttribute(Constants.ERROR, 1);
							request.setAttribute(Constants.ERRORMSG, "Your age must be a number!");
							update = false;
							shouldProceed = false;
						}
						
						if (shouldProceed){
							if (!gender.equalsIgnoreCase(updatedPreference.getGender())){
								if (gender.equalsIgnoreCase("M") || gender.equalsIgnoreCase("F") || gender.equalsIgnoreCase("O")) {
									updatedPreference.setGender(gender);
									update = true;
									shouldProceed = true;
								}
								else{
									System.err.println("ProfileCommand: Invalid Gender Type.");
									request.setAttribute(Constants.ERROR, 1);
									request.setAttribute(Constants.ERRORMSG, "Invalid gender chosen!");
									update = false;
									shouldProceed = false;
								}
							} 
							else {
								System.out.println("ProfileCommand: Gender same as previous.");
								shouldProceed = true;
							}
						}
						
						if (shouldProceed){
							if (currency.matches("^[0-9]+$")) {
								int currencyId = Integer.parseInt(currency);
								
								if (currencyId != updatedPreference.getCurrencyID()){			
									if (currencyId >= 1 && currencyId <= 64) {
										updatedPreference.setCurrencyID(currencyId);
										update = true;
										shouldProceed = true;
									}
									else{
										System.err.println("ProfileCommand: Invalid Currency ID.");
										request.setAttribute(Constants.ERROR, 1);
										request.setAttribute(Constants.ERRORMSG, "Invalid currency chosen!");
										update = false;
										shouldProceed = false;
									}
								} 
								else {
									System.out.println("ProfileCommand: Currency same as previous.");
									shouldProceed = true;
								}
							}
							else {
								System.err.println("ProfileCommand: Currency id must be a number.");
								request.setAttribute(Constants.ERROR, 1);
								request.setAttribute(Constants.ERRORMSG, "Invalid currency chosen!");
								update = false;
								shouldProceed = false;
							}
						}
						
						if (shouldProceed){
							if (occupation.matches("^[0-9]+$")) {
								int occupationId = Integer.parseInt(occupation);
								
								//TODO add limit occupationID
								
								if (occupationId != updatedPreference.getOccupationID()) {
									updatedPreference.setOccupationID(occupationId);
									update = true;
									shouldProceed = true;		
								} 
								else {
									System.out.println("ProfileCommand: Occupation same as previous.");
									shouldProceed = true;
								}
								
							} 
							else {
								System.err.println("ProfileCommand: Occupation id must be a number.");
								request.setAttribute(Constants.ERROR, 1);
								request.setAttribute(Constants.ERRORMSG, "Incorrect occupation format!");
								update = false;
								shouldProceed = false;
							}
						}
						
						if (update && shouldProceed) {
							if (dao.updatePreference(updatedPreference)) {
								preference = updatedPreference;
								request.setAttribute(Constants.ERROR, 0);
								request.setAttribute(Constants.ERRORMSG, "Your preferences has been updated!");
							}
						}
						else if (!update) {
							request.setAttribute(Constants.ERROR, 3);
							request.setAttribute(Constants.ERRORMSG, "No Changes Made!");
						}
					}
					else {
						System.err.println("ProfileCommand: Missing information.");
						request.setAttribute(Constants.ERROR, 1);
						request.setAttribute(Constants.ERRORMSG, "Missing Required Information!");
					}
					
					break;
					
				case DELETE_DATA:
					
					if (dao.deleteAllUserData(user.getUserID())){
						request.setAttribute(Constants.ERROR, 0);
						request.setAttribute(Constants.ERRORMSG, "All of your Data has been deleted!");
					}
					else{
						System.err.println("ProfileCommand: Failed to delete user's data.");
						request.setAttribute(Constants.ERROR, 1);
						request.setAttribute(Constants.ERRORMSG, "Failed to Delete your Data!");
					}
					
					break;
				
				case DELETE_ACCOUNT:

					if (dao.deleteAllUserData(user.getUserID())){
						//TODO send email
					}
					
					
					break;
					
				case DEALS:
					List<String> category;
					String deals = "";
					
					for (String s : request.getParameterValues("category")) {
						deals += s;
						deals += ",";
					}
					
					if (deals.equals(preference.getDeals())) {
						category = preference.getDealsArrayList();
					} else {
						Preference newP = preference.clone();
						newP.setDeals(deals);
						dao.updatePreference(newP);
						category = newP.getDealsArrayList();
					}
					
					request.setAttribute("category", category);
					System.out.println(deals.toString());
					break;
			}
		}
		
		
		List<DealsCategory> categories = new ArrayList<DealsCategory>();
		if (session.getAttribute("categories") == null) {
			// get categories of deals to select from
			JSONObject categories_json = DealsCommand.sendAPIRequest(Constants.API_URL + "/categories");
			JSONArray categories_array = categories_json.getJSONArray("categories");
			ObjectMapper mapper = new ObjectMapper();
			
			for (int i = 0; i < categories_array.length(); ++i) {
				String category = categories_array.getJSONObject(i).getJSONObject("category").toString();
				DealsCategory c = mapper.readValue(category, DealsCategory.class);
				categories.add(c);
			}
			session.setAttribute("categories", categories);
		}
		
        request.setAttribute(Constants.USER, user);
        request.setAttribute(Constants.PREFERENCE, preference);

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
		content += "You recently requested to update your email for your WalletHero account. ";
		content += "You must follow this link to upadte your email:";
		content += "<br/><br/>";
		content += Constants.SERVER + Constants.ROUTER + Constants.EMAILUPDATE_COMMAND;
		content += "&username" + "=" + user.getUsername() + "&token"+ "=" + token;
		content += "&email" + "=" + email;
		content += "<br/><br/>";
		content += "Have fun, and don't hesitate to contact us with your feedback.";
		content += "<br/><br/>";
		content += "WalletHero Team";
		content += "<br/><br/>";
		content += Constants.SERVER;
		
		MailHelper mh = new MailHelper();
		mh.sendEmail(email, "WalletHero - Email Update", content);
	}
}
