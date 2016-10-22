package au.edu.unsw.comp4920.scheduler;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.fasterxml.jackson.databind.ObjectMapper;

import au.edu.unsw.comp4920.common.CommonDAO;
import au.edu.unsw.comp4920.common.Constants;
import au.edu.unsw.comp4920.common.MailHelper;
import au.edu.unsw.comp4920.common.PostgreSQLDAOImpl;
import au.edu.unsw.comp4920.exception.ServiceLocatorException;
import au.edu.unsw.comp4920.objects.Deal;
import au.edu.unsw.comp4920.objects.Preference;
import au.edu.unsw.comp4920.objects.User;
import au.edu.unsw.comp4920.web.DealsCommand;

public class DealEmailJob implements Job {
	protected CommonDAO _dao;

	public void execute(JobExecutionContext context) throws JobExecutionException {
		
		System.out.println("DealEmailJob is being executed!");
		
		/** Initialize database connection. **/
		try {
			_dao = new PostgreSQLDAOImpl();
		} 
		catch (ServiceLocatorException | SQLException e) {
			System.out.println(e.getMessage());
		}
		
		
		
		
		
		/** Create Deals Email. **/
		
		List<User> userList = _dao.getAllUsers();
		
		for (User user : userList) {
			try {
				Preference p = _dao.getUserPreference(user.getUserID());
	
				String req = Constants.API_URL + "/deals" + "?page=1";
				String link_categories_more = Constants.SERVER + Constants.ROUTER + Constants.DEALS_COMMAND;
				
				String[] categories = p.getDealsList();
				
				if (categories.length > 0) {
					for (String s : categories) {
						req += "&category_slugs=";
						req += s;
						
						link_categories_more += "&category=" + s;
					}
				}
	
				
				JSONObject deals_json = DealsCommand.sendAPIRequest(req);
				
				if (deals_json == null || !deals_json.isNull("error")) {
					System.err.println("Fail to find the Deals. So skip sending email.");
					continue;
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
					
					// Delegate tasks.
					this.makeAndSendEmail(deals, user, link_categories_more);
				}
			}
			catch (IOException e) {
				e.printStackTrace();
				System.err.println("Fail to find the Deals. So skip sending email.");
				continue;
			}
		}
		
		
		
		
		
		/** Close database connection. **/
		try {
			_dao.closeConnection();
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void makeAndSendEmail(ArrayList<Deal> deals, User user, String link_categories_more) {
		System.out.println("Making and Sending Email");
		
		int counter = 0;

		String content = "";
		content += "<html>";
		content += "<head>";
		
		content += "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">";
		content += "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">";
		content += "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">";
		content += "<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\" />";
		content += "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\" />";
		content += "<title>" + Constants.WEB_NAME + " - Home</title>";
		content += "<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css\" integrity=\"sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u\" crossorigin=\"anonymous\">";
		content += "<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css\" integrity=\"sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp\" crossorigin=\"anonymous\">";
		content += "<script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js\" integrity=\"sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa\" crossorigin=\"anonymous\"></script>";
		
		content += "</head>";
		content += "<body>";
		
		content += "<div class=\"container marketing\">";
		content += "<h2>Hi " + user.getFirstName() + ",</h2>";
		content += "<hr class=\"featurette-divider\">";
		content += "<div class=\"row featurette\">";
		content += "<div class=\"col-md-12\">";
		
		content += "<div class=\"pull-left\">";
		content += "<h2 class=\"featurette-heading\">Recommended For You</h2>";
		content += "<div class=\"col-md-12\">";
		content += "<div class=\"pull-right\">";
		content += "<p><a href=\"" + link_categories_more + "\">See More...</a></p>";
		
		content += "<div class=\"row\">";
		
		for (Deal deal : deals){
			
			if (counter == 3){
				break;
			}
		
			content += "<div class=\"col-sm-6 col-md-4\">";
			content += "<div class=\"thumbnail\" style=\"height: 330px;\">";
			
			content += "<a target=\"_blank\" href=\"" + deal.getUrl() + "\"><img src=\"" + deal.getImage_url() + "\" height=\"200\" width=\"300\" /></a>";
			
			content += "<div class=\"caption\">";
			
			content += "<h3>";
			content += "<a target=\"_blank\" href=\"" + deal.getUrl() + "\">" + deal.getShort_title() + "</a>";
			content += "</h3>";
			
			content += "<p>";
			content += "From $" + String.format("%.2f", deal.getValue());
			content += " down to <b>$" + String.format("%.2f", deal.getPrice()) + "</b>";
			
			content += "</p>";
			
			content += "</div>";
			content += "</div>";
			content += "</div>";
			
			counter++;
		}
		
		content += "</div>";
		
		content += "</div>";
		content += "<div class=\"clearfix\"></div>";
		content += "</div>";
		content += "</div>";
	
		content += "</div>";
		content += "</div>";
		content += "</div>";
		
		content += "<br/><br/>";
		content += "Have fun, and don't hesitate to contact us with your feedback.";
		content += "<br/><br/>";
		content += "WalletHero Team";
		content += "<br/><br/>";
		content += Constants.SERVER;
		
		content += "</body>";
		content += "</html>";
		
		System.out.println("CT: " + content);
		
		MailHelper mh = new MailHelper();
		mh.sendEmail(user.getEmail(), "WalletHero - Latest Deals", content);
	}
}