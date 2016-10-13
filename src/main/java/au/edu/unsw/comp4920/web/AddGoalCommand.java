package au.edu.unsw.comp4920.web;

import java.io.IOException;
import java.math.BigDecimal;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import au.edu.unsw.comp4920.common.CommonDAO;
import au.edu.unsw.comp4920.common.Constants;
import au.edu.unsw.comp4920.objects.Goal;

public class AddGoalCommand implements Command {

	public AddGoalCommand() {

	}

	public void execute(HttpServletRequest request, HttpServletResponse response, CommonDAO dao) throws ServletException, IOException {
		System.out.println("Inside: AddGoalCommand");
		
		// User has just navigated to the page and has not yet attempted to
		// enter in a goal.
		if (request.getParameterMap().size() == 1) {
			// NOP
		} 
		else if (request.getParameter("details") != null && request.getParameter("goalType") != null && request.getParameter("amount") != null && request.getParameter("goalFreq") != null) {		
			int userID = (int) request.getSession().getAttribute(Constants.USERID);
			String details = request.getParameter("details");
			//String transactionType = request.getParameter("goalType");

			BigDecimal value = new BigDecimal(Double.parseDouble(request.getParameter("amount")));
			String goalFreq = request.getParameter("goalFreq");
			int category;

			Goal g = new Goal();

			g.setGoalAmount(value);
			g.setDetail(details);
			g.setUserID(userID);
			g.setGoalPeriod(goalFreq);

			if (request.getParameter("goalType").equals("limitExpenses")) {
				category = Integer.parseInt(request.getParameter("categoryOption"));
				g.setExpenseRestrictionGoal();
				g.setCategory(category);

			} else {
				g.setSavingGoal();
				g.setCategory(-1);
			}
			
			boolean result = dao.addGoal(g);
			
			if (result){
				response.sendRedirect(Constants.ROUTER + Constants.ADDGOAL_COMMAND + "&success=yes");
			}
			else{
				response.sendRedirect(Constants.ROUTER + Constants.ADDGOAL_COMMAND + "&success=no");
			}
			
			return;
		}
		else {
			System.out.println("AddGoalCommand: Failed as something was null.");			
			request.setAttribute(Constants.ERROR, 1);
			request.setAttribute(Constants.ERRORMSG, "Missing Required Information!");
		}

		RequestDispatcher rd = request.getRequestDispatcher("/addgoal.jsp");
		rd.forward(request, response);
	}
}
