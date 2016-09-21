package au.edu.unsw.comp4920.web;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
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

		// TODO: Clean this logic up.
		if (fromDate == null || toDate == null) {
			fromDate = "";
			toDate = "";
		}

		List<Transaction> transactions;
		String transactionRange;

		if (!fromDate.equals("") && !fromDate.equals("")) {
			SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");

			Date from = Date.valueOf(fromDate);
			Date to = Date.valueOf(toDate);
			transactionRange = "Viewing transactions from " + from.toString() + " to " + to.toString() + ".";

			// TODO: Find proper workaround.
			// Need to increment to by one day as PostgreSQL stores dates weird.
			// http://stackoverflow.com/a/15840057
			to = new Date(to.getTime() + 24 * 60 * 60 * 1000);
			transactions = dao.getTransactionsByDate(personID, from, to);

		} else {
			transactions = dao.getAllTransactions(personID);
			transactionRange = "Viewing all transactions ever entered!";
		}

		request.setAttribute("transactionList", transactions);
		request.setAttribute("transactionRange", transactionRange);

		RequestDispatcher rd = request.getRequestDispatcher("/viewtransactions.jsp");
		rd.forward(request, response);
	}
}
