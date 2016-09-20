package au.edu.unsw.comp4920.web;

import java.io.IOException;
import java.util.Calendar;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import au.edu.unsw.comp4920.common.CommonDAO;
import au.edu.unsw.comp4920.common.Constants;
import au.edu.unsw.comp4920.objects.Transaction;

public class AddTransactionCommand implements Command {

	public AddTransactionCommand() {
	}

	public void execute(HttpServletRequest request, HttpServletResponse response, CommonDAO dao)
			throws ServletException, IOException {
		System.out.println("Inside: AddTransactionCommand");

		if (request.getParameter("amount") != null && request.getParameter("transactionType") != null) {
			String details = request.getParameter("details");
			double value = Double.parseDouble(request.getParameter("amount"));
			String transactionType = request.getParameter("transactionType");

			Boolean isIncome = null;

			int personID = (int) request.getSession().getAttribute(Constants.PERSONID);

			if (transactionType.equals("income")) {
				isIncome = true;
			} else if (transactionType.equals("expense")) {
				isIncome = false;
			}

			if ((isIncome == null) || (details == null)) {
				System.out.println("AddTransactionCommand: Failed as something was null.");
				response.sendRedirect(Constants.ROUTER + Constants.ADDTRANSACTION_COMMAND);
			} else {
				Transaction t = new Transaction();
				t.setPersonID(personID);
				t.setDetail(details);
				t.setAmount(value);
				t.setIsIncome(isIncome);
				t.setDate(new java.sql.Date(Calendar.getInstance().getTime().getTime()));

				dao.addTransaction(t);

				System.out.println("AddTransactionCommand: Successfully inserted transaction: " + t.toString());

				RequestDispatcher rd = request.getRequestDispatcher("/addtransaction.jsp");
				rd.forward(request, response);
			}
		}
	}
}
