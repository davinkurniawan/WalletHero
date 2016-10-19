package au.edu.unsw.comp4920.web;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;

import au.edu.unsw.comp4920.common.CommonDAO;
import au.edu.unsw.comp4920.common.Constants;
import au.edu.unsw.comp4920.objects.Deal;
import au.edu.unsw.comp4920.objects.Preference;
import au.edu.unsw.comp4920.objects.Session;
import au.edu.unsw.comp4920.objects.Transaction;
import au.edu.unsw.comp4920.objects.User;

/**
 * @author Timothy
 *
 */
public class HomeCommand implements Command {

	public HomeCommand() {
		
	}
	
	public void execute(HttpServletRequest request, HttpServletResponse response, CommonDAO dao) throws ServletException, IOException{
		System.out.println("Inside: HomeCommand");
		
		HttpSession session = request.getSession();
        String sid = session.getAttribute(Constants.SID).toString();
		
		User user = dao.getUser(sid);
		Session sess = dao.getSession(sid);
				
		// Get Last 7 Days Transactions
		
		boolean viewIncomes = true;
		boolean viewExpenses = true;
		int categoryID = -1; // All Categories
		
		Date to = new Date();
		Date from = new Date();
		SimpleDateFormat dfDate = new SimpleDateFormat(Constants.SIMPLE_DEFAULT_DATE_FORMAT);
		
		from = new Date(from.getTime() - 24 * 60 * 60 * 1000 * 6);
		to = new Date(to.getTime() + 24 * 60 * 60 * 1000);
		
		String userPreferredCurrency = dao.getUserPreference(sid).getCurrency().getShortName();
		List<Transaction> transactions = dao.getTransactionsByDate(user.getUserID(), from, to, viewIncomes, viewExpenses, categoryID, userPreferredCurrency);
		
		request.setAttribute("fromDate", dfDate.format(from));

		// TODO: Find proper workaround.
		to = new Date(to.getTime() - 24 * 60 * 60 * 1000);
		request.setAttribute("toDate", dfDate.format(to));
				
		// Decide which graph to present to user.
		int graphType = 3;

		// Constants used here and in viewtransactions.jsp.
		int EXPENSES_ONLY = 1;
		int INCOMES_ONLY = 2; 
		
		if (viewExpenses == true) {
			HashMap<String, BigDecimal> hashmap = getTransactionCategoryData(transactions, true);
			
			graphType = INCOMES_ONLY;
			request.setAttribute("graphDataIncome", hashmap);
			request.setAttribute("graphTypeIncome", graphType);
			
		} 
		if (viewIncomes == true) {
			HashMap<String, BigDecimal> hashmap = getTransactionCategoryData(transactions, false);
			
			graphType = EXPENSES_ONLY;
			request.setAttribute("graphDataExpense", hashmap);
			request.setAttribute("graphTypeExpense", graphType);
		}
		
		Preference p = dao.getUserPreference(user.getUserID());
		String req = Constants.API_URL + "/deals" + "?page=1";
		String[] categories = p.getDealsList();
		if (categories.length > 0) {
			for (String s : categories) {
				req += "&category_slugs=";
				req += s;
			}
		}
		request.setAttribute("categories", categories);
		
		JSONObject deals_json = DealsCommand.sendAPIRequest(req);
		
		if (!deals_json.isNull("error")) {
			request.setAttribute(Constants.ERROR, 1);
			request.setAttribute(Constants.ERRORMSG, "Sorry, page does not exist!");
		} 
		else {
			JSONArray array = deals_json.getJSONArray("deals");
			ObjectMapper mapper = new ObjectMapper();
			ArrayList<Deal> deals = new ArrayList<Deal>();
			
			for (int i = 0; i < array.length(); ++i) {
				String deal = array.getJSONObject(i).getJSONObject("deal").toString();
				Deal d = mapper.readValue(deal, Deal.class);
				deals.add(d); 
			}
			
			request.setAttribute("deals_list", deals);
		}
		// Get Last Accessed
		DateFormat df = new SimpleDateFormat(Constants.DEFAULT_DATE_FORMAT);
		
		try {
			Date lastAccessed = df.parse(sess.getLastAccess());
			DateFormat df_simple = new SimpleDateFormat(Constants.SIMPLE_DEFAULT_DATE_FORMAT);
			
			String simple_accessed_date = df_simple.format(lastAccessed);
			
			request.setAttribute(Constants.LASTACCESSED, simple_accessed_date);
		} 
		catch (ParseException e) {
			e.printStackTrace();
		}

		
		Map<String, BigDecimal> latestSpending = dao.getCurrentBudget(user.getUserID(), userPreferredCurrency);
		request.setAttribute(Constants.TOTAL_INCOME, latestSpending.get("totalIncome"));
		request.setAttribute(Constants.TOTAL_EXPENSE, latestSpending.get("totalExpense"));
		request.setAttribute(Constants.TOTAL_BUDGET, latestSpending.get("totalBudget"));
		
		request.setAttribute(Constants.PREFERRED_CURRENCY, userPreferredCurrency);
		request.setAttribute(Constants.USERNAME, user.getUsername());

		RequestDispatcher rd = request.getRequestDispatcher("/home.jsp");
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
}
