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
import au.edu.unsw.comp4920.objects.Transaction;

public class AddTransactionCommand implements Command {

	public AddTransactionCommand() {
	}

	public void execute(HttpServletRequest request, HttpServletResponse response, CommonDAO dao)
			throws ServletException, IOException {
		System.out.println("Inside: AddTransactionCommand");
 
		if (!request.getParameter("amount").equals("") && !request.getParameter("details").equals("")) {
			String details = request.getParameter("details");
			String transactionType = request.getParameter("transactionType");
			BigDecimal value = new BigDecimal(request.getParameter("amount"));

			int personID = (int) request.getSession().getAttribute(Constants.USERID);

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
			if (request.getParameter("category") != null) {
				t.setCategory(request.getParameter("category"));
			}
			
			dao.addTransaction(t);
			
			request.setAttribute("success", true);
		} else {
			System.out.println("AddTransactionCommand: Failed as something was null.");
			request.setAttribute("error", true);
		}

		RequestDispatcher rd = request.getRequestDispatcher("/addtransaction.jsp");
		rd.forward(request, response);
	}
}
