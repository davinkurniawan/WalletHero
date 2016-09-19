package au.edu.unsw.comp4920.web;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Calendar;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import au.edu.unsw.comp4920.common.CommonDAO;
import au.edu.unsw.comp4920.common.Constants;
import au.edu.unsw.comp4920.common.PostgreSQLDAOImpl;
import au.edu.unsw.comp4920.objects.Transaction;

public class AddTransactionCommand implements Command {

	public AddTransactionCommand() {
	}

	public void execute(HttpServletRequest request, HttpServletResponse response, CommonDAO dao)
			throws ServletException, IOException {

		String details = request.getParameter("details");
		BigDecimal value = new BigDecimal(request.getParameter("amount"));
		String transactionType = request.getParameter("transactionType");
		Boolean isIncome = null;

		// Integer personID = Integer.parseInt((String)
		// request.getSession().getAttribute(Constants.PERSONID));
		Integer personID = 1;

		if (transactionType.equals("income")) {
			isIncome = true;
		} else if (transactionType.equals("expense")) {
			isIncome = false;
		}

		if ((isIncome == null) || (value == null) || (details == null)) {
			System.out.println("Failed as something was null.");

			RequestDispatcher rd = request.getRequestDispatcher("/error.jsp");
			rd.forward(request, response);
		} else {
			Transaction t = new Transaction();
			t.setPersonID(personID);
			t.setDetails(details);
			t.setAmount(value);
			t.setIsIncome(isIncome);
			t.setDate(new java.sql.Date(Calendar.getInstance().getTime().getTime()));

			// TODO: Replace daoTEMP with dao
			PostgreSQLDAOImpl daoTEMP = new PostgreSQLDAOImpl();
			daoTEMP.addTransaction(t);

			System.out.println("Successfully inserted transaction: " + t.toString());

			RequestDispatcher rd = request.getRequestDispatcher("/index.jsp");
			rd.forward(request, response);
		}

	}

}
