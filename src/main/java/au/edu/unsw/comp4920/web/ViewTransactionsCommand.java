package au.edu.unsw.comp4920.web;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.text.ParseException;
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

	public void execute(HttpServletRequest request, HttpServletResponse response, CommonDAO dao) throws ServletException, IOException {
		System.out.println("Inside: ViewTransactionsCommand");

		HttpSession session = request.getSession();
		int personID = Integer.parseInt(session.getAttribute(Constants.USERID).toString());

		String fromDate = request.getParameter("from_date");
		String toDate = request.getParameter("to_date");

		int categoryID;
		boolean viewIncomes = true;
		boolean viewExpenses = true;

		try {
			categoryID = Integer.parseInt(request.getParameter("category"));
		} catch (NumberFormatException e) {
			categoryID = -1;
		}

		if (request.getParameter("incomesChk") != null && request.getParameter("expensesChk") == null) {
			viewIncomes = true;
			viewExpenses = false;
		}
		if (request.getParameter("incomesChk") == null && request.getParameter("expensesChk") != null) {
			viewExpenses = true;
			viewIncomes = false;
		}
		
		request.setAttribute("viewExpenses", viewExpenses);
		request.setAttribute("viewIncomes", viewIncomes);
		
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
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} else {
			// If no range is specified, look for transactions done in past
			// week.
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

		// Decide which graph to present to user.
		int graphType = 3;

		// Constants used here and in viewtransactions.jsp.
		int EXPENSES_ONLY = 1;
		int INCOMES_ONLY = 2; 
		int BOTH = 3;

		if (viewIncomes == true && viewExpenses == true) {
			HashMap<String, HashMap<String, BigDecimal>> hashmap = getBalance(transactions);
			
			graphType = BOTH;
			request.setAttribute("graphData", hashmap);
			
		} else if (viewExpenses == false) {
			HashMap<String, BigDecimal> hashmap = getTransactionCategoryData(transactions, true);
			
			graphType = INCOMES_ONLY;
			request.setAttribute("transactionType", "Income");
			request.setAttribute("graphData", hashmap);
			
		} else if (viewIncomes == false) {
			HashMap<String, BigDecimal> hashmap = getTransactionCategoryData(transactions, false);
			
			graphType = EXPENSES_ONLY;
			request.setAttribute("transactionType", "Expense");
			request.setAttribute("graphData", hashmap);
			
		}

		request.setAttribute("graphType", graphType);

		RequestDispatcher rd = request.getRequestDispatcher("/viewtransactions.jsp");
		request.setAttribute(Constants.VIEWTRANSACTIONS_COMMAND, "active");
		rd.forward(request, response);
	}

	private HashMap<String, BigDecimal> getTransactionCategoryData(List<Transaction> transactions, boolean isIncome) {
		HashMap<String, BigDecimal> hashmap = new HashMap<String, BigDecimal>();

		// Get spending categories data:
		for (Transaction t : transactions) {
			if (t.isIncome() == isIncome) {

				String category = t.getCategoryName();
				BigDecimal amount = t.getAmount();

				if (hashmap.containsKey(category)) {
					BigDecimal prevAmount = hashmap.get(category);
					hashmap.put(category, prevAmount.add(amount));
				} else {
					hashmap.put(category, amount);
				}
			}
		}

		return hashmap;
	}

	// Parent hash is the date.
	// Child hash is the profit/loss associated with said date.
	private LinkedHashMap<String, HashMap<String, BigDecimal>> getBalance(List<Transaction> transactions) {
		LinkedHashMap<String, HashMap<String, BigDecimal>> parentHashmap = new LinkedHashMap<String, HashMap<String, BigDecimal>>();

		for (Transaction t : transactions) { 
			String dateString = t.getDate();

			HashMap<String, BigDecimal> childHashmap = null;
			if (!parentHashmap.containsKey(dateString)) {
				childHashmap = new HashMap<String, BigDecimal>();
				childHashmap.put("income", new BigDecimal(0));
				childHashmap.put("expense", new BigDecimal(0));
				childHashmap.put("profit", new BigDecimal(0));

				parentHashmap.put(dateString, childHashmap);
			} else {
				childHashmap = parentHashmap.get(dateString);
			}

			if (!t.isIncome()) {
				BigDecimal prevExpense = childHashmap.get("expense");
				childHashmap.put("expense", prevExpense.subtract(t.getAmount()));

				BigDecimal prevProfit = childHashmap.get("profit");
				childHashmap.put("profit", prevProfit.subtract(t.getAmount()));
			} else {
				BigDecimal prevExpense = childHashmap.get("income");
				childHashmap.put("income", prevExpense.add(t.getAmount()));

				BigDecimal prevProfit = childHashmap.get("profit");
				childHashmap.put("profit", prevProfit.add(t.getAmount()));
			}
		}

		return parentHashmap;
	}
}
