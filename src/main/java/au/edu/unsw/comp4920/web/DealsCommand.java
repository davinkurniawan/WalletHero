package au.edu.unsw.comp4920.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
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

public class DealsCommand implements Command {
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response, CommonDAO dao) throws ServletException, IOException {
		System.out.println("Inside: DealsCommand");
		int req_page = 1;
		
		try {
			req_page = Integer.parseInt(request.getParameter("page"));
		} 
		catch (Exception e) {
			req_page = 1;
		}
		
		//int pages = 9;
		int starting_page = 1;
		int ending_page = 9;
		HttpClient client = HttpClientBuilder.create().build();
		HttpGet req = new HttpGet(Constants.API_URL + "/deals" + "?page=" + req_page);
		req.addHeader("Authorization", Constants.PUBLIC_API_KEY);
		req.addHeader("Accept", "application/json");
		HttpResponse resp = client.execute(req);
		HttpEntity entity = resp.getEntity();
		BufferedReader br = new BufferedReader(new InputStreamReader(entity.getContent()));
		StringBuilder sb = new StringBuilder();
		String line = "";
		
		while ((line = br.readLine())!= null) {
			sb.append(line + "\n");
		}
		
		JSONObject json = new JSONObject(sb.toString());
		
		//String json_error = json.getString("error");
		//System.out.println(json_query.toString());
		if (!json.isNull("error")) {
			request.setAttribute(Constants.ERROR, 1);
			request.setAttribute(Constants.ERRORMSG, "Sorry, page does not exist!");
		} 
		else {
			JSONObject json_query = json.getJSONObject("query");
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
			
			JSONArray array = json.getJSONArray("deals");
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
