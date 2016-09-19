package au.edu.unsw.comp4920.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import au.edu.unsw.comp4920.common.CommonDAO;
import au.edu.unsw.comp4920.common.Constants;
import au.edu.unsw.comp4920.common.PostgreSQLDAOImpl;
import au.edu.unsw.comp4920.exception.ServiceLocatorException;
import au.edu.unsw.comp4920.objects.User;

public class ControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Map<String, Command> _commands;

	protected CommonDAO _dao;
	
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		//TODO
		// Initialize database connection
		_dao = new PostgreSQLDAOImpl();

		// Initialize hashmap of commands
		_commands = new HashMap<String, Command>();
		_commands.put(Constants.SIGNIN_COMMAND,	 		new SignInCommand());
		_commands.put(Constants.SIGNOUT_COMMAND, 		new SignOutCommand());
		_commands.put(Constants.SIGNUP_COMMAND, 		new SignUpCommand());
		_commands.put(Constants.PROFILE_COMMAND, 		new ProfileCommand());
		_commands.put(Constants.PUBLIC_COMMAND, 		new PublicCommand());
		_commands.put(Constants.HOME_COMMAND, 			new HomeCommand());
		_commands.put(Constants.SEARCH_COMMAND,			new SearchCommand());
		_commands.put(Constants.NOTFOUND_COMMAND, 		new ErrorCommand());
		_commands.put(Constants.VALIDATE_COMMAND, 		new EmailValidationCommand());
		_commands.put(Constants.ABOUT_COMMAND, 			new AboutCommand());
		
		_commands.put(Constants.ADDTRANSACTION_COMMAND, new AddTransactionCommand());
		_commands.put(Constants.VIEWTRANSACTIONS_COMMAND, new ViewTransactionsCommand());

		// Global Attributes to be accessed by JSP Files
		ServletContext servletContext = getServletContext();
        servletContext.setAttribute(Constants.WEB_NAME, 				Constants.WEB);
        servletContext.setAttribute(Constants.ROUTER_SIGNIN, 			Constants.ROUTER + Constants.SIGNIN_COMMAND);
        servletContext.setAttribute(Constants.ROUTER_SIGNOUT, 			Constants.ROUTER + Constants.SIGNOUT_COMMAND);
        servletContext.setAttribute(Constants.ROUTER_SIGNUP, 			Constants.ROUTER + Constants.SIGNUP_COMMAND);
        servletContext.setAttribute(Constants.ROUTER_PROFILE, 			Constants.ROUTER + Constants.PROFILE_COMMAND);
        servletContext.setAttribute(Constants.ROUTER_PUBLIC, 			Constants.ROUTER + Constants.PUBLIC_COMMAND);
        servletContext.setAttribute(Constants.ROUTER_HOME, 				Constants.ROUTER + Constants.HOME_COMMAND);
        servletContext.setAttribute(Constants.ROUTER_SEARCH, 			Constants.ROUTER + Constants.SEARCH_COMMAND);
        servletContext.setAttribute(Constants.ROUTER_NOTFOUND, 			Constants.ROUTER + Constants.NOTFOUND_COMMAND);
        servletContext.setAttribute(Constants.ROUTER_VALIDATE, 			Constants.ROUTER + Constants.VALIDATE_COMMAND);
        servletContext.setAttribute(Constants.ROUTER_ABOUT, 			Constants.ROUTER + Constants.ABOUT_COMMAND);
        
        servletContext.setAttribute(Constants.ROUTER_ADDTRANSACTION,    Constants.ROUTER + Constants.ADDTRANSACTION_COMMAND);
        servletContext.setAttribute(Constants.ROUTER_VIEWTRANSACTIONS,    Constants.ROUTER + Constants.VIEWTRANSACTIONS_COMMAND);
        
        
	}

	/*
	 * Process requests for both HTTP <code>GET</code> and <code>POST</code> methods *
	 *
	 * @param request servlet request
	 * @param response servlet response
	 * @param "operation" is the keyword that needs to be used for the commands
	 */
	protected void resolveCommand(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Inside: resolveCommand");
		String dest = request.getParameter(Constants.OPERATION);
		System.out.println("Destinationnnnn: " + dest);
		
		if (this.getLoginStatus(request, response) == false) {
			System.out.println("Inside: Not Signed In");

			if (dest == null) {
				dest = Constants.PUBLIC_COMMAND;
			} 
			else {
				if (_commands.containsKey(dest)) {
					dest = (dest.equals(Constants.PUBLIC_COMMAND) 			||
							dest.equals(Constants.SIGNUP_COMMAND) 			||
							dest.equals(Constants.VALIDATE_COMMAND) 		||
							dest.equals(Constants.NOTFOUND_COMMAND)			||
							dest.equals(Constants.ABOUT_COMMAND) 			||
							// For testing purposes:
							dest.equals(Constants.VIEWTRANSACTIONS_COMMAND) ||
							dest.equals(Constants.ADDTRANSACTION_COMMAND) 	||
							dest.equals(Constants.SIGNIN_COMMAND)) 

							? dest : Constants.SIGNIN_COMMAND;
				}
				else {
					dest = Constants.NOTFOUND_COMMAND;
				}
			}
		}
		else {
			System.out.println("Inside: Signed In");
			
			if (dest == null) {
				dest = Constants.HOME_COMMAND;
			}
			else{
				if (_commands.containsKey(dest)) {
					dest = (!dest.equals(Constants.PUBLIC_COMMAND) 			&&
							!dest.equals(Constants.SIGNIN_COMMAND) 			&&
							!dest.equals(Constants.SIGNUP_COMMAND)			&&
							!dest.equals(Constants.VALIDATE_COMMAND))
							? dest : Constants.HOME_COMMAND;
				}
				else {
					dest = Constants.NOTFOUND_COMMAND;
				}
			}
		}
		
		Command cmd = (Command) _commands.get(dest) == null ? (Command) _commands.get(Constants.NOTFOUND_COMMAND) : (Command) _commands.get(dest);
		cmd.execute(request,response, _dao);
	}

	/*
	 * (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Inside: doGet");
		this.resolveCommand(request, response);
	}

	/*
	 * (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Inside: doPost");
		this.resolveCommand(request, response);
	}

	/*
	 * Return user status: logged in or not
	 *
	 * @param request servlet request
	 * @param response servlet response
	 */
	private boolean getLoginStatus(HttpServletRequest request, HttpServletResponse response) {
		// Query in Session table with current sessionID
		// If current sessionID exists, that means user is already logged in
		// Else that means user has not logged in yet
		String sid = request.getSession().getId();
		Object uid = request.getSession().getAttribute(Constants.PERSONID);

		// Query here
		if (uid == null) return false;
		//TODO return _dao.getUserLoginStatus((int) uid, sid);
		return false;
	}
	
}
