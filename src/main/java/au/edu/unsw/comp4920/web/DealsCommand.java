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
import au.edu.unsw.comp4920.objects.Deal;

public class DealsCommand implements Command {

	private static final String API_URL = "http://api.sqoot.com/v2";
	private static final String PUBLIC_API_KEY = "api_key 8bv8fw";
	private static final String PRIVATE_API_KEY = "api_key DlRDddP_GW6htiuxZiRY";
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response, CommonDAO dao)
			throws ServletException, IOException {
		System.out.println("Inside: DealsCommand");
		HttpClient client = HttpClientBuilder.create().build();
		HttpGet req = new HttpGet(API_URL + "/deals");
		req.addHeader("Authorization", PUBLIC_API_KEY);
		req.addHeader("Accept", "application/json");
		HttpResponse resp = client.execute(req);
		HttpEntity entity = resp.getEntity();
		BufferedReader br = new BufferedReader(new InputStreamReader(entity.getContent()));
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = br.readLine())!= null) {
			sb.append(line + "\n");
		}
		JSONObject json = new JSONObject(sb.toString());
		JSONArray array = json.getJSONArray("deals");
		ObjectMapper mapper = new ObjectMapper();
		ArrayList<Deal> deals = new ArrayList<Deal>();
		for (int i = 0; i < array.length(); ++i) {
			String deal = array.getJSONObject(i).getJSONObject("deal").toString();
			Deal d = mapper.readValue(deal, Deal.class);
			deals.add(d);
		}
		request.setAttribute("deals", deals);
		// Display resulting deals page
		RequestDispatcher rd = request.getRequestDispatcher("/deals.jsp");
		rd.forward(request, response);
	}
	
}
