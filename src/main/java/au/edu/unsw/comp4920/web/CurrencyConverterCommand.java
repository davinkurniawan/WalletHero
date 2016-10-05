package au.edu.unsw.comp4920.web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import au.edu.unsw.comp4920.common.CommonDAO;
import au.edu.unsw.comp4920.common.Constants;
import au.edu.unsw.comp4920.objects.Preference;

public class CurrencyConverterCommand implements Command {
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response, CommonDAO dao) throws ServletException, IOException {
		System.out.println("Inside: CurrencyConverterCommand");
		
		HttpSession session = request.getSession(true);		
		session.setAttribute(Constants.CURRENCY, dao.getAllCurrencies());

		if (session.getAttribute(Constants.SID) != null){
			String sid = session.getAttribute(Constants.SID).toString();
			Preference preference = dao.getUserPreference(sid);
			request.setAttribute(Constants.PREFERENCE, preference);
		}
		
		RequestDispatcher rd = request.getRequestDispatcher("/currencyconverter.jsp");
		rd.forward(request, response);
	}
}
