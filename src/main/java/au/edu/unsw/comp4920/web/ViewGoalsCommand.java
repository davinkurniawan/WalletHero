package au.edu.unsw.comp4920.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import au.edu.unsw.comp4920.common.CommonDAO;
import au.edu.unsw.comp4920.common.Constants;
import au.edu.unsw.comp4920.objects.Goal;

public class ViewGoalsCommand implements Command {

	public ViewGoalsCommand() {

	}

	public void execute(HttpServletRequest request, HttpServletResponse response, CommonDAO dao)
			throws ServletException, IOException {
		System.out.println("Inside: ViewGoals");

		HttpSession session = request.getSession();
		int personID = Integer.parseInt(session.getAttribute(Constants.USERID).toString());

		if (request.getParameter("action") != null) {
			dao.deleteUserGoal(Integer.parseInt(request.getParameter("goalID")), personID);
		}
		
		String sid = session.getAttribute(Constants.SID).toString();
		String userPrefferedCurrency = dao.getUserPreference(sid).getCurrency().getShortName();

		List<Goal> goals = dao.getAllGoals(personID, userPrefferedCurrency);
		
		request.setAttribute("goalList", goals);
		request.setAttribute("userPreferredCurrency", userPrefferedCurrency);
		
		RequestDispatcher rd = request.getRequestDispatcher("/viewgoals.jsp");
		rd.forward(request, response);
	}
}
