package au.edu.unsw.comp4920.web;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import au.edu.unsw.comp4920.common.CommonDAO;
import au.edu.unsw.comp4920.common.Constants;
import au.edu.unsw.comp4920.common.PostgreSQLDAOImpl;
import au.edu.unsw.comp4920.objects.*;

public class ViewTransactionsCommand implements Command {

	public ViewTransactionsCommand() {
	}

	public void execute(HttpServletRequest request, HttpServletResponse response, CommonDAO dao)
			throws ServletException, IOException {
		System.out.println("Inside: ViewTransactionsCommand");

		int personID = (int) request.getSession().getAttribute(Constants.PERSONID);

		String fromDate = request.getParameter("from_date");
		String toDate = request.getParameter("to_date");

		List<Transaction> transactions;
		String transactionRange;

		if (fromDate != null || toDate != null) {
			LocalDate from = LocalDate.parse(fromDate);
			LocalDate to = LocalDate.parse(toDate);
			transactionRange = "Viewing transactions from " + from.toString() + " to " + to.toString() + ".";
			// Hack because dates are stored weird in database. i.e: "2016-09-20 +10".
			to = to.plusDays(1);
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
