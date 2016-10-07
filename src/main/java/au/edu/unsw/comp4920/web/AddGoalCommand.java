package au.edu.unsw.comp4920.web;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import au.edu.unsw.comp4920.common.CommonDAO;
import au.edu.unsw.comp4920.common.Constants;
import au.edu.unsw.comp4920.objects.Goal;
import au.edu.unsw.comp4920.objects.Recurrence;
import au.edu.unsw.comp4920.objects.Transaction;

public class AddGoalCommand implements Command {

	public AddGoalCommand() {

	}

	public void execute(HttpServletRequest request, HttpServletResponse response, CommonDAO dao)
			throws ServletException, IOException {
		System.out.println("Inside: AddGoalCommand");

		// User has just navigated to the page and has not yet attempted to
		// enter in a goal.
		if (request.getParameterMap().size() == 1) {
			// NOP
		} else {
			int personID = (int) request.getSession().getAttribute(Constants.USERID);
			String details = request.getParameter("details");
			String transactionType = request.getParameter("goalType");

			BigDecimal value = new BigDecimal(request.getParameter("amount"));
			String goalFreq = request.getParameter("goalFreq");
			int category;

			Goal g = new Goal();

			g.setAmount(value);
			g.setDetail(details);
			g.setPersonID(personID);
			g.setGoalPeriod(goalFreq);

			if (request.getParameter("goalType").equals("limitExpenses")) {
				category = Integer.parseInt(request.getParameter("categoryOption"));
				g.setExpenseRestrictionGoal();
				g.setCategory(category);

			} else {
				g.setSavingGoal();
				g.setCategory(-1);
			}
			
			dao.addGoal(g);
		}

		RequestDispatcher rd = request.getRequestDispatcher("/addgoal.jsp");
		rd.forward(request, response);
	}
}
