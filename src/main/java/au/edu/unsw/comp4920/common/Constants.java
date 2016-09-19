package au.edu.unsw.comp4920.common;

/*
 * This class contains all CONSTANTS
 * As the name suggests
 * Nothing else
 */
public final class Constants {
	
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
	
	public static final String ADDTRANSACTION_COMMAND   = "addTransaction";
	public static final String VIEWTRANSACTIONS_COMMAND   = "viewTransactions";
	
	public static final String VALIDATE_COMMAND 		= "emailvalidation";
	public static final String ROUTER					= "router?operation=";
	public static final String SERVER					= "http://localhost:8080/COMP4920-Project/";
	
	// Request Parameters
	public static final String ACTION 					= "action";
	public static final String OPERATION 				= "operation";
	public static final String ERROR 					= "errorFlg";
	public static final String ERRORMSG					= "errorMessage";
	public static final String RESULT					= "result";
	public static final String SEARCH					= "search";
	
	// Session Parameters
	public static final String PERSONID 				= "personID";
	public static final String SID 						= "sessionID";
	public static final String SEARCHRESULT				= "searchResult";
	
	// Naming
	public static final String WEB						= "WalletHero";
	
	// Integer
	public static final int DEFAULT						= -1;
	
	// Date format
	public static final String DEFAULT_DATE_FORMAT		= "dd MMMM yyyy hh:mm:ss";
	
	// Servlet Global Attributes
	public static final String WEB_NAME					= "WEB_NAME";
	public static final String ROUTER_SIGNIN			= "ROUTER_SIGNIN";
	public static final String ROUTER_SIGNOUT			= "ROUTER_SIGNOUT";
	public static final String ROUTER_SIGNUP			= "ROUTER_SIGNUP";
	public static final String ROUTER_PROFILE			= "ROUTER_PROFILE";
	public static final String ROUTER_PUBLIC			= "ROUTER_PUBLIC";
	public static final String ROUTER_HOME				= "ROUTER_HOME";
	public static final String ROUTER_SEARCH			= "ROUTER_SEARCH";
	public static final String ROUTER_NOTFOUND			= "ROUTER_NOTFOUND";
	public static final String ROUTER_VALIDATE			= "ROUTER_VALIDATE";
	public static final String ROUTER_ABOUT				= "ROUTER_ABOUT";
	
	public static final String ROUTER_ADDTRANSACTION	= "ROUTER_ADDTRANSACTION";
	public static final String ROUTER_VIEWTRANSACTIONS	= "ROUTER_VIEWTRANSACTIONS";
	
	// Prevent instantiation
	private Constants() {};
}
