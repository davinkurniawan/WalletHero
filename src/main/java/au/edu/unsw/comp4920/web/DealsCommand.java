package au.edu.unsw.comp4920.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONArray;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;

import au.edu.unsw.comp4920.common.CommonDAO;
import au.edu.unsw.comp4920.common.Constants;
import au.edu.unsw.comp4920.objects.Deal;
import au.edu.unsw.comp4920.objects.DealsCategory;

public class DealsCommand implements Command {
	
	JSONObject sendAPIRequest(String URI) throws IOException {
		HttpClient client = HttpClientBuilder.create().build();
		HttpGet req = new HttpGet(URI);
		req.addHeader("Authorization", Constants.PUBLIC_API_KEY);
		req.addHeader("Accept", "application/json");
		HttpResponse resp = client.execute(req);
		HttpEntity entity = resp.getEntity();
		BufferedReader br = new BufferedReader(new InputStreamReader(entity.getContent())); 
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = br.readLine()) != null) {
			sb.append(line + "\n");
		}
		return (new JSONObject(sb.toString()));
	}
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response, CommonDAO dao) throws ServletException, IOException {
		System.out.println("Inside: DealsCommand");
		if (request.getParameter("category") != null) {
			for(int i = 0; i < request.getParameterValues("category").length; ++i) {
				System.out.println(request.getParameterValues("category")[i]);
			}
			
		}
		int req_page = 1;
		
		try {
			req_page = Integer.parseInt(request.getParameter("page"));
		} 
		catch (Exception e) {
			req_page = 1;
		}
		
		int starting_page = 1;
		int ending_page = 9;
		List<DealsCategory> categories = new ArrayList<DealsCategory>();
		HttpSession session = request.getSession(true);
		if (session.getAttribute("categories") == null) {
			// get categories of deals to select from
			JSONObject categories_json = sendAPIRequest(Constants.API_URL + "/categories");
			JSONArray categories_array = categories_json.getJSONArray("categories");
			ObjectMapper mapper = new ObjectMapper();
			
			for (int i = 0; i < categories_array.length(); ++i) {
				String category = categories_array.getJSONObject(i).getJSONObject("category").toString();
				DealsCategory c = mapper.readValue(category, DealsCategory.class);
				categories.add(c);
			}
			session.setAttribute("categories", categories);
		}
		String req = Constants.API_URL + "/deals" + "?page=" + req_page;
		
		if (request.getParameterValues("category") != null) {
			req += "&category_slugs=";
			for (String s : request.getParameterValues("category")) {
				req += s;
				req += ",";
			}
		} 
		
		if (request.getParameter("query") != null) {
			System.out.println("query is " + request.getParameter("query"));
			req += "&query=";
			req += request.getParameter("query").replaceAll(" ", "+");
		}
		JSONObject deals_json = sendAPIRequest(req);
		if (!deals_json.isNull("error")) {
			request.setAttribute(Constants.ERROR, 1);
			request.setAttribute(Constants.ERRORMSG, "Sorry, page does not exist!");
		} 
		else {
			JSONObject json_query = deals_json.getJSONObject("query");
			int total = json_query.getInt("total");
			int max_page = (int) Math.ceil((double)total/10);
			
			if (max_page < 10) {
				ending_page = max_page;
			}
			
			if (req_page > 5) {
				starting_page = req_page - 4;
				
				if (req_page + 4 < max_page) {
					ending_page = req_page + 4;
				} 
				else {
					ending_page = max_page;
				}
			}
			
			JSONArray array = deals_json.getJSONArray("deals");
			ObjectMapper mapper = new ObjectMapper();
			ArrayList<Deal> deals = new ArrayList<Deal>();
			
			for (int i = 0; i < array.length(); ++i) {
				String deal = array.getJSONObject(i).getJSONObject("deal").toString();
				Deal d = mapper.readValue(deal, Deal.class);
				deals.add(d);
			}
			
			request.setAttribute("deals_list", deals);
			request.setAttribute("max_page", max_page);
		}
		
		request.setAttribute("starting_page", starting_page);
		request.setAttribute("ending_page", ending_page);
		
		// Display resulting deals page
		RequestDispatcher rd = request.getRequestDispatcher("/deals.jsp");
		rd.forward(request, response);
	}
}
