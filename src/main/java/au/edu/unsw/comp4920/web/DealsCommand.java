package au.edu.unsw.comp4920.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import au.edu.unsw.comp4920.common.CommonDAO;

public class DealsCommand implements Command {

	private static final String API_URL = "http://api.sqoot.com/v2";
	private static final String PUBLIC_API_KEY = "api_key 8bv8fw";
	private static final String PRIVATE_API_KEY = "api_key DlRDddP_GW6htiuxZiRY";
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response, CommonDAO dao)
			throws ServletException, IOException {
		System.out.println("Inside: DealsCommand");
		URL url = new URL(API_URL + "/deals");
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("Authorization", PUBLIC_API_KEY);
		BufferedReader buf = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer res = new StringBuffer();
		while((inputLine = buf.readLine()) != null) {
			res.append(inputLine);
		}
		buf.close();
		//System.out.println(response.toString());
	}
	
}
