package au.edu.unsw.comp4920.common;

/*
 * This class contains all CONSTANTS
 * As the name suggests
 * Nothing else
 */
public final class Constants {
	
	// Router Commands 
	// Which navigation bar choice is active depends on router commands
	public static final String SIGNIN_COMMAND 				= "signIn";
	public static final String SIGNOUT_COMMAND 				= "signOut";
	public static final String SIGNUP_COMMAND 				= "signUp";
	public static final String PROFILE_COMMAND 				= "profile";
	public static final String PUBLIC_COMMAND 				= "public";
	public static final String HOME_COMMAND					= "home";
	public static final String SEARCH_COMMAND 				= "search";
	public static final String NOTFOUND_COMMAND 			= "PAGE_NOT_FOUND";
	public static final String ABOUT_COMMAND 				= "about";
	public static final String ADDTRANSACTION_COMMAND   	= "addTransaction";
	public static final String VIEWTRANSACTIONS_COMMAND 	= "viewTransactions";
	public static final String ADDGOAL_COMMAND 				= "addGoal";
	public static final String VIEWGOALS_COMMAND 			= "viewGoals";
	public static final String HELP_COMMAND 				= "help";
	
	public static final String DEALS_COMMAND				= "deals";
	public static final String CURRENCYCONVERTER_COMMAND	=  "currencyConverter";

	public static final String VALIDATE_COMMAND 			= "emailvalidation";
	public static final String EMAILUPDATE_COMMAND 			= "emailupdate";
	public static final String ROUTER						= "router?operation=";
	public static final String SERVER						= "http://localhost:8080/COMP4920-Project/";
	
	public static final String FORGOTPASSWORD_COMMAND   	= "forgotPassword";
	public static final String RESETPASSWORD_COMMAND		= "resetPassword";
	public static final String REENABLE_ACCOUNT_COMMAND		= "reenableAccount";
	
	// Request Parameters
	public static final String ACTION 						= "action";
	public static final String OPERATION 					= "operation";
	public static final String ERROR 						= "errorFlg";
	public static final String ERRORMSG						= "errorMessage";
	public static final String RESULT						= "result";
	public static final String SEARCH						= "search";
	public static final String TOKEN						= "token";
	public static final String USERNAME						= "username";
	public static final String EMAIL						= "email";
	public static final String TOOLS						= "tools";
	public static final String TRANSACTIONS					= "transactions";
	public static final String GOALS						= "goals";
	public static final String LASTACCESSED					= "lastAccessed";
	public static final String TOTAL_INCOME					= "totalIncome";
	public static final String TOTAL_EXPENSE				= "totalExpense";
	public static final String TOTAL_BUDGET					= "totalBudget";
	public static final String PREFERRED_CURRENCY			= "preferredCurrency";

	// Session Parameters
	public static final String USERID	 					= "userID";
	public static final String SID 							= "sessionID";
	public static final String SEARCHRESULT					= "searchResult";
	public static final String USER 						= "user";
	public static final String CURRENCY 					= "currency";
	public static final String CATEGORY 					= "category";
	public static final String OCCUPATION 					= "occupation";
	public static final String PREFERENCE 					= "preference";

	// Naming
	public static final String WEB							= "WalletHero";
	
	// Integer
	public static final int DEFAULT							= -1;
	
	// Date format
	public static final String DEFAULT_DATE_FORMAT			= "dd MMMM yyyy hh:mm:ss";
	public static final String SIMPLE_DEFAULT_DATE_FORMAT	= "dd MMMM yyyy";
	
	// Servlet Global Attributes
	public static final String WEB_NAME						= "WEB_NAME";
	public static final String ROUTER_SIGNIN				= "ROUTER_SIGNIN";
	public static final String ROUTER_SIGNOUT				= "ROUTER_SIGNOUT";
	public static final String ROUTER_SIGNUP				= "ROUTER_SIGNUP";
	public static final String ROUTER_PROFILE				= "ROUTER_PROFILE";
	public static final String ROUTER_PUBLIC				= "ROUTER_PUBLIC";
	public static final String ROUTER_HOME					= "ROUTER_HOME";
	public static final String ROUTER_SEARCH				= "ROUTER_SEARCH";
	public static final String ROUTER_NOTFOUND				= "ROUTER_NOTFOUND";
	public static final String ROUTER_VALIDATE				= "ROUTER_VALIDATE";
	public static final String ROUTER_EMAILUPDATE			= "ROUTER_EMAILUPDATE";
	public static final String ROUTER_ABOUT					= "ROUTER_ABOUT";
	public static final String ROUTER_ADDTRANSACTION		= "ROUTER_ADDTRANSACTION";
	public static final String ROUTER_VIEWTRANSACTIONS		= "ROUTER_VIEWTRANSACTIONS";
	public static final String ROUTER_DEALS					= "ROUTER_DEALS";
	public static final String ROUTER_FORGOTPASSWORD		= "ROUTER_FORGOTPASSWORD";
	public static final String ROUTER_RESETPASSWORD			= "ROUTER_RESETPASSWORD";
	public static final String ROUTER_CURRENCYCONVERTER		= "ROUTER_CURRENCYCONVERTER";
	public static final String ROUTER_ADDGOAL				= "ROUTER_ADDGOAL";
	public static final String ROUTER_VIEWGOALS				= "ROUTER_VIEWGOALS";
	public static final String ROUTER_HELP					= "ROUTER_HELP";
	public static final String ROUTER_REENABLE_ACCOUNT		= "ROUTER_REENABLE_ACCOUNT";

	// Status
	public static enum Status {INACTIVE, ACTIVE, DISABLED};
	
	// Deals
	public static  final String API_URL = "http://api.sqoot.com/v2";
	public static final String PUBLIC_API_KEY = "api_key 8bv8fw";
	public static final String PRIVATE_API_KEY = "api_key DlRDddP_GW6htiuxZiRY";
	
	// Prevent instantiation
	private Constants() {};
}
