package au.edu.unsw.comp4920.web;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import au.edu.unsw.comp4920.common.CommonDAO;
import au.edu.unsw.comp4920.common.Constants;
import au.edu.unsw.comp4920.objects.Goal;
import au.edu.unsw.comp4920.objects.Transaction;

public class ViewGoalsCommand implements Command {

	public ViewGoalsCommand() {

	}

	public void execute(HttpServletRequest request, HttpServletResponse response, CommonDAO dao)
			throws ServletException, IOException {
		System.out.println("Inside: ViewGoals");

		HttpSession session = request.getSession();
		int personID = Integer.parseInt(session.getAttribute(Constants.USERID).toString());

		List<Goal> goals = dao.getAllGoals(personID);

		for (Goal g : goals) {
			if (g.getCategoryString() == null) {
				g.setCategoryString("All");
			}

			Date from = null;
			Date to = null;

			// TODO: Extend to fortnightly, quarterly, yearly ... etc.
			if (g.getGoalPeriod().equals("weekly")) {
				from = getWeekStart();
				to = getWeekEnd();
			} else if (g.getGoalPeriod().equals("monthly")) {
				from = getMonthStart();
				to = getMonthEnd();
			}
			else {
				from = getWeekStart();
				to = getWeekEnd();
			}

			if (g.isExpenseRestrictionGoal()) {
				List<Transaction> transactions = dao.getTransactionsByDate(personID, from, to, false, true,
						g.getCategory());

				BigDecimal amount = sumExpenses(transactions);
				g.setCurrentAmount(amount);

				if (g.getCurrentAmount().compareTo(g.getGoalAmount()) <= 0) {
					g.setStatusString("Congratulations you are currently under your " + g.getGoalPeriod() + " spending limit for "
							+ g.getCategoryString() + ". You can spend $"
							+ (g.getGoalAmount().subtract(g.getCurrentAmount()) + " more and not exceed your limit."));
				} else {
					g.setStatusString("You have exceed your " + g.getGoalPeriod() + " spending limit for " + g.getCategoryString()
							+ ". You have exceed your limit by $" + (g.getCurrentAmount().subtract(g.getGoalAmount()))
							+ ".");
				}

			} else if (g.isSavingGoal()) {
				List<Transaction> transactions = dao.getTransactionsByDate(personID, from, to, true, true, -1);

				BigDecimal amount = getBalance(transactions);
				g.setCurrentAmount(amount);

				if (g.getCurrentAmount().compareTo(g.getGoalAmount()) >= 0) {
					g.setStatusString("Congratulations you are currently meeting your " + g.getGoalPeriod() + " saving goal. You have saved $"
							+ g.getCurrentAmount() + " of the target $" + g.getGoalAmount() + ".");
				} else {
					g.setStatusString("You have not currently met your " + g.getGoalPeriod() + " saving goal. You have saved $" + g.getCurrentAmount()
							+ " of the target $" + g.getGoalAmount() + ". You need to save $"
							+ (g.getGoalAmount().subtract(g.getCurrentAmount()) + " more."));
				}

			}

		}

		request.setAttribute("goalList", goals);
		RequestDispatcher rd = request.getRequestDispatcher("/viewgoals.jsp");
		rd.forward(request, response);
	}

	private BigDecimal getBalance(List<Transaction> transactions) {
		BigDecimal sum = new BigDecimal(0);

		for (Transaction t : transactions) {
			if (t.isIncome()) {
				sum = sum.add(t.getAmount());
			} else {
				sum = sum.subtract(t.getAmount());
			}
		}
		return sum;
	}

	private BigDecimal sumExpenses(List<Transaction> transactions) {
		BigDecimal sum = new BigDecimal(0);

		for (Transaction t : transactions) {
			sum = sum.add(t.getAmount());
		}
		return sum;
	}

	private Date getWeekEnd() {
		// http://stackoverflow.com/a/12075998
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);

		Date d = c.getTime();
		return new Date(d.getTime() + 24 * 60 * 60 * 1000 * 7);
	}

	private Date getWeekStart() {
		// http://stackoverflow.com/a/12075998
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);

		Date d = c.getTime();
		return new Date(d.getTime());
	}

	private Date getMonthStart() {
		// http://stackoverflow.com/a/12075998
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());

		c.set(Calendar.DATE, 1);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);

		Date d = c.getTime();
		return new Date(d.getTime());
	}

	private Date getMonthEnd() {
		// http://stackoverflow.com/a/12075998
		// http://stackoverflow.com/a/13012840
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());

		c.set(Calendar.DATE, c.getActualMaximum(Calendar.DATE));
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);

		Date d = c.getTime();
		return new Date(d.getTime());
	}

}
