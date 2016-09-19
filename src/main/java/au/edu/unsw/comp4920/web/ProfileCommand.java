package au.edu.unsw.comp4920.web;

import java.io.IOException;
import java.util.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import au.edu.unsw.comp4920.common.CommonDAO;
import au.edu.unsw.comp4920.objects.*;

/**
 * @author Timothy
 *
 */
public class ProfileCommand implements Command {

	public ProfileCommand() {
		
	}
	
	public void execute(HttpServletRequest request, HttpServletResponse response, CommonDAO dao) throws ServletException, IOException{
		System.out.println("Inside: ProfileCommand");

		RequestDispatcher rd = request.getRequestDispatcher("/profile.jsp");
		rd.forward(request, response);
	}	
}
