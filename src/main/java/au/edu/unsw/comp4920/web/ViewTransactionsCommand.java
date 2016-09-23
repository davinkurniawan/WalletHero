package au.edu.unsw.comp4920.web;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import au.edu.unsw.comp4920.common.CommonDAO;
import au.edu.unsw.comp4920.common.Constants;
import au.edu.unsw.comp4920.objects.Transaction;

public class ViewTransactionsCommand implements Command {

	public ViewTransactionsCommand() {
	}

	public void execute(HttpServletRequest request, HttpServletResponse response, CommonDAO dao)
			throws ServletException, IOException {
		System.out.println("Inside: ViewTransactionsCommand");

		HttpSession session = request.getSession();
		int personID = Integer.parseInt(session.getAttribute(Constants.USERID).toString());

		String fromDate = request.getParameter("from_date");
		String toDate = request.getParameter("to_date");
		String category = "";

		// TODO: insert this as part of the filter; might be better if we
		// can make one query for all the filters.
		if (request.getParameter("category") != null) {
			category = request.getParameter("category");
		}

		// TODO: Clean this logic up.
		if (fromDate == null || toDate == null) {
			fromDate = "";
			toDate = "";
		}

		List<Transaction> transactions;
		String transactionRange;

		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");

		// If a date range is specified, look in that range.
		if (!fromDate.equals("") && !fromDate.equals("")) {
			Date from = Date.valueOf(fromDate);
			Date to = Date.valueOf(toDate);
			transactionRange = "Viewing transactions from " + from.toString() + " to " + to.toString() + ".";

			// TODO: Find proper workaround.
			// Need to increment to by one day as PostgreSQL stores dates weird.
			// http://stackoverflow.com/a/15840057
			to = new Date(to.getTime() + 24 * 60 * 60 * 1000);
			transactions = dao.getTransactionsByDate(personID, from, to);

			// If no range is specified, look for transactions done in past
			// week.
		} else {
			//stackoverflow.com/a/35613796
			Date from =	new java.sql.Date(Calendar.getInstance().getTimeInMillis());
			from = new Date(from.getTime() - 24 * 60 * 60 * 1000 * 6);
			
			Date to =	new java.sql.Date(Calendar.getInstance().getTimeInMillis());
			transactionRange = "Viewing transactions from " + from.toString() + " to " + to.toString() + ".";

			// TODO: Find proper workaround.
			to = new Date(to.getTime() + 24 * 60 * 60 * 1000);
			transactions = dao.getTransactionsByDate(personID, from, to);
		}

		request.setAttribute("transactionList", transactions);
		request.setAttribute("transactionRange", transactionRange);

		RequestDispatcher rd = request.getRequestDispatcher("/viewtransactions.jsp");
		rd.forward(request, response);
	}
}
