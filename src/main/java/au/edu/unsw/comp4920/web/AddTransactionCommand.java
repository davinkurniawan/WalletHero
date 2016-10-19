package au.edu.unsw.comp4920.web;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import au.edu.unsw.comp4920.common.CommonDAO;
import au.edu.unsw.comp4920.common.Constants;
import au.edu.unsw.comp4920.objects.Recurrence;
import au.edu.unsw.comp4920.objects.Transaction;

public class AddTransactionCommand implements Command {

	public AddTransactionCommand() {

	}

	public void execute(HttpServletRequest request, HttpServletResponse response, CommonDAO dao) throws ServletException, IOException {
		System.out.println("Inside: AddTransactionCommand");

		String action = request.getParameter(Constants.ACTION) == null ? null
				: request.getParameter(Constants.ACTION).toString();
		System.out.println("AddTransactionCommand: Action is " + action);
		
		HttpSession session = request.getSession(true);		
		String sid = session.getAttribute(Constants.SID).toString();
		
		if (session.getAttribute(Constants.CURRENCY) == null){
			session.setAttribute(Constants.CURRENCY, dao.getAllCurrencies());
		}
		session.setAttribute(Constants.PREFERENCE, dao.getUserPreference(sid).getCurrency());
		
		if (action != null && action.equalsIgnoreCase("addTransaction")) {

			// User has just navigated to the page and has not yet attempted to
			// enter in a transaction.
			if (request.getParameterMap().size() == 1) {
				// NOP
			} else if (request.getParameter("amount") != null && request.getParameter("details") != null
					&& request.getParameter("categoryOption") != null) {
				String details = request.getParameter("details");
				String transactionType = request.getParameter("transactionType");
				BigDecimal value = new BigDecimal(Double.parseDouble(request.getParameter("amount")));
				String currency = request.getParameter("currency");
				
				int userID = (int) request.getSession().getAttribute(Constants.USERID);
				boolean result = false;

				String type = request.getParameter("oneOff");
				int category = Integer.parseInt(request.getParameter("categoryOption"));

				String startingDate = (request.getParameter("transaction_date") != null) ? request.getParameter("transaction_date")
						: "";
				SimpleDateFormat df = new SimpleDateFormat(Constants.SIMPLE_DEFAULT_DATE_FORMAT);
				Date date = null;

				if (startingDate.equals("")) {
					date = new Date();
				} else {
					try {
						date = df.parse(startingDate);
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}

				Boolean isIncome = null;
				if (transactionType.equals("income")) {
					isIncome = true;
				} else if (transactionType.equals("expense")) {
					isIncome = false;
				}

				Transaction t = new Transaction();
				t.setUserID(userID);
				t.setDetail(details);
				t.setAmount(value);
				t.setIsIncome(isIncome);
				t.setCurrency(currency);

				try {
					t.setDate(df.format(date));
				} catch (NullPointerException e) {
					response.sendRedirect(Constants.ROUTER + Constants.ADDTRANSACTION_COMMAND + "&success=no");
					return;
				}

				t.setCategoryID(category);

				// One off expense.
				if (type.equals("true")) {
					int tID = dao.addTransaction(t);

					if (tID == -1) {
						result = false;
					} else {
						result = true;
					}
				} else { // Recurring expense.
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

					result = dao.addRecurring(r);
				}

				if (result) {
					response.sendRedirect(Constants.ROUTER + Constants.ADDTRANSACTION_COMMAND + "&success=yes");
				} else {
					response.sendRedirect(Constants.ROUTER + Constants.ADDTRANSACTION_COMMAND + "&success=no");
				}

				return;
			} else {
				System.out.println("AddTransactionCommand: Failed as something was null.");
				request.setAttribute(Constants.ERROR, 1);
				request.setAttribute(Constants.ERRORMSG, "Missing Required Information!");
			}
		}

		RequestDispatcher rd = request.getRequestDispatcher("/addtransaction.jsp");
		rd.forward(request, response);
	}
}
