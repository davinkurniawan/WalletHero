package au.edu.unsw.comp4920.common;

/*
 * This class contains all CONSTANTS
 * As the name suggests
 * Nothing else
 */
public final class Constants {
	//TODO
	
	// Router Commands
	public static final String SIGNIN_COMMAND 			= "signIn";
	public static final String SIGNOUT_COMMAND 			= "signOut";
	public static final String SIGNUP_COMMAND 			= "signUp";
	public static final String PROFILE_COMMAND 			= "viewEditProfile";
	public static final String PUBLIC_COMMAND 			= "public";
	public static final String HOME_COMMAND				= "home";
	public static final String SEARCH_COMMAND 			= "search";
	public static final String NOTFOUND_COMMAND 		= "PAGE_NOT_FOUND";
	public static final String ABOUT_COMMAND 			= "about";
	public static final String VALIDATE_COMMAND 		= "emailvalidation";
	public static final String ROUTER					= "router?operation=";
	public static final String SERVER					= "http://localhost:8080/COMP4920-Project/";
	
	// Session Parameters
	public static final String PERSONID 				= "personID";
	public static final String SID 						= "sessionID";
	public static final String SEARCHRESULT				= "searchResult";
	
	// Request Parameters
	public static final String ACTION 					= "action";
	public static final String OPERATION 				= "operation";
	
	// Naming
	public static final String WEB						= "COMP420 Project";
	
	// Integer
	public static final int DEFAULT						= -1;
	
	// Prevent instantiation
	private Constants() {};
}
