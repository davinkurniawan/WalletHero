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
import au.edu.unsw.comp4920.objects.Recurrence;
import au.edu.unsw.comp4920.objects.Transaction;

public class AddTransactionCommand implements Command {

	public AddTransactionCommand() {
	}

	public void execute(HttpServletRequest request, HttpServletResponse response, CommonDAO dao)
			throws ServletException, IOException {

		System.out.println("Inside: AddTransactionCommand"); 

		// User has just navigated to the page and has not yet attempted to
		// enter in a transaction.
		if (request.getParameterMap().size() == 1) {
			// NOP
		} else if (!request.getParameter("amount").equals("") && !request.getParameter("details").equals("")
				&& !request.getParameter("categoryOption").equals("")) {
			
			String details = request.getParameter("details");
			String transactionType = request.getParameter("transactionType");
			BigDecimal value = new BigDecimal(request.getParameter("amount"));
			int personID = (int) request.getSession().getAttribute(Constants.USERID);
			String type = request.getParameter("oneOff");
			int category = Integer.parseInt(request.getParameter("categoryOption"));

			Boolean isIncome = null;
			if (transactionType.equals("income")) {
				isIncome = true;
			} else if (transactionType.equals("expense")) {
				isIncome = false;
			}

			Transaction t = new Transaction();
			t.setPersonID(personID);
			t.setDetail(details);
			t.setAmount(value);
			t.setIsIncome(isIncome);
			t.setDate(new java.sql.Date(Calendar.getInstance().getTime().getTime()));
			t.setCategoryID(category);

			// One off expense.
			if (type.equals("true")) {
				dao.addTransaction(t);

				// Recurring expense.
			} else {
				t.setRecurrence(true);
				int transactionID = dao.addTransaction(t);

				String recurrenceFreq = request.getParameter("recurrenceFreq");
				String paymentPeriod = request.getParameter("paymentPeriod");
				int recurrenceNumber;

				if (paymentPeriod.equals("indefinite")) {
					recurrenceNumber = -1;
				} else {
					recurrenceNumber = new Integer(request.getParameter("numberPayments"));
				}

				Recurrence r = new Recurrence();
				r.setTransactionID(transactionID);
				r.setRecurrenceFreq(recurrenceFreq);
				r.setRecurrenceNumber(recurrenceNumber);

				dao.addRecurring(r);
			}

			request.setAttribute("success", true);
		} else {
			System.out.println("AddTransactionCommand: Failed as something was null.");
			request.setAttribute("error", true);
		}

		request.setAttribute("categories", dao.getCategories());

		RequestDispatcher rd = request.getRequestDispatcher("/addtransaction.jsp");
		request.setAttribute(Constants.ADDTRANSACTION_COMMAND, "active");
		rd.forward(request, response);
	}
}
