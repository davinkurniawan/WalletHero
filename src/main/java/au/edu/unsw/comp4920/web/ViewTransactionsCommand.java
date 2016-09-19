package au.edu.unsw.comp4920.web;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import au.edu.unsw.comp4920.common.CommonDAO;
import au.edu.unsw.comp4920.common.PostgreSQLDAOImpl;
import au.edu.unsw.comp4920.objects.*;

public class ViewTransactionsCommand implements Command {

	public ViewTransactionsCommand() {
	}

	public void execute(HttpServletRequest request, HttpServletResponse response, CommonDAO dao)
			throws ServletException, IOException {

		// TODO: Replace daoTEMP with dao
		PostgreSQLDAOImpl daoTEMP = new PostgreSQLDAOImpl();
		List<Transaction> transactions = daoTEMP.getAllTransactions(1);

		request.setAttribute("transactionList", transactions);

		RequestDispatcher rd = request.getRequestDispatcher("/viewtransactions.jsp");
		rd.forward(request, response);
	}
}
