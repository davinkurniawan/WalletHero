package au.edu.unsw.comp4920.web;

import java.io.IOException;
import java.util.Date;
import java.text.ParseException;
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

	public void execute(HttpServletRequest request, HttpServletResponse response, CommonDAO dao) throws ServletException, IOException {
		System.out.println("Inside: ViewTransactionsCommand");

		HttpSession session = request.getSession();
		int personID = Integer.parseInt(session.getAttribute(Constants.USERID).toString());

		String fromDate = request.getParameter("from_date");
		String toDate = request.getParameter("to_date");
		
		int categoryID;
		boolean viewIncomes = false;
		boolean viewExpenses = false;
		
		try {
			categoryID = Integer.parseInt(request.getParameter("category"));
		} 
		catch (NumberFormatException e) {
			categoryID = -1;
		}
	
		if (request.getParameter("incomesChk") != null) {
			viewIncomes = true;
		}
		if (request.getParameter("expensesChk") != null) {
			viewExpenses = true;
		}
		if (fromDate == null || toDate == null) {
			fromDate = "";
			toDate = "";
		}

		List<Transaction> transactions = null;
		String transactionRange = "";

		Date from = null;
		Date to = null;
		SimpleDateFormat df = new SimpleDateFormat("dd MMMM yyyy");

		if (!fromDate.equals("") && !toDate.equals("")) {			
			// If a date range is specified, look in that range.
			try {
				from = df.parse(fromDate);
				to = df.parse(toDate);
				transactionRange = "Viewing transactions from " + df.format(from) + " to " + df.format(to);
				
				// TODO: Find proper workaround.
				to = new Date(to.getTime() + 24 * 60 * 60 * 1000);
			} 
			catch (ParseException e) {
				e.printStackTrace();
			}			
		} 
		else {
			// If no range is specified, look for transactions done in past week.
			// https://stackoverflow.com/a/35613796
			from = new Date();
			from = new Date(from.getTime() - 24 * 60 * 60 * 1000 * 6);

			to = new Date();

			transactionRange = "Viewing transactions from " + df.format(from) + " to " + df.format(to);

			// TODO: Find proper workaround.
			to = new Date(to.getTime() + 24 * 60 * 60 * 1000);
		}
		
		transactions = dao.getTransactionsByDate(personID, from, to, viewIncomes, viewExpenses, categoryID);

		request.setAttribute("transactionList", transactions);
		request.setAttribute("transactionRange", transactionRange);
		request.setAttribute("fromDate", df.format(from));
		
		// TODO: Find proper workaround.
		to = new Date(to.getTime() - 24 * 60 * 60 * 1000);
		request.setAttribute("toDate", df.format(to));

		RequestDispatcher rd = request.getRequestDispatcher("/viewtransactions.jsp");
		request.setAttribute(Constants.VIEWTRANSACTIONS_COMMAND, "active");
		rd.forward(request, response);
	}
}
