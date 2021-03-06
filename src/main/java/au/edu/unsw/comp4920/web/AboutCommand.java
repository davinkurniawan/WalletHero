package au.edu.unsw.comp4920.web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import au.edu.unsw.comp4920.common.CommonDAO;

/**
 * @author Timothy
 *
 */
public class AboutCommand implements Command {

	public AboutCommand() {
		
	}
	
	public void execute(HttpServletRequest request, HttpServletResponse response, CommonDAO dao) throws ServletException, IOException{
		System.out.println("Inside: AboutCommand");
		RequestDispatcher rd = request.getRequestDispatcher("/about.jsp");
		rd.forward(request, response);
	}	
}
